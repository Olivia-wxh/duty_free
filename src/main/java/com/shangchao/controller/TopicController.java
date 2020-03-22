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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
@Api(tags = "专题管理相关接口", description = "如有疑问请联系王晓辉")
public class TopicController {

  @Autowired private TopicService topicService;

  /** delete topic */
//  @DeleteMapping("/delete")
//  @ApiOperation("server-删除专题的接口")
//  @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
//  public JSONObject delTopic(String topicId) {
//    DeleteResult deleteResult = topicService.deleteTopicById(topicId);
//    return ResponseUtil.success(deleteResult);
//  }

  /** 添加或修改专题 */
//  @PostMapping("/submit")
//  @ApiOperation("server-添加/修改专题的接口")
//  @ApiImplicitParams({
//    @ApiImplicitParam(name = "topicId", value = "专题ID"),
//    @ApiImplicitParam(name = "topicName", value = "专题名称", required = true)
//  })
//  public JSONObject updateTopic(@RequestBody JSONObject json) {
//    String topicName = json.getString("topicName").toString();
//    String topicId = json.getString("topicId");
//    Topic topic = new Topic();
//    if (topicId != null) {
//      topic.setId(topicId);
//    }
//    topic.setTopicName(topicName);
//    Topic t = topicService.saveOrUpdateTopic(topic);
//    return ResponseUtil.success(t);
//  }

//  /** 专题查询1：查询所有专题 */
//  @GetMapping("/all")
//  @ApiOperation("查询所有专题的接口")
//  public JSONObject getAllTopic() {
//    List<Topic> topicList = topicService.getAllTopic();
//    return ResponseUtil.success(topicList);
//  }

  /** 专题查询2：查询所有专题,同时查询每个专题下所有商品 */
  @GetMapping("/topicPro")
  @ApiOperation(value = "client-获取首页", notes = "currentPage表示的是当前页数（必传），从0开始；" +
          "每次请求返回10个专题，以及专题下所有的商品信息，点击专题进入专题下商品二级页面无需再次请求接口；" +
          "第一页即currentPage=0时需要展示轮播图；" +
          "每个专题的imgUrl字段存储一张代表当前专题的图片，用于轮播图展示；")
  @ApiImplicitParam(name = "currentPage", value = "当前页数")
  public JSONObject getTopicWithProduct(@RequestParam Integer currentPage) {
    System.out.println("start-time:" + new Date(System.currentTimeMillis()));
    Integer pageSize = 10;
    JSONObject in = new JSONObject();
//    List<Topic> topicList = topicService.getTopicWithProduct();
    //查找专题
    List<Topic> topicList = topicService.getByPage(currentPage, pageSize);
    System.out.println("getTopic-time:" + new Date(System.currentTimeMillis()));
    in.put("topic", topicList);
    //查找专题轮播图
    List<TopicImage> imageList = topicService.getImages();
    in.put("images", imageList);
    System.out.println("end-time:" + new Date(System.currentTimeMillis()));
    return ResponseUtil.success(in);
  }

  /** 专题查询2：根据ID查询专题,同时查询当前专题下所有商品 */
  @GetMapping("/topicPro/one")
  @ApiOperation("根据ID查询专题,同时查询当前专题下所有商品")
  @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
  public JSONObject getTopicWithProduct(@RequestParam String topicId) {
    JSONObject jo = topicService.getTopicWithProduct(topicId);
    return ResponseUtil.success(jo);
  }

  /**
   * 给产品指定专题
   *
   * @return
   */
//  @PostMapping("/setTopic")
//  @ApiOperation("给商品指定专题的接口")
//  @ApiImplicitParams({
//          @ApiImplicitParam(name = "topicId", value = "专题ID", required = true),
//          @ApiImplicitParam(name = "productId", value = "商品ID", required = true)
//  })
//  public JSONObject setTopic(@RequestBody JSONObject json) {
//    String topicId = json.getString("topicId");
//    String productId = json.getString("productId");
//    UpdateResult topic = topicService.setTopic(topicId, productId);
//    return ResponseUtil.success(topic);
//  }

  @GetMapping("/resetTopic")
  public void resetTopic(){
    topicService.resetTopic();
  }
}
