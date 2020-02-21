package com.shangchao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;
import com.shangchao.service.BrowseService;
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


}
