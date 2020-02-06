package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.repository.CollectionRepository;
import com.shangchao.repository.ProductRepository;
import com.shangchao.service.CollectionService;
import com.shangchao.service.ProductService;
import com.shangchao.service.TopicService;
import com.shangchao.utils.BeanUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CollectTopic saveCollectionTopic(String userId, String topicId) {
        //查询当前专题是否收藏过
        CollectTopic topic = collectionRepository.getByUserIdTopicId(userId, topicId);
        if (topic == null) {
            //收藏专题
            CollectTopic ct = new CollectTopic();
            ct.setUserId(userId);
            ct.setTopicId(topicId);
            CollectTopic collectTopic = collectionRepository.saveCollectionTopic(ct);
            return collectTopic;
        } else {
            return null;
        }
    }

    @Override
    public CollectProduct saveCollectionProduct(String userId, String productId) {
        CollectProduct cpro = collectionRepository.getByUserIdProductId(userId, productId);
        if (cpro == null) {
            //收藏商品
            CollectProduct cp = new CollectProduct();
            cp.setUserId(userId);
            cp.setProductId(productId);
            CollectProduct collectProduct = collectionRepository.saveCollectionProduct(cp);
            return collectProduct;
        } else {
            return null;
        }
    }

    @Override
    public DeleteResult delCollectionTopic(String userId, String topicId) {
        DeleteResult re = collectionRepository.delCollectionTopic(userId, topicId);
        return re;
    }

    @Override
    public DeleteResult delCollectionProduct(String userId, List<String> productIds) {
        DeleteResult re = collectionRepository.delCollectionProduct(userId, productIds);
        return re;
    }

    @Override
    public JSONObject getTopics(String userId) {
        JSONObject jo = new JSONObject();
        Double cny = productRepository.getRate();
        //根据userId查询出收藏的topicId的集合
        List<CollectTopic> collectTopics = collectionRepository.getTopics(userId);
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

    @Override
    public JSONObject getProduct(String userId) {
        JSONObject jo = new JSONObject();
        //根据userId查询出收藏的productId的集合
        List<CollectProduct> collectProducts = collectionRepository.getProducts(userId);
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < collectProducts.size(); i++) {
            Product product = productService.getById(collectProducts.get(i).getProductId());
            productList.add(product);
        }
        jo.put("collectProduct", productList);
        return jo;
    }
}
