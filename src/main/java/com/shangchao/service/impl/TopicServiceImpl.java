package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
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
    public void deleteTopicById(String topicId) {
        topicRepository.deleteById(topicId);
    }

    @Override
    public Topic saveOrUpdateTopic(JSONObject json) {
        String topicName = json.getString("topicName").toString();
        String topicId = json.getString("topicId").toString();
        Topic topic = new Topic();
        topic.setId(Long.parseLong(topicId));
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
            List<Product> byTopic = productRepository.getByTopic(all.get(i).getId());
            all.get(i).setProducts(byTopic);
        }
        return all;
    }

    @Override
    public Topic getTopicWithProduct(Long topicId) {
        Optional<Topic> byId = topicRepository.findById(topicId.toString());
        Topic topic = byId.get();
        List<Product> byTopic = productRepository.getByTopic(topic.getId());
        topic.setProducts(byTopic);
        return topic;
    }
}
