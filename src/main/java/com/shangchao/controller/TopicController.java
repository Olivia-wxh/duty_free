package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shangchao.entity.Topic;
import com.shangchao.service.TopicService;
import com.shangchao.utils.ResponseUtil;
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
    DeleteResult deleteResult = topicService.deleteTopicById(topicId);
    return ResponseUtil.success(deleteResult);
  }

  /** 添加或修改专题 */
  @PostMapping("/submit")
  @ApiOperation("添加/修改专题的接口")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "topicId", value = "专题ID"),
    @ApiImplicitParam(name = "topicName", value = "专题名称", required = true)
  })
  public JSONObject updateTopic(@RequestBody JSONObject json) {
    Topic t = topicService.saveOrUpdateTopic(json);
    return ResponseUtil.success(t);
  }

  /** 专题查询1：查询所有专题 */
  @GetMapping("/all")
  @ApiOperation("查询所有专题的接口")
  public JSONObject getAllTopic() {
    List<Topic> topicList = topicService.getAllTopic();
    return ResponseUtil.success(topicList);
  }

  /** 专题查询2：查询所有专题,同时查询每个专题下所有商品 */
  @GetMapping("/topicPro")
  @ApiOperation("查询所有专题,同时查询每个专题下所有商品")
  public JSONObject getTopicWithProduct() {
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
    return ResponseUtil.success(in);
  }

  /** 专题查询2：根据ID查询专题,同时查询当前专题下所有商品 */
  @GetMapping("/topicPro/one")
  @ApiOperation("根据ID查询专题,同时查询当前专题下所有商品")
  @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
  public JSONObject getTopicWithProduct(String topicId) {
    JSONObject jo = topicService.getTopicWithProduct(topicId);
    return ResponseUtil.success(jo);
  }

  /**
   * 给产品指定专题
   *
   * @return
   */
  @PostMapping("/setTopic")
  @ApiOperation("给产品指定专题的接口")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "topicId", value = "专题ID", required = true),
          @ApiImplicitParam(name = "productId", value = "产品ID", required = true)
  })
  public JSONObject setTopic(@RequestBody JSONObject json) {
    UpdateResult topic = topicService.setTopic(json);
    return ResponseUtil.success(topic);
  }
}
