package com.shangchao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.BrowseTopic;
import com.shangchao.entity.CollectTopic;
import com.shangchao.service.BrowseService;
import com.shangchao.service.SystemService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/browse")
@Api(tags = "浏览相关接口", description = "如有疑问请联系王晓辉")
public class BrowseController {

    @Resource
    private BrowseService browseService;

    /**
     * 添加一个商品的浏览记录
     */
    @PostMapping("/save")
    @ApiOperation(value = "添加一个商品的浏览记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    })
    public JSONObject saveCollectionProduct(@RequestBody JSONObject params){
        String userId = params.getString("userId").toString();
        String productId = params.getString("productId").toString();
        BrowseProduct browseProduct = browseService.save(userId, productId, "browse_product");
        if (browseProduct == null) {
            return ResponseUtil.fail("数据已存在");
        } else {
            return ResponseUtil.success(browseProduct);
        }
    }

    /**
     * 查询浏览的商品的接口（方案1）
     */
    @GetMapping("/find")
    @ApiOperation(value = "查询浏览的商品的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject getProducts(String userId){
        JSONObject collectTopic = browseService.getProducts(userId);
        return ResponseUtil.success(collectTopic);
    }

    /**
     * 删除一个商品的浏览记录
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除商品的浏览记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productIds", value = "商品id", required = true)
    })
    public JSONObject delBrowseProduct(@RequestBody JSONObject params){
        String userId = params.getString("userId").toString();
        JSONArray jsonArray = params.getJSONArray("productIds");
        String jsonStr = JSONObject.toJSONString(jsonArray);
        List<String> productIds = JSONObject.parseArray(jsonStr,  String.class);
        DeleteResult deleteResult = browseService.delBrowse(userId, productIds, "browse_product");
        return ResponseUtil.success(deleteResult);
    }

    /**
     * 添加一条专题的浏览记录
     */
    @PostMapping("/saveTopic")
    @ApiOperation(value = "添加一个专题的浏览记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
    })
    public JSONObject saveCollectionTopic(@RequestBody JSONObject params){
        String userId = params.getString("userId").toString();
        String topicId = params.getString("topicId").toString();
        BrowseTopic browseTopic = browseService.save(userId, topicId, "browse_topic");
        if (browseTopic == null) {
            return ResponseUtil.fail("数据已存在");
        } else {
            return ResponseUtil.success(browseTopic);
        }
    }

    /**
     * 查询浏览的商品的接口
     */
    @GetMapping("/findTopics")
    @ApiOperation(value = "查询浏览的专题的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject getTopics(String userId){
        JSONObject collectTopic = browseService.getTopics(userId);
        return ResponseUtil.success(collectTopic);
    }

    /**
     * 删除一个商品的浏览记录
     */
    @GetMapping("/deleteTopic")
    @ApiOperation(value = "删除一条专题的浏览记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "topicId", value = "商品id", required = true)
    })
    public JSONObject delBrowseTopic(String userId, String topicId){
        List<String> ids = new ArrayList<>();
        ids.add(topicId);
        DeleteResult deleteResult = browseService.delBrowse(userId, ids, "browse_topic");
        return ResponseUtil.success(deleteResult);
    }
}
