package com.shangchao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "special_topic")
public class Topic implements Serializable {

    @Id
//    @Field("_id")
    private String id;

    @Field("topicName")
    private String topicName;

    @Field("isDelete")
    private Integer isDelete;//0表示未删除/上架，1表示删除/下架；

    //引用商品信息
    @DBRef
    private List<Product> products;
}
