package com.shangchao.service.impl;

import com.shangchao.entity.Product;
import com.shangchao.repository.ProductRepository;
import com.shangchao.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    @Resource
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getById(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product;
    }

    @Override
    public List<Product> getByTopic(Long topicId) {
        List<Product> list = productRepository.getByTopic(topicId);
        return list;
    }
}
