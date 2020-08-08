package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shangchao.entity.ExchangeRate;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.entity.TopicImage;
import com.shangchao.repository.ProductRepository;
import com.shangchao.repository.TopicRepository;
import com.shangchao.service.TopicService;
import com.shangchao.utils.BeanUtil;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.bson.BsonValue;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource private TopicRepository topicRepository;

    @Resource private ProductRepository productRepository;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public DeleteResult deleteTopicById(String topicId) {
        DeleteResult re = topicRepository.deleteById(topicId);
        return re;
    }

    @Override
    public Topic saveOrUpdateTopic(Topic topic) {
        Topic save = topicRepository.saveOrUpdateTopic(topic);
        return save;
    }

    @Override
    public List<Topic> getAllTopic() {
        List<Topic> all = topicRepository.findAll();
        if (!CollectionUtils.isEmpty(all)) {
            for (int i = 0; i < all.size(); i++) {
                all.get(i).setProductIds(null);
            }
        }
        return all;
    }

    @Override
    public List<Topic> getTopicWithProduct() {
        List<Topic> all = topicRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            List<Product> temp = new ArrayList<>();
                ObjectId[] oid = all.get(i).getProductIds();
                if (oid != null) {
                    for (int j = 0; j < oid.length; j++) {
                        Product byId = productRepository.findById(oid[j].toString());
                        if (byId != null) {
                            temp.add(byId);
                        }
                    }
                }
            all.get(i).setProduct(temp);
            all.get(i).setProductIds(null);
        }
        return all;
    }

    @Override
    public JSONObject getTopicWithProduct(String topicId) {
        //查询汇率
        String cnyStr = "";
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("addtime")));
        List<ExchangeRate> rate = mongoTemplate.find(query, ExchangeRate.class, "sc_usd_rate");
        if(rate.size() == 0) {
            cnyStr = "0.00";
        } else {
            cnyStr = rate.get(0).getCny();
        }
        Double cny = Double.parseDouble(cnyStr);
        JSONObject jo = new JSONObject();
        Topic topic = topicRepository.findById(topicId.toString());
        if (topic != null) {
            ObjectId[] oid = topic.getProductIds();
            List<Product> temp = new ArrayList<>();
            List<Product> products = productRepository.findProductByBrand(oid);
//            for (int i = 0; i < products.size(); i++) {
//                if (products.get(i).getImages().size() > 0) {
//                    if (products.get(i).getImages().size() == 1) {
//                        if (!"".equals(products.get(i).getImages().get(0)) && products.get(i).getImages().get(0) != null) {
//                            temp.add(products.get(i));
//                        }
//                    } else {
//                        temp.add(products.get(i));
//                    }
//                }
//            }
            temp = BeanUtil.exeType(products, cny);
            topic.setProductIds(null);
            topic.setProduct(temp);
        }
        jo.put("topic", topic);
        return jo;
    }

