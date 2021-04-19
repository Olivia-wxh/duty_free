package com.shangchao.dao;

import com.shangchao.entity.Orders;
import com.shangchao.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdersDAO {
    Integer save(Orders orders);

    List<Orders> findAll(String userId);

    Integer deleteById(Integer id);

    Orders findById(Integer id);
}
