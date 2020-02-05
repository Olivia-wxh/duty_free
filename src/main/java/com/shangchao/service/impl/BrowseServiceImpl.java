package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;
import com.shangchao.entity.Product;
import com.shangchao.repository.BrowseRepository;
import com.shangchao.repository.ProductRepository;
import com.shangchao.service.BrowseService;
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
        List<Product> result = new ArrayList<>();
        List<BrowseProduct> browseProducts = browseRepository.getProducts(userId);
        for (int i = 0; i < browseProducts.size(); i++) {
            Product product = productRepository.findById(browseProducts.get(i).getProductId());
            if (product != null) {
                //封装infos
                String[][] infos = product.getInfos();
                if (infos != null) {
//                        JSONObject jo = new JSONObject();
                    String in = "";
                    for (int aa = 0; aa < infos.length; aa++) {
//                            jo.put(infos[aa][0], infos[aa][1]);
                        in = in + infos[aa][0] + ":" + infos[aa][1] + "。";
                    }
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(in);
                    in = m.replaceAll("");
                    product.setProductInfo(in);
                    product.setInfos(null);
                }
                result.add(product);
            }
        }
        jo.put("products", result);
        return jo;
    }

    @Override
    public DeleteResult delBrowseProduct(String productId) {
        DeleteResult deleteResult = browseRepository.removeProduct(productId);
        return deleteResult;
    }
}
