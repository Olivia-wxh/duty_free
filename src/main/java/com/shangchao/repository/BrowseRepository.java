package com.shangchao.repository;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.BrowseTopic;

import java.util.List;

public interface BrowseRepository {
    <T> T save(T t, String tableName);

    <T>List get(String userId, Class<T> t);

    <T> T getByIdAndUserId(String userId, String id, String paramName, Class<T> t);

    DeleteResult remove(String userId, List<String> ids, String tableName);
}
