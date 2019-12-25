package com.shangchao.repository.impl;

import com.shangchao.entity.Product;
import com.shangchao.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryImpl implements ProductRepository {

  @Autowired protected MongoTemplate mongoTemplate;

  @Override
  public List<Product> findByTopicIdWithin(String topicId) {
    Query query = new Query(Criteria.where("specialTopic").is(topicId));
    List<Product> products = mongoTemplate.find(query, Product.class);
    return products;
  }

  @Override
  public Product findById(String productId) {
    Query query = new Query(Criteria.where("_id").is(productId));
    Product products = mongoTemplate.findOne(query, Product.class);
    return products;
  }
}
