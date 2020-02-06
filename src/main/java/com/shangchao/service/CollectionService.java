package com.shangchao.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;

import java.util.List;

public interface CollectionService {

    CollectTopic saveCollectionTopic(String userId, String topicId);

    CollectProduct saveCollectionProduct(String userId, String productId);

    DeleteResult delCollectionTopic(String userId, String topicId);

    DeleteResult delCollectionProduct(String userId, List<String> productIds);

    JSONObject getTopics(String userId);

    JSONObject getProduct(String userId);
}
