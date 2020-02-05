package com.shangchao.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.CollectTopic;

public interface BrowseService {

    BrowseProduct save(String userId, String productId);

    JSONObject getProducts(String userId);

    DeleteResult delBrowseProduct(String productId);

}
