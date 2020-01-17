package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shangchao.entity.ExchangeRate;
import com.shangchao.entity.Product;
import com.shangchao.entity.Topic;
import com.shangchao.repository.ProductRepository;
import com.shangchao.repository.TopicRepository;
import com.shangchao.service.TopicService;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        //        String topicId = json.getString("topicId").toString();
        Topic save = topicRepository.save(topic);
        return save;
    }

    @Override
    public List<Topic> getAllTopic() {
        List<Topic> all = topicRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
          all.get(i).setProductIds(null);
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
                        temp.add(byId);
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
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("addtime")));
        List<ExchangeRate> rate = mongoTemplate.find(query, ExchangeRate.class, "sc_usd_rate");
        String cnyStr = rate.get(0).getCny();
        Double cny = Double.parseDouble(cnyStr);
        JSONObject jo = new JSONObject();
        Topic topic = topicRepository.findById(topicId.toString());
        ObjectId[] oid = topic.getProductIds();
        List<Product> temp = new ArrayList<>();
        for (int i = 0; i < oid.length; i++) {
            Product byId = productRepository.findById(oid[i].toString());
            if (byId.getImages().size() != 0) {
                Double priceOff = byId.getPriceOff();//折扣
                Double price = byId.getPrice();//美元原价
                if (priceOff == null && !"".equals(priceOff)) {
                    byId.setPriceRMB(price * cny);//人民币原价
                    byId.setDollar(price);
                    byId.setRmb(price * cny);
                } else {
                    byId.setPriceRMB(price * cny);//人民币原价
                    byId.setDollar(price * priceOff);
                    byId.setRmb(price * priceOff * cny);
                }
                temp.add(byId);
            }
        }
        topic.setProductIds(null);
        topic.setProduct(temp);
        jo.put("topic", topic);
        return jo;
    }

    @Override
    public UpdateResult setTopic(String topicId, String productId) {
        UpdateResult save = topicRepository.update(topicId, new ObjectId(productId));
        return save;
    }

    @Override
    public List<Topic> getByPage(Integer currentPage, Integer pageSize) {
        //查询汇率
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("addtime")));
        ExchangeRate rate = mongoTemplate.findOne(query, ExchangeRate.class, "sc_usd_rate");
        String cnyStr = rate.getCny();
        Double cny = 6.9;//Double.parseDouble(cnyStr);
        List<Topic> list = topicRepository.getByPage(currentPage, pageSize);
        List<Topic> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Product> temp = new ArrayList<>();
            ObjectId[] oid = list.get(i).getProductIds();
            if (oid != null) {
                List<Product> products = productRepository.findProductByBrand(oid);
                for (int j = 0; j < products.size(); j++) {
                    Product byId = products.get(j);
                    //计算卖价
                    Double priceOff = byId.getPriceOff();
                    Double price = byId.getPrice();
                    if (priceOff != null && !"".equals(priceOff)) {
                        byId.setPriceRMB(price * cny);//人民币原价
                        byId.setDollar(price * priceOff);
                        byId.setRmb(price * priceOff * cny);
                    } else {
                        byId.setPriceRMB(price * cny);//人民币原价
                        byId.setDollar(price);
                        byId.setRmb(price * cny);
                    }
                    //封装infos
                    String[][] infos = byId.getInfos();
                    if (infos != null) {
//                        JSONObject jo = new JSONObject();
                        String in = "";
                        for (int aa = 0; aa < infos.length; aa++) {
//                            jo.put(infos[aa][0], infos[aa][1]);
                            in = in + infos[aa][0] + ":" + infos[aa][1] + "。";
                        }
                        byId.setProductInfo(in);
                        byId.setInfos(null);
                    }
                    temp.add(byId);
                }
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
            for (int mm = 0; mm < proAll.size(); mm++) {
                setTopic(topic1.getId(), proAll.get(mm).getId());
            }
        }
    }
}
