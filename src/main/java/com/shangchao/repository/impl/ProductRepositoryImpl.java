package com.shangchao.repository.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shangchao.entity.ExchangeRate;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.entity.dto.ScProductQueryDto;
import com.shangchao.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public List<Product> findByTopicIdWithin(String topicId) {
        Query query = new Query(Criteria.where("specialTopic").is(topicId));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    @Override
    public Product findById(String productId) {
        Query query = new Query(Criteria.where("_id").is(productId));
        Product products = mongoTemplate.findOne(query, Product.class);
        return products;
    }

    @Override
    public List<String> getBrands() {
        List<String> distinct = mongoTemplate.findDistinct(new Query(), "brandName", "sc_product", String.class);
        return distinct;
    }

    @Override
    public List<Product> findAll() {
        List<Product> all = mongoTemplate.findAll(Product.class);
        return all;
    }

    @Override
    public List<Product> findByBrandName(String s) {
        Query query = new Query(Criteria.where("brandName").is(s));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    @Cacheable(value = "pros")
    @Override
    public List<Product> findProductByBrand(ObjectId[] oid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("images").ne("").not().size(0));
        query.addCriteria(Criteria.where("_id").in(oid));
        query.fields().include("priceRMB");
        query.fields().include("brandName");
        query.fields().include("priceOff");
        query.fields().include("price");
        query.fields().include("images");
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    @Cacheable(value = "pros")
    @Override
    public List<Product> findProductByPage(ObjectId[] oid, Integer currentPage) {
        Pageable pageable = PageRequest.of(currentPage,10);
        Query query = new Query();
        query.with(pageable);
        query.addCriteria(Criteria.where("images").ne("").not().size(0));
        query.addCriteria(Criteria.where("_id").in(oid));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    @Override
    public Double getRate() {
        //查询汇率
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("addtime")));
        ExchangeRate rate = mongoTemplate.findOne(query, ExchangeRate.class, "sc_usd_rate");
        if (rate != null) {
            String cnyStr = rate.getCny();
            Double cny = Double.parseDouble(cnyStr);
            return cny;
        } else {
            return 1.00;
        }
    }

    @Override
    public List<Product> queryProductPageList(ScProductQueryDto queryDto) {
        List<AggregationOperation> aggregationOperations = new LinkedList<>();
        //分页
        SkipOperation skip = Aggregation.skip((long) (queryDto.getPageNumber() - 1) * queryDto.getPageSize());
        LimitOperation limit = Aggregation.limit(queryDto.getPageSize());
        aggregationOperations.add(skip);
        aggregationOperations.add(limit);
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        AggregationResults<Product> detailResults = mongoTemplate.aggregate(aggregation, "sc_product", Product.class);
        List<Product> mappedResults = detailResults.getMappedResults();
        return mappedResults;
    }

}
