package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.*;
import com.shangchao.service.OrdersService;
import com.shangchao.service.StockService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Api(tags = "账单管理相关接口", description = "如有疑问请联系王晓辉")
public class OrdersController {

    @Resource
    OrdersService ordersService;

    @PostMapping("/submit")
    @ApiOperation("创建订单:非必填的参数都不要填")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户ID", name = "userId", dataType = "String", required = true),
            @ApiImplicitParam(value = "产品ID", name = "productId", dataType = "String", required = true),
            @ApiImplicitParam(value = "产品名称", name = "productName", dataType = "String", required = true),
            @ApiImplicitParam(value = "品牌ID", name = "brandId", dataType = "String", required = true),
            @ApiImplicitParam(value = "品牌名称", name = "brandName", dataType = "String", required = true),
            @ApiImplicitParam(value = "数量", name = "count", dataType = "String", required = true),
            @ApiImplicitParam(value = "单价", name = "unitPrice", dataType = "String", required = true),
            @ApiImplicitParam(value = "地址", name = "address", dataType = "String", required = true)
    })
    public JSONObject save(Orders orders) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        orders.setYear(year);
        orders.setMonth(monthValue);
        orders.setPrice(orders.getUnitPrice() * orders.getCount());
        Boolean b = ordersService.save(orders);
        return ResponseUtil.status(b);
    }

    @GetMapping("/findAll")
    @ApiOperation("查询某个用户所有订单")
    public JSONObject findAll(String userId) {
        List<Orders> oList = ordersService.findAll(userId);
        return ResponseUtil.success(oList);
    }

    @GetMapping("/delete")
    @ApiOperation("删除订单")
    public JSONObject delete(Integer id) {
        Boolean b = ordersService.deleteById(id);
        return ResponseUtil.status(b);
    }

    @GetMapping("/order_s")
    @ApiOperation("账单统计")
    public JSONObject findStatistics(Integer year) {
        List<Statistics> oList = ordersService.findStatistics(year);
        return ResponseUtil.success(oList);
    }
}
