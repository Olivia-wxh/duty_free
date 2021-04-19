package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.Product;
import com.shangchao.entity.Stock;
import com.shangchao.entity.StockImages;
import com.shangchao.service.StockService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
@Api(tags = "库存管理相关接口", description = "如有疑问请联系王晓辉")
public class StockController {

    @Resource
    StockService stockService;

    @PostMapping("/submit")
    @ApiOperation("添加或者编辑库存")
    public JSONObject save(@RequestBody Product product, String info,
                           Double unitPrice, Integer count, Integer id) {
        Stock stock = new Stock();
        stock.setBrandId(product.getBrandId());
        stock.setBrandName(product.getBrandName());
        stock.setProductId(product.getId());
        stock.setProductName(product.getProductName());
        stock.setInfos(info);
        stock.setUnitPrice(unitPrice);
        stock.setCount(count);
        List<StockImages> images = new ArrayList<>();
        List<String> imagesTemp = product.getImages();
        if (!CollectionUtils.isEmpty(imagesTemp)) {
            for (int i = 0; i < imagesTemp.size(); i++) {
                StockImages si = new StockImages();
                si.setProductId(product.getId());
                si.setImageUrl(imagesTemp.get(i));
                images.add(si);
            }
        }
        stock.setImagesList(images);
        Boolean b = false;
        if (id == null || "".equals(id)) {
            Integer save = stockService.save(stock);
            if (save == 1) {
                b = true;
            } else if (save == 2) {
                return ResponseUtil.fail("当前产品已有库存,请不要重复创建");
            }
        } else {
            stock.setId(id);
            b = stockService.update(stock);
        }
        return ResponseUtil.status(b);
    }

    @GetMapping("/findAll")
    @ApiOperation("查询所有库存")
    public JSONObject findAll() {
        List<Stock> stockList = stockService.findAll();
        return ResponseUtil.success(stockList);
    }

    @GetMapping("/delete")
    @ApiOperation("查询某商品的库存")
    public JSONObject delete(String productId) {
        Boolean b = stockService.deleteByProductId(productId);
        return ResponseUtil.status(b);
    }
}
