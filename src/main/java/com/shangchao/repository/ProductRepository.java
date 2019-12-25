package com.shangchao.repository;

import com.shangchao.entity.Product;

import java.util.List;

public interface ProductRepository {

  List<Product> findByTopicIdWithin(String topicId);

  Product findById(String productId);
}