//    for (int i = 0; i < oid.length; i++) {
//        Product byId = productRepository.findById(oid[i].toString());
//        if (byId.getImages().size() != 0) {
//            Double priceOff = byId.getPriceOff();//折扣
//            Double price = byId.getPrice();//美元原价
//            if (priceOff == null && !"".equals(priceOff)) {
//                byId.setPriceRMB(Double.parseDouble(String.format("%.2f", price * cny)));//人民币原价
//                byId.setDollar(price);
//                byId.setRmb(Double.parseDouble(String.format("%.2f", price * cny)));
//            } else {
//                byId.setPriceRMB(Double.parseDouble(String.format("%.2f", price * cny)));//人民币原价
//                byId.setDollar(Double.parseDouble(String.format("%.2f", price * priceOff)));
//                byId.setRmb(Double.parseDouble(String.format("%.2f", price * priceOff * cny)));
//            }
//            //封装infos
//            String[][] infos = byId.getInfos();
//            if (infos != null) {
//                String in = "";
//                for (int aa = 0; aa < infos.length; aa++) {
//                    in = in + infos[aa][0] + ":" + infos[aa][1] + "。";
//                }
//                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//                Matcher m = p.matcher(in);
//                in = m.replaceAll("");
//                byId.setProductInfo(in);
//                byId.setInfos(null);
//            }
//            //图片信息为空的数据过滤掉
//            List<String> images = byId.getImages();
//            if (images.size() > 0) {
//                if (images.size() == 1) {
//                    if (!"".equals(images.get(0).trim())) {
//                        temp.add(byId);
//                    }
//                } else {
//                    temp.add(byId);
//                }
//            }
//        }
//    }

    @Override
    public UpdateResult setTopic(String topicId, ObjectId[] productId) {
        UpdateResult save = topicRepository.update(topicId, productId);
        return save;
    }

    @Override
    public List<Topic> getByPage(Integer currentPage, Integer pageSize) {
//        System.out.println("start-time:" + new Date(System.currentTimeMillis()));
        Double cny = productRepository.getRate();
        System.out.println("getRate-time:" + System.currentTimeMillis());
        List<Topic> list = topicRepository.getByPage(currentPage, pageSize);
        System.out.println("service：getTopic-time:" + System.currentTimeMillis());
        List<Topic> result = new ArrayList<>();

        /**
         * 优化for循环，新的思路：
         * 上面的for循环只用来统计需要的productIds
         */
        ObjectId[] oid = new ObjectId[0];
        for (int i = 0; i < list.size(); i++) {
            ObjectId[] oid1 = list.get(i).getProductIds();
            if (oid.length == 0) {
                oid = oid1;
            } else {
                int len = oid.length + oid1.length;
                ObjectId[] oid2 = new ObjectId[len];
                for (int j = 0; j < oid1.length; j++) {
                    oid2[j] = oid1[j];
                }
                //再把原oid中的元素循环放进来
                for (int j = 0; j < oid.length; j++) {
                    int len2 = oid1.length + j;
                    oid2[len2] = oid[j];
                }
                //把oid2复制给oid，保持oid最新、最准确
                oid = new ObjectId[oid2.length];
                oid = oid2;
            }
        }
        System.out.println("封装oid：" + System.currentTimeMillis());
        List<Product> products = productRepository.findProductByBrand(oid);
        System.out.println("查询专题产品：" + System.currentTimeMillis());
        for (int i = 0; i < list.size(); i++) {
            List<Product> temp = new ArrayList<>();
            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getBrandName().equals(list.get(i).getTopicName())) {
                    temp.add(products.get(j));
                }
            }
            if (!CollectionUtils.isEmpty(temp)) {
                temp = BeanUtil.exeType(temp, cny);
            }
            list.get(i).setProduct(temp);
            list.get(i).setProductIds(null);
            if (temp.size() > 0) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public void resetTopic() {
        List<String> brands = productRepository.getBrands();
        if (!CollectionUtils.isEmpty(brands)) {
            for (int i = 0; i < brands.size(); i++) {
                List<Product> proAll = productRepository.findByBrandName(brands.get(i));
                Topic topic = new Topic();
                topic.setTopicName(brands.get(i));
                //设置图片
//            String[] temp = new String[3];
                List<String> tempStr = new ArrayList<>();
                for (int m = 0; m < proAll.size(); m++) {
                    if (tempStr.size() < 3 && proAll.get(m).getImages().size() > 0) {
                        for (int n = 0; n < proAll.get(m).getImages().size(); n++) {
                            String img = proAll.get(m).getImages().get(n);
                            if (img != null && !"".equals(img.replaceAll(" ", ""))) {
                                tempStr.add(proAll.get(m).getImages().get(n));
                            }
                        }
                    }
                }
                topic.setImages(tempStr);
                Topic topic1 = saveOrUpdateTopic(topic);
                ObjectId[] ids = new ObjectId[proAll.size()];
                for (int mm = 0; mm < proAll.size(); mm++) {
                    ids[mm] = new ObjectId(proAll.get(mm).getId());
                }
                setTopic(topic1.getId(), ids);
            }
        }
    }

    @Override
    public List<TopicImage> getImages() {
        List<TopicImage> images = topicRepository.getImages();
        return images;
    }

    @Override
    public Topic getTopicById(String topicId) {
        Topic topic = topicRepository.findById(topicId);
        return topic;
    }
}
