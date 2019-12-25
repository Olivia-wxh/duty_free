package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.Product;
import com.shangchao.service.ProductService;
import io.swagger.annotations.Api;
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
  public JSONObject getById(String productId) {
    JSONObject jo = new JSONObject();
    Product product = productService.getById(productId);
    jo.put("product", product);
    return jo;
  }

  /**
   * 根据主题ID查询产品信息
   *
   * @return
   */
  @GetMapping("/topicId")
  public JSONObject getByTopic(String topicId) {
    JSONObject jo = new JSONObject();
    List<Product> product = productService.getByTopic(topicId);
    jo.put("product", product);
    return jo;
  }
}
