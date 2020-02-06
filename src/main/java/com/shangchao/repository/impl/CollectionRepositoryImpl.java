package com.shangchao.repository.impl;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionRepositoryImpl implements CollectionRepository {

  @Autowired protected MongoTemplate mongoTemplate;

  @Override
  public CollectTopic saveCollectionTopic(CollectTopic ct) {
    CollectTopic collectTopic = mongoTemplate.save(ct);
    return collectTopic;
  }

  @Override
  public CollectProduct saveCollectionProduct(CollectProduct cp) {
    CollectProduct collectProduct = mongoTemplate.save(cp);
    return collectProduct;
  }

  @Override
  public DeleteResult delCollectionTopic(String userId, String topicId) {
    Query query = new Query(Criteria.where("topicId").is(topicId));
    query.addCriteria(Criteria.where("userId").is(userId));
    DeleteResult remove = mongoTemplate.remove(query, CollectTopic.class);
    return remove;
  }

  @Override
  public DeleteResult delCollectionProduct(String userId, List<String> productIds) {
      Query query = new Query(Criteria.where("productId").in(productIds));
      query.addCriteria(Criteria.where("userId").is(userId));
      List<CollectProduct> collectProducts = mongoTemplate.find(query, CollectProduct.class);
      DeleteResult remove = mongoTemplate.remove(query, CollectProduct.class);
      return remove;
  }

  @Override
  public List<CollectTopic> getTopics(String userId) {
    Query query = new Query(Criteria.where("userId").is(userId));
    List<CollectTopic> topics = mongoTemplate.find(query, CollectTopic.class);
    return topics;
  }

  @Override
  public List<CollectProduct> getProducts(String userId) {
    Query query = new Query(Criteria.where("userId").is(userId));
    List<CollectProduct> products = mongoTemplate.find(query, CollectProduct.class);
    return products;
  }

  @Override
  public CollectTopic getByUserIdTopicId(String userId, String topicId) {
    Query query = new Query(Criteria.where("userId").is(userId));
    query.addCriteria(Criteria.where("topicId").is(topicId));
    CollectTopic topics = mongoTemplate.findOne(query, CollectTopic.class);
    return topics;
  }

  @Override
  public CollectProduct getByUserIdProductId(String userId, String productId) {
    Query query = new Query(Criteria.where("userId").is(userId));
    query.addCriteria(Criteria.where("productId").is(productId));
    CollectProduct pro = mongoTemplate.findOne(query, CollectProduct.class);
    return pro;
  }
}
