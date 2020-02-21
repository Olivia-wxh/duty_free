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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TopicServiceImpl implements TopicService {


}
