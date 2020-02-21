package com.shangchao.repository.impl;

import com.mongodb.client.DistinctIterable;
import com.shangchao.entity.ExchangeRate;
import com.shangchao.entity.Product;
import com.shangchao.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

  @Override
  public List<String> getBrands() {
    List<String> distinct = mongoTemplate.findDistinct(new Query(), "brandName", "sc_product", String.class);
    return distinct;
  }

  @Override
  public List<Product> findAll() {
    List<Product> all = mongoTemplate.findAll(Product.class);
    return all;
  }

  @Override
  public List<Product> findByBrandName(String s) {
    Query query = new Query(Criteria.where("brandName").is(s));
    List<Product> products = mongoTemplate.find(query, Product.class);
    return products;
  }

  @Override
  public List<Product> findProductByBrand(ObjectId[] oid) {
    Query query = new Query();
    query.addCriteria(Criteria.where("images").ne("").not().size(0));
    query.addCriteria(Criteria.where("_id").in(oid));
    List<Product> products = mongoTemplate.find(query, Product.class);
    return products;
  }

  @Override
  public Double getRate() {
    //查询汇率
    Query query = new Query();
    query.with(Sort.by(Sort.Order.desc("addtime")));
    ExchangeRate rate = mongoTemplate.findOne(query, ExchangeRate.class, "sc_usd_rate");
    String cnyStr = rate.getCny();
    Double cny = Double.parseDouble(cnyStr);
    return cny;
  }
}
