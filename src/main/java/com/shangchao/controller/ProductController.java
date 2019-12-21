package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.repository.ProductRepository;
import com.shangchao.entity.Product;
import com.shangchao.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

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
     * @return
     */
    @GetMapping("/id")
    public JSONObject getById(String productId){
        JSONObject jo = new JSONObject();
        Optional<Product> product = productService.getById(productId);
        jo.put("product", product);
        return jo;
    }
}
