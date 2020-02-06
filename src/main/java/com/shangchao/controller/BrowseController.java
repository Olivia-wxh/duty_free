package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.service.BrowseService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/browse")
@Api(tags = "浏览相关接口", description = "如有疑问请联系王晓辉")
public class BrowseController {

    @Resource
    private BrowseService browseService;

    /**
     * 添加一条浏览记录
     */
    @PostMapping("/save")
    @ApiOperation(value = "添加浏览商品的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    })
    public JSONObject saveCollectionTopic(@RequestBody JSONObject params){
        String userId = params.getString("userId").toString();
        String productId = params.getString("productId").toString();
        BrowseProduct browseProduct = browseService.save(userId, productId);
        return ResponseUtil.success(browseProduct);
    }

    /**
     * 收藏专题列表查询
     */
    @GetMapping("/find")
    @ApiOperation(value = "查询浏览的商品的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public JSONObject getTopics(String userId){
        JSONObject collectTopic = browseService.getProducts(userId);
        return ResponseUtil.success(collectTopic);
    }

    /**
     * 删除收藏的专题
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除浏览的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    })
    public JSONObject delBrowseProduct(String userId, String productId){
        DeleteResult deleteResult = browseService.delBrowseProduct(userId, productId);
        return ResponseUtil.success(deleteResult);
    }
}
