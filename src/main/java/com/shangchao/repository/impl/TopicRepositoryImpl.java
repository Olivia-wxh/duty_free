package com.shangchao.repository.impl;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.Topic;
import com.shangchao.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicRepositoryImpl implements TopicRepository {

  @Autowired protected MongoTemplate mongoTemplate;

  @Override
  public DeleteResult deleteById(String topicId) {
    Query query = new Query(Criteria.where("_id").is(topicId));
    DeleteResult remove = mongoTemplate.remove(query, Topic.class);
    return remove;
  }

  @Override
  public Topic save(Topic topic) {
    Topic save = mongoTemplate.save(topic);
    return save;
  }

  @Override
  public List<Topic> findAll() {
    List<Topic> all = mongoTemplate.findAll(Topic.class);
    return all;
  }

  @Override
  public Topic findById(String topicId) {
    Query query = new Query(Criteria.where("_id").is(topicId));
    Topic topic = mongoTemplate.findOne(query, Topic.class);
    return topic;
  }
}
