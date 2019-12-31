package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.Product;
import com.shangchao.service.ProductService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Api(tags = "产品管理相关接口", description = "如有疑问请联系王晓辉")
public class ProductController {

  @Autowired private ProductService productService;

  //    /**
  //     * 查询所有产品信息
  //     * @return
  //     */
  //    @GetMapping("/all")
  //    public JSONObject getAll(){
  //        JSONObject jo = new JSONObject();
  //        List<Product> all = productRepository.findAll();
  //        jo.put("list", all);
  //        return jo;
  //    }

  /**
   * 根据ID查询单品信息
   *
   * @return
   */
  @GetMapping("/id")
  @ApiOperation("根据商品ID获取商品信息")
  @ApiImplicitParam(name = "productId", value = "商品id", required = true)
  public JSONObject getById(String productId) {
    Product product = productService.getById(productId);
    return ResponseUtil.success(product);
  }

  /**
   * 根据主题ID查询产品信息
   *
   * @return
   */
  @GetMapping("/topicId")
  @ApiOperation("根据专题ID获取商品集信息")
  @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
  public JSONObject getByTopic(String topicId) {
    List<Product> product = productService.getByTopic(topicId);
    return ResponseUtil.success(product);
  }


}
