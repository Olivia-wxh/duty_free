package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.Product;
import com.shangchao.repository.BrowseRepository;
import com.shangchao.repository.ProductRepository;
import com.shangchao.service.BrowseService;
import com.shangchao.utils.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BrowseServiceImpl implements BrowseService {

    @Resource
    private BrowseRepository browseRepository;

    @Resource
    private ProductRepository productRepository;

    @Override
    public BrowseProduct save(String userId, String productId) {
        BrowseProduct bp = new BrowseProduct();
        bp.setUserId(userId);
        bp.setProductId(productId);
        BrowseProduct browseProduct = browseRepository.save(bp);
        return browseProduct;
    }

    @Override
    public JSONObject getProducts(String userId) {
        JSONObject jo = new JSONObject();
        Double cny = productRepository.getRate();
        List<Product> temp = new ArrayList<>();
        List<Product> result = new ArrayList<>();
        List<BrowseProduct> browseProducts = browseRepository.getProducts(userId);
        for (int i = 0; i < browseProducts.size(); i++) {
            Product product = productRepository.findById(browseProducts.get(i).getProductId());
            if (product != null) {
                temp.clear();
                temp.add(product);
                temp = BeanUtil.exeType(temp, cny);
                result.add(temp.get(0));
            }
        }
        jo.put("products", result);
        return jo;
    }

    @Override
    public DeleteResult delBrowseProduct(String userId, String productId) {
        DeleteResult deleteResult = browseRepository.removeProduct(userId, productId);
        return deleteResult;
    }
}
