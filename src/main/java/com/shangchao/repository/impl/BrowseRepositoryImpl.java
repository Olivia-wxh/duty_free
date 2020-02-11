package com.shangchao.repository.impl;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.repository.BrowseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrowseRepositoryImpl implements BrowseRepository {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public <T> T save(T t,  String tableName) {
        T save = mongoTemplate.save(t, tableName);
        return save;
    }

    @Override
    public <T>List get(String userId, Class<T> t) {
        Query query = new Query(Criteria.where("userId").is(userId));
        List<T> pros = mongoTemplate.find(query, t);
        return pros;
    }

    @Override
    public DeleteResult remove(String userId, String id, String tableName) {
        Query query = new Query();
        if ("browse_product".equals(tableName)) {
            query.addCriteria(Criteria.where("productId").is(id));
        } else {
            query.addCriteria(Criteria.where("topicId").is(id));
        }
        query.addCriteria(Criteria.where("userId").is(userId));
        DeleteResult remove = mongoTemplate.remove(query, tableName);
        return remove;
    }
}
