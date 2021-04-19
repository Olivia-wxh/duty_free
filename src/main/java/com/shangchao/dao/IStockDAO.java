package com.shangchao.dao;

import com.shangchao.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStockDAO {
    Integer save(Stock stock);

    List<Stock> findAll();

    Integer update(Stock stock);

    Stock findByProductId(String productId);

    Integer deleteByProductId(String productId);
}
