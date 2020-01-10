package com.shangchao.repository;

import com.shangchao.entity.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductRepository {

  List<Product> findByTopicIdWithin(String topicId);

  Product findById(String productId);

  List<String> getBrands();

  List<Product> findAll();

  List<Product> findByBrandName(String s);

  List<Product> findProductByBrand(ObjectId[] oid);
}
