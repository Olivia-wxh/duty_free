package com.shangchao.service.impl;

import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.repository.ProductRepository;
import com.shangchao.repository.TopicRepository;
import com.shangchao.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  @Resource private ProductRepository productRepository;
  @Resource private TopicRepository topicRepository;

  @Override
  public Product getById(String productId) {
    Product product = productRepository.findById(productId);
    return product;
  }

  @Override
  public List<Product> getByTopic(String topicId) {
      Topic topic = topicRepository.findById(topicId.toString());
      List<Product> list = new ArrayList<>();
      for (int i = 0; i < topic.getProductIds().length; i++) {
        Product byId = productRepository.findById(topic.getProductIds()[i].toString());
        list.add(byId);
      }
    return list;
  }


}
