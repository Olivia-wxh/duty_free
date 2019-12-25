package com.shangchao.service;

import com.shangchao.entity.Product;

import java.util.List;

public interface ProductService {
  Product getById(String productId);

  List<Product> getByTopic(String topicId);
}
