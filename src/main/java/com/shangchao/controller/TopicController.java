package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.Topic;
import com.shangchao.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
@Api(tags = "专题管理相关接口", description = "如有疑问请联系王晓辉")
public class TopicController {

  @Autowired private TopicService topicService;

  /** delete topic */
  @DeleteMapping("/delete")
  @ApiOperation("删除专题的接口")
  @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
  public JSONObject delTopic(String topicId) {
    JSONObject jo = new JSONObject();
    DeleteResult deleteResult = topicService.deleteTopicById(topicId);
    jo.put("result", deleteResult);
    return jo;
  }

  /** 添加或修改专题 */
  @PostMapping("/submit")
  @ApiOperation("添加/修改专题的接口")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "topicId", value = "专题ID"),
    @ApiImplicitParam(name = "topicName", value = "专题名称", required = true)
  })
  public JSONObject updateTopic(@RequestBody JSONObject json) {
    JSONObject jo = new JSONObject();
    Topic t = topicService.saveOrUpdateTopic(json);
    jo.put("topic", t);
    return jo;
  }

  /** 专题查询1：查询所有专题 */
  @GetMapping("/all")
  @ApiOperation("查询所有专题的接口")
  public JSONObject getAllTopic() {
    JSONObject jo = new JSONObject();
    List<Topic> topicList = topicService.getAllTopic();
    jo.put("data", topicList);
    return jo;
  }

  /** 专题查询2：查询所有专题,同时查询每个专题下所有商品 */
  @GetMapping("/topicPro")
  @ApiOperation("查询所有专题,同时查询每个专题下所有商品")
  public JSONObject getTopicWithProduct() {
    JSONObject jo = new JSONObject();
    JSONObject in = new JSONObject();
    List<Topic> topicList = topicService.getTopicWithProduct();
    // 查询轮播图路径
    List<String> list = new ArrayList<>();
    list.add("/images/a.jpg");
    list.add("/images/b.jpg");
    list.add("/images/c.jpg");
    list.add("/images/d.jpg");
    in.put("topic", topicList);
    in.put("picture", list);
    jo.put("data", in);
    return jo;
  }

  /** 专题查询2：根据ID查询专题,同时查询当前专题下所有商品 */
  @GetMapping("/topicPro/one")
  @ApiOperation("根据ID查询专题,同时查询当前专题下所有商品")
  @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
  public JSONObject getTopicWithProduct(String topicId) {
    JSONObject jo = new JSONObject();
    Topic topicList = topicService.getTopicWithProduct(topicId);
    jo.put("data", topicList);
    return jo;
  }
}
