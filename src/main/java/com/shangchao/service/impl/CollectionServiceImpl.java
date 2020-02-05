package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.repository.CollectionRepository;
import com.shangchao.service.CollectionService;
import com.shangchao.service.ProductService;
import com.shangchao.service.TopicService;
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

    @Override
    public CollectTopic saveCollectionTopic(String userId, String topicId) {
        CollectTopic ct = new CollectTopic();
        ct.setUserId(userId);
        ct.setTopicId(topicId);
        CollectTopic collectTopic = collectionRepository.saveCollectionTopic(ct);
        return collectTopic;
    }

    @Override
    public CollectProduct saveCollectionProduct(String userId, String productId) {
        CollectProduct cp = new CollectProduct();
        cp.setUserId(userId);
        cp.setProductId(productId);
        CollectProduct collectProduct = collectionRepository.saveCollectionProduct(cp);
        return collectProduct;
    }

    @Override
    public DeleteResult delCollectionTopic(String topicId) {
        DeleteResult re = collectionRepository.delCollectionTopic(topicId);
        return re;
    }

    @Override
    public DeleteResult delCollectionProduct(String productId) {
        DeleteResult re = collectionRepository.delCollectionProduct(productId);
        return re;
    }

    @Override
    public JSONObject getTopics(String userId) {
        JSONObject jo = new JSONObject();
        //根据userId查询出收藏的topicId的集合
        List<CollectTopic> collectTopics = collectionRepository.getTopics(userId);
        List<Topic> topicList = new ArrayList<>();
        for (int i = 0; i < collectTopics.size(); i++) {
            Topic topic = topicService.getTopicById(collectTopics.get(i).getTopicId());
            if (topic != null) {
                topic.setProductIds(null);
                topicList.add(topic);
            }
        }
        jo.put("collectTopic", topicList);
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
