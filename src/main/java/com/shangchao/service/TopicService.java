package com.shangchao.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.Topic;

import java.util.List;

public interface TopicService {

    DeleteResult deleteTopicById(String topicId);

    Topic saveOrUpdateTopic(JSONObject json);

    List<Topic> getAllTopic();

    List<Topic> getTopicWithProduct();

    Topic getTopicWithProduct(String topicId);
}
