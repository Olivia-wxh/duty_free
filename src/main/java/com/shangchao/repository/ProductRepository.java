package com.shangchao.repository;

import com.shangchao.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public interface ProductRepository{

    List<Product> findByTopicIdWithin(String topicId);

    Product findById(String productId);
}
