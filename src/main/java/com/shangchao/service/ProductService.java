package com.shangchao.service;

import com.shangchao.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getById(String productId);

    List<Product> getByTopic(Long topicId);
}
