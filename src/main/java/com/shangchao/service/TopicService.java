package com.shangchao.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shangchao.entity.Topic;
import com.shangchao.entity.TopicImage;
import org.bson.types.ObjectId;

import java.util.List;

public interface TopicService {

  DeleteResult deleteTopicById(String topicId);

  Topic saveOrUpdateTopic(Topic topic);

  List<Topic> getAllTopic();

  List<Topic> getTopicWithProduct();

  JSONObject getTopicWithProduct(String topicId);

  UpdateResult setTopic(String topicId, ObjectId[] productId);

  List<Topic> getByPage(Integer currentPage, Integer pageSize);

  void resetTopic();

  List<TopicImage> getImages();

  Topic getTopicById(String topicId);
}
