package com.shangchao.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.BrowseTopic;
import com.shangchao.entity.CollectTopic;

import java.util.List;

public interface BrowseService {

    <T> T save(String userId, String id, String tableName);

    JSONObject getProducts(String userId);

    DeleteResult delBrowse(String userId, List<String> ids, String tableName);

    JSONObject getTopics(String userId);
}
