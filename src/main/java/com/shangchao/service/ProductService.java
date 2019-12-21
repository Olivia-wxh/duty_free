package com.shangchao.service;

import com.shangchao.entity.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> getById(String productId);
}
