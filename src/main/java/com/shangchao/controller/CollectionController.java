package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.service.CollectionService;
import com.shangchao.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/collection")
@Api(tags = "收藏管理相关接口", description = "如有疑问请联系王晓辉")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 收藏专题
     */
    @PostMapping("/collectTopic")
    @ApiOperation(value = "收藏专题的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
    })
    public JSONObject saveCollectionTopic(@RequestBody JSONObject params){
        JSONObject jo = new JSONObject();
        String userId = params.getString("userId").toString();
        String topicId = params.getString("topicId").toString();
        CollectTopic collectTopic = collectionService.saveCollectionTopic(userId, topicId);
        jo.put("data", collectTopic);
        return jo;
    }

    /**
     * 收藏专题列表查询
     */
    @GetMapping("/topics")
    @ApiOperation(value = "收藏专题列表查询的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject getTopics(String userId){
        JSONObject jo = new JSONObject();
        JSONObject collectTopic = collectionService.getTopics(userId);
        jo.put("data", collectTopic);
        return jo;
    }

    /**
     * 删除收藏的专题
     */
    @GetMapping("/delCollectTopic")
    @ApiOperation(value = "删除收藏的专题的接口")
    @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
    public JSONObject delCollectionTopic(String topicId){
        JSONObject jo = new JSONObject();
        DeleteResult deleteResult = collectionService.delCollectionTopic(topicId);
        jo.put("data", deleteResult);
        return jo;
    }

    /**
     * 收藏商品
     */
    @PostMapping("/collectProduct")
    @ApiOperation(value = "收藏商品的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    })
    public JSONObject saveCollectionProduct(@RequestBody JSONObject params){
        JSONObject jo = new JSONObject();
        String userId = params.getString("userId").toString();
        String productId = params.getString("productId").toString();
        CollectProduct collectProduct = collectionService.saveCollectionProduct(userId, productId);
        jo.put("data", collectProduct);
        return jo;
    }

    /**
     * 收藏商品列表查询
     */
    @GetMapping("/products")
    @ApiOperation(value = "收藏商品列表查询的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject getProducts(String userId){
        JSONObject jo = new JSONObject();
        JSONObject collectProduct = collectionService.getProduct(userId);
        jo.put("data", collectProduct);
        return jo;
    }

    /**
     * 删除收藏的商品
     */
    @GetMapping("/delCollectProduct")
    @ApiOperation(value = "删除收藏的商品的接口")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    public JSONObject delCollectionProduct(String productId){
        JSONObject jo = new JSONObject();
        DeleteResult deleteResult = collectionService.delCollectionProduct(productId);
        jo.put("data", deleteResult);
        return jo;
    }
}
