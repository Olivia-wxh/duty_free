package com.shangchao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.service.CollectionService;
import com.shangchao.service.TopicService;
import com.shangchao.utils.ResponseUtil;
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
        String userId = params.getString("userId").toString();
        String topicId = params.getString("topicId").toString();
        CollectTopic collectTopic = collectionService.saveCollectionTopic(userId, topicId);
        if (collectTopic == null) {
            //收藏失败
            return ResponseUtil.fail("专题不能重复收藏");
        } else {
            //收藏成功
            return ResponseUtil.success(collectTopic);
        }
    }

    /**
     * 收藏专题列表查询
     */
    @GetMapping("/topics")
    @ApiOperation(value = "收藏专题列表查询的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject getTopics(String userId){
        JSONObject collectTopic = collectionService.getTopics(userId);
        return ResponseUtil.success(collectTopic);
    }

    /**
     * 删除收藏的专题
     */
    @GetMapping("/delCollectTopic")
    @ApiOperation(value = "取消收藏的专题的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
    })
    public JSONObject delCollectionTopic(String userId, String topicId){
        DeleteResult deleteResult = collectionService.delCollectionTopic(userId, topicId);
        return ResponseUtil.success(deleteResult);
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
        String userId = params.getString("userId").toString();
        String productId = params.getString("productId").toString();
        CollectProduct collectProduct = collectionService.saveCollectionProduct(userId, productId);
        if (collectProduct == null) {
            //收藏失败
            return ResponseUtil.fail("商品不能重复收藏");
        } else {
            //收藏成功
            return ResponseUtil.success(collectProduct);
        }
    }

    /**
     * 收藏商品列表查询
     */
    @GetMapping("/products")
    @ApiOperation(value = "收藏商品列表查询的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject getProducts(String userId){
        JSONObject collectProduct = collectionService.getProduct(userId);
        return ResponseUtil.success(collectProduct);
    }

    /**
     * 删除收藏的商品
     */
    @PostMapping("/delCollectProduct")
    @ApiOperation(value = "取消收藏的商品的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productIds", value = "商品id", allowMultiple=true, required = true)
    })
    public JSONObject delCollectionProduct(@RequestBody JSONObject params){//String userId, String productId
        String userId = params.getString("userId").toString();
        JSONArray jsonArray = params.getJSONArray("productIds");
        String jsonStr = JSONObject.toJSONString(jsonArray);
        List<String> productIds = JSONObject.parseArray(jsonStr,  String.class);
        DeleteResult deleteResult = collectionService.delCollectionProduct(userId, productIds);
        return ResponseUtil.success(deleteResult);
    }
}
