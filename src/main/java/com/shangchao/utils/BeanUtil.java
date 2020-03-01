package com.shangchao.utils;

import com.shangchao.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeanUtil {

    public static List<Product> exeType(List<Product> products, Double cny) {
        List<Product> temp = new ArrayList<>();
        for (int j = 0; j < products.size(); j++) {
            Product byId = products.get(j);
            //计算卖价
            Double priceOff = byId.getPriceOff();
            Double price = byId.getPrice();
            if (priceOff != null && !"".equals(priceOff)) {
                byId.setPriceRMB(Double.parseDouble(String.format("%.2f", price * cny)));//人民币原价
                byId.setDollar(Double.parseDouble(String.format("%.2f", price * priceOff)));
                byId.setRmb(Double.parseDouble(String.format("%.2f", price * priceOff * cny)));
            } else {
                byId.setPriceRMB(Double.parseDouble(String.format("%.2f", price * cny)));//人民币原价
                byId.setDollar(price);
                byId.setRmb(Double.parseDouble(String.format("%.2f", price * cny)));
            }
            //封装infos
            String[][] infos = byId.getInfos();
            if (infos != null) {
                String in = "";
                for (int aa = 0; aa < infos.length; aa++) {
                    in = in + infos[aa][0] + ":" + infos[aa][1] + "。\n";
                }
//                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Pattern p = Pattern.compile("\r");
                Matcher m = p.matcher(in);
                in = m.replaceAll("");
                byId.setProductInfo(in);
                byId.setInfos(null);
            }
            //图片信息为空的数据过滤掉
            List<String> images = byId.getImages();
            if (images.size() > 0) {
                if (images.size() == 1) {
                    if (!"".equals(images.get(0).trim())) {
                        temp.add(byId);
                    }
                } else {
                    temp.add(byId);
                }
            }
        }
        return temp;
    }
}
