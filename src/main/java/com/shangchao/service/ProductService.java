package com.shangchao.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.Product;
import com.shangchao.entity.dto.ScProductQueryDto;

import java.util.List;

public interface ProductService {

    Product getById(String productId);

    List<Product> getByTopic(String topicId, Integer currentPage);

    List<Product> queryProductPageList(ScProductQueryDto queryDto);

    List<String> getBrands();

    List<String> getBrandsWithName(String brandName);

    JSONArray getBrandsByName(String brandName);
}
