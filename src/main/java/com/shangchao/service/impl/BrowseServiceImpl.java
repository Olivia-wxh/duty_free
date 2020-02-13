package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.*;
import com.shangchao.repository.BrowseRepository;
import com.shangchao.repository.ProductRepository;
import com.shangchao.service.BrowseService;
import com.shangchao.service.TopicService;
import com.shangchao.utils.BeanUtil;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BrowseServiceImpl implements BrowseService {

    @Resource
    private BrowseRepository browseRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private TopicService topicService;

    @Override
    public <T> T save(String userId, String id, String tableName) {
        if ("browse_product".equals(tableName)) {
            BrowseProduct product = browseRepository.getByIdAndUserId(userId, id, "productId", BrowseProduct.class);
            if (product == null) {
                BrowseProduct bp = new BrowseProduct();
                bp.setUserId(userId);
                bp.setProductId(id);
                BrowseProduct save = browseRepository.save(bp, tableName);
                return (T) save;
            } else {
                return null;
            }
        } else {
            BrowseTopic topic = browseRepository.getByIdAndUserId(userId, id, "topicId", BrowseTopic.class);
            if (topic == null) {
                BrowseTopic bt = new BrowseTopic();
                bt.setUserId(userId);
                bt.setTopicId(id);
                BrowseTopic save = browseRepository.save(bt, tableName);
                return (T) save;
            } else {
                return null;
            }
        }
    }

    @Override
    public JSONObject getProducts(String userId) {
        JSONObject jo = new JSONObject();
        Double cny = productRepository.getRate();
        List<Product> temp = new ArrayList<>();
        List<BrowseProduct> browseProducts = browseRepository.get(userId, BrowseProduct.class);
        for (int i = 0; i < browseProducts.size(); i++) {
            Product product = productRepository.findById(browseProducts.get(i).getProductId());
            if (product != null) {
                temp.add(product);
            }
        }
        temp = BeanUtil.exeType(temp, cny);
        jo.put("products", temp);
        return jo;
    }

    @Override
    public DeleteResult delBrowse(String userId, List<String> ids, String tableName) {
        DeleteResult deleteResult = browseRepository.remove(userId, ids, tableName);
        return deleteResult;
    }

    @Override
    public JSONObject getTopics(String userId) {
        JSONObject jo = new JSONObject();
        Double cny = productRepository.getRate();
        //根据userId查询出收藏的topicId的集合
        List<BrowseTopic> collectTopics = browseRepository.get(userId, BrowseTopic.class);
        List<Topic> topicList = new ArrayList<>();
        for (int i = 0; i < collectTopics.size(); i++) {
            Topic topic = topicService.getTopicById(collectTopics.get(i).getTopicId());
            if (topic != null) {
                ObjectId[] productIds = topic.getProductIds();
                List<Product> products = productRepository.findProductByBrand(productIds);
                List<Product> prolist = BeanUtil.exeType(products, cny);
                topic.setProductIds(null);
                topic.setProduct(prolist);
                topicList.add(topic);
            }
        }
        jo.put("topic", topicList);
        return jo;
    }
}
