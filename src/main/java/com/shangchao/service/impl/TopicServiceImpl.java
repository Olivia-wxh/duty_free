package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.repository.ProductRepository;
import com.shangchao.repository.TopicRepository;
import com.shangchao.service.TopicService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource private TopicRepository topicRepository;

    @Resource private ProductRepository productRepository;

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
        for (int i = 0; i < all.size(); i++) {
          all.get(i).setProductIds(null);
        }
        return all;
    }

    @Override
    public List<Topic> getTopicWithProduct() {
        List<Topic> all = topicRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            List<Product> temp = new ArrayList<>();
                ObjectId[] oid = all.get(i).getProductIds();
                if (oid != null) {
                    for (int j = 0; j < oid.length; j++) {
                        Product byId = productRepository.findById(oid[j].toString());
                        temp.add(byId);
                    }
                }
            all.get(i).setProduct(temp);
            all.get(i).setProductIds(null);
        }
        return all;
    }

    @Override
    public JSONObject getTopicWithProduct(String topicId) {
        JSONObject jo = new JSONObject();
        Topic topic = topicRepository.findById(topicId.toString());
        ObjectId[] oid = topic.getProductIds();
        List<Product> temp = new ArrayList<>();
        for (int i = 0; i < oid.length; i++) {
            Product byId = productRepository.findById(oid[i].toString());
            temp.add(byId);
        }
        topic.setProductIds(null);
        topic.setProduct(temp);
        jo.put("topic", topic);
        return jo;
    }

    @Override
    public UpdateResult setTopic(JSONObject json) {
        String topicId = json.getString("topicId");
        String productId = json.getString("productId");
        UpdateResult save = topicRepository.update(topicId, new ObjectId(productId));
        return save;
    }
}
