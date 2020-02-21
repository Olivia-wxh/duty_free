package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.Topic;
import com.shangchao.entity.TopicImage;
import com.shangchao.service.TopicService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
@Api(tags = "专题管理相关接口", description = "如有疑问请联系王晓辉")
public class TopicController {


}
