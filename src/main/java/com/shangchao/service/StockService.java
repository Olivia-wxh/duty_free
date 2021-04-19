package com.shangchao.service;

import com.shangchao.entity.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {

    Integer save(Stock stock);

    List<Stock> findAll();

    Boolean update(Stock stock);

    Boolean deleteByProductId(String productId);
}
