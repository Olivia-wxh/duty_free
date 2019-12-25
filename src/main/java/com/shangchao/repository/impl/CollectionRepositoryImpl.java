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
  public DeleteResult delCollectionTopic(String topicId) {
    Query query = new Query(Criteria.where("_id").is(topicId));
    DeleteResult remove = mongoTemplate.remove(query, CollectTopic.class);
    return remove;
  }

  @Override
  public DeleteResult delCollectionProduct(String productId) {
    Query query = new Query(Criteria.where("_id").is(productId));
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
}
