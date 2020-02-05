package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.CollectTopic;
import com.shangchao.service.BrowseService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    @PostMapping("/collectTopic")
//    @ApiOperation(value = "收藏专题的接口")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
//            @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
//    })
//    public JSONObject saveCollectionTopic(@RequestBody JSONObject params){
//        String userId = params.getString("userId").toString();
//        String topicId = params.getString("topicId").toString();
//        CollectTopic collectTopic = collectionService.saveCollectionTopic(userId, topicId);
//        return ResponseUtil.success(collectTopic);
//    }
}
