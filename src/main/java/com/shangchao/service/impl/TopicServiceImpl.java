package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.repository.ProductRepository;
import com.shangchao.repository.TopicRepository;
import com.shangchao.service.TopicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicRepository topicRepository;

    @Resource
    private ProductRepository productRepository;

    @Override
    public DeleteResult deleteTopicById(String topicId) {
        DeleteResult re = topicRepository.deleteById(topicId);
        return re;
    }

    @Override
    public Topic saveOrUpdateTopic(JSONObject json) {
        String topicName = json.getString("topicName").toString();
        String topicId = json.getString("topicId");
//        String topicId = json.getString("topicId").toString();
        Topic topic = new Topic();
        if (topicId != null) {
            topic.setId(topicId);
        }
        topic.setTopicName(topicName);
        Topic save = topicRepository.save(topic);
        return save;
    }

    @Override
    public List<Topic> getAllTopic() {
        List<Topic> all = topicRepository.findAll();
        return all;
    }

    @Override
    public List<Topic> getTopicWithProduct() {
        List<Topic> all = topicRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            List<Product> byTopic = productRepository.findByTopicIdWithin(all.get(i).getId());
            all.get(i).setProducts(byTopic);
        }
        return all;
    }

    @Override
    public Topic getTopicWithProduct(String topicId) {
        Topic topic = topicRepository.findById(topicId.toString());
        List<Product> byTopic = productRepository.findByTopicIdWithin(topic.getId());
        topic.setProducts(byTopic);
        return topic;
    }
}
