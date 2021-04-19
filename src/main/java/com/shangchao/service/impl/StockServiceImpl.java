package com.shangchao.service.impl;

import com.shangchao.dao.IStockDAO;
import com.shangchao.dao.IStockImagesDAO;
import com.shangchao.entity.Stock;
import com.shangchao.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    IStockDAO stockDao;
    @Resource
    IStockImagesDAO imagesDao;

    @Override
    public Integer save(Stock stock) {
        Stock ss = stockDao.findByProductId(stock.getProductId());
        if (ss != null) {
            return 2;
        }
        Integer i = stockDao.save(stock);
        if (i == 1) {
            //添加关联表
            if (stock.getImagesList().size() > 0) {
                imagesDao.saveBatch(stock.getImagesList());
            }
            return 1;
        }
        return 0;
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> stockList = stockDao.findAll();
        return stockList;
    }

    @Override
    public Boolean update(Stock stock) {
        Integer i = stockDao.update(stock);
        if (i == 1) {
            //添加关联表
            imagesDao.deleteByProductId(stock.getProductId());
            if (stock.getImagesList().size() > 0) {
                imagesDao.saveBatch(stock.getImagesList());
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteByProductId(String productId) {
        Integer result = stockDao.deleteByProductId(productId);
        if (result == 1) {
            imagesDao.deleteByProductId(productId);
            return true;
        }
        return false;
    }
}
