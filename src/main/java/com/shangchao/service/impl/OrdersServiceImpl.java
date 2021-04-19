package com.shangchao.service.impl;

import com.shangchao.dao.IOrdersDAO;
import com.shangchao.dao.IStatisticsDao;
import com.shangchao.dao.IStockDAO;
import com.shangchao.dao.IStockImagesDAO;
import com.shangchao.entity.*;
import com.shangchao.service.OrdersService;
import com.shangchao.service.ProductService;
import com.shangchao.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    IOrdersDAO ordersDAO;
    @Resource
    IStatisticsDao statisticsDao;
    @Autowired
    private ProductService productService;

    @Override
    public Boolean save(Orders orders) {
        Boolean b = false;
        Integer i = ordersDAO.save(orders);
        if (i == 1) {
            b = true;
            LocalDate now = LocalDate.now();
            Integer year = now.getYear();
            Integer month = now.getMonthValue();
            Statistics byTime = statisticsDao.findByTime(year, month);
            Product product = productService.getById(orders.getProductId());
            if (byTime == null) {
                Statistics s = new Statistics();
                s.setYear(year);
                s.setMonth(month);
                s.setSalePrice(orders.getPrice());
                double val = orders.getCount() * product.getPrice();
                s.setProfit(val - orders.getPrice());
                statisticsDao.save(s);
            } else {
                byTime.setSalePrice(byTime.getSalePrice() + orders.getPrice());
                double val = orders.getCount() * product.getPrice();
                double profit = val - orders.getPrice();
                byTime.setProfit(byTime.getProfit() + profit);
                statisticsDao.update(byTime);
            }
        }
        return b;
    }

    @Override
    public List<Orders> findAll(String userId) {
        List<Orders> orders = ordersDAO.findAll(userId);
        return orders;
    }

    @Override
    public Boolean deleteById(Integer id) {
        Orders orders = ordersDAO.findById(id);
        Integer i = ordersDAO.deleteById(id);
        if (i == 1) {
            LocalDate now = LocalDate.now();
            Integer year = now.getYear();
            Integer month = now.getMonthValue();
            Statistics byTime = statisticsDao.findByTime(year, month);
            Product product = productService.getById(orders.getProductId());
            if (byTime != null) {
                byTime.setSalePrice(byTime.getSalePrice() - orders.getPrice());
                double val = orders.getCount() * product.getPrice();
                double profit = val - orders.getPrice();
                byTime.setProfit(byTime.getProfit() - profit);
                statisticsDao.update(byTime);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Statistics> findStatistics(Integer year) {
        List<Statistics> res = statisticsDao.findStatistics(year);
        return res;
    }

}
