package com.shangchao.service;

import com.shangchao.entity.Orders;
import com.shangchao.entity.Statistics;
import com.shangchao.entity.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrdersService {

    Boolean save(Orders orders);

    List<Orders> findAll(String userId);

    Boolean deleteById(Integer id);

    List<Statistics> findStatistics(Integer year);
}
