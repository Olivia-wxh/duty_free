package com.shangchao.dao;

import com.shangchao.entity.StockImages;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStockImagesDAO {
    List<StockImages> selectByProductId(String productId);

    Integer save(StockImages stockImages);

    Integer saveBatch(List<StockImages> imagesList);

    Integer deleteByProductId(String productId);
}
