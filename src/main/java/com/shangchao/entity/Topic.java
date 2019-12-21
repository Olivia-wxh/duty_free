package com.shangchao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "special_topic")
public class Topic implements Serializable {

    @Id
    private Long id;

    private String topicName;

    //引用商品信息
    @DBRef
    private List<Product> students;
}
