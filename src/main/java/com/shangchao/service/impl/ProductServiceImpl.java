package com.shangchao.service.impl;

import com.shangchao.entity.ExchangeRate;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.repository.ProductRepository;
import com.shangchao.repository.TopicRepository;
import com.shangchao.service.ProductService;
import com.shangchao.utils.BeanUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductServiceImpl implements ProductService {

  @Resource private ProductRepository productRepository;
  @Resource private TopicRepository topicRepository;

  @Override
  public Product getById(String productId) {
      Product product = productRepository.findById(productId);
      if (product != null) {
          Double cny = productRepository.getRate();
          List<Product> temp = new ArrayList<>();
          temp.add(product);
          temp = BeanUtil.exeType(temp, cny);
          return temp.get(0);
//          //计算卖价
//          Double priceOff = product.getPriceOff();
//          Double price = product.getPrice();
//          if (priceOff != null && !"".equals(priceOff)) {
//              product.setPriceRMB(Double.parseDouble(String.format("%.2f", price * cny)));//人民币原价
//              product.setDollar(Double.parseDouble(String.format("%.2f", price * priceOff)));
//              product.setRmb(Double.parseDouble(String.format("%.2f", price * priceOff * cny)));
//          } else {
//              product.setPriceRMB(Double.parseDouble(String.format("%.2f", price * cny)));//人民币原价
//              product.setDollar(price);
//              product.setRmb(Double.parseDouble(String.format("%.2f", price * cny)));
//          }
//          //封装infos
//          String[][] infos = product.getInfos();
//          if (infos != null) {
////                        JSONObject jo = new JSONObject();
//              String in = "";
//              for (int aa = 0; aa < infos.length; aa++) {
////                            jo.put(infos[aa][0], infos[aa][1]);
//                  in = in + infos[aa][0] + ":" + infos[aa][1] + "。";
//              }
//              Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//              Matcher m = p.matcher(in);
//              in = m.replaceAll("");
//              product.setProductInfo(in);
//              product.setInfos(null);
//          }
      }
      return product;
  }

  @Override
  public List<Product> getByTopic(String topicId) {
      Topic topic = topicRepository.findById(topicId.toString());
      List<Product> list = new ArrayList<>();
      for (int i = 0; i < topic.getProductIds().length; i++) {
        Product byId = productRepository.findById(topic.getProductIds()[i].toString());
        list.add(byId);
      }
    return list;
  }


}
