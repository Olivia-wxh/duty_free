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
    public BrowseProduct save(BrowseProduct bp) {
        BrowseProduct browseProduct = mongoTemplate.save(bp);
        return browseProduct;
    }

    @Override
    public List<BrowseProduct> getProducts(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        List<BrowseProduct> pros = mongoTemplate.find(query, BrowseProduct.class);
        return pros;
    }

    @Override
    public DeleteResult removeProduct(String productId) {
        Query query = new Query(Criteria.where("productId").is(productId));
        DeleteResult remove = mongoTemplate.remove(query, BrowseProduct.class);
        return remove;
    }
}
