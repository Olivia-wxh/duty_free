package com.shangchao.repository.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shangchao.entity.ExchangeRate;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.entity.dto.ScProductQueryDto;
import com.shangchao.repository.ProductRepository;
import org.apache.commons.collections.map.HashedMap;
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

import java.util.*;

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
        List<String> distinct = mongoTemplate.findDistinct(new Query().with(Sort.by(Sort.Order.asc("brandName"))), "brandName", "sc_product", String.class);
        Collections.sort(distinct, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });
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
        Pageable pageable = PageRequest.of(0,3);
        query.addCriteria(Criteria.where("images").ne("").not().size(0));
        query.fields().include("priceRMB");
        query.fields().include("brandName");
        query.fields().include("priceOff");
        query.fields().include("price");
        query.fields().include("images");
        query.fields().include("productName");
        query.fields().include("dollar");
        query.fields().include("rmb");
        query.with(pageable);
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

//    @Cacheable(value = "pros")
//    @Override
//    public List<Product> findProductByBrandLimit(ObjectId[] oid) {
//        Pageable pageable = PageRequest.of(0,3);
//        Query query = new Query();
//        query.addCriteria(Criteria.where("images").ne("").not().size(0));
//        query.addCriteria(Criteria.where("_id").in(oid));
//        query.fields().include("priceRMB");
//        query.fields().include("brandName");
//        query.fields().include("priceOff");
//        query.fields().include("price");
//        query.fields().include("images");
//        query.with(pageable);
//        List<Product> products = mongoTemplate.find(query, Product.class);
//        return products;
//    }

    @Cacheable(value = "pages")
    @Override
    public List<Product> findProductByPage(ObjectId[] oid, Integer currentPage) {
        Pageable pageable = PageRequest.of(currentPage,10);
        Query query = new Query();
        query.with(pageable);
        query.addCriteria(Criteria.where("images").ne("").not().size(0));
        query.addCriteria(Criteria.where("_id").in(oid));
//        query.fields().include("priceRMB");
//        query.fields().include("brandName");
//        query.fields().include("priceOff");
//        query.fields().include("price");
//        query.fields().include("images");
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

    @Override
    public List<String> getBrandsWithName(String brandName) {
        Query query = new Query(Criteria.where("brandName").regex(brandName));
        List<String> distinct = mongoTemplate.findDistinct(query, "brandName", "sc_product", String.class);
        Collections.sort(distinct, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });
        return distinct;
    }

    @Override
    public List<Product> getBrandsByName(String brandName) {
        Query query = new Query(Criteria.where("brandName").regex(brandName));
        List<Product> distinct = mongoTemplate.find(query, Product.class);
        return distinct;
    }

    @Override
    public List<Product> findByBrandNameUnlimit(String s) {
        Query query = new Query(Criteria.where("brandName").is(s));
        Pageable pageable = PageRequest.of(0,500);
        query.addCriteria(Criteria.where("images").ne("").not().size(0));
        query.fields().include("priceRMB");
        query.fields().include("brandName");
        query.fields().include("priceOff");
        query.fields().include("price");
        query.fields().include("images");
        query.fields().include("productName");
        query.fields().include("dollar");
        query.fields().include("rmb");
        query.with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

}
