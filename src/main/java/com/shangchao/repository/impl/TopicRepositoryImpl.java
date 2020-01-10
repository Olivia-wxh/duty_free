package com.shangchao.repository.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shangchao.entity.Topic;
import com.shangchao.repository.TopicRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
  public UpdateResult update(String topicId, ObjectId productId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(topicId));
    Update update = new Update();
    update.addToSet("productIds", productId);
    UpdateResult sc_topic = mongoTemplate.upsert(query, update, "sc_topic");
    return sc_topic;
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

  @Override
  public List<Topic> getByPage(Integer currentPage, Integer pageSize) {
    Pageable pageable = PageRequest.of(currentPage,pageSize);
    Query query = new Query();
    query.with(pageable);
    List<Topic> all = mongoTemplate.find(query, Topic.class);
    return all;
  }
}
