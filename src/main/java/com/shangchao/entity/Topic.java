package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "sc_topic") // 对应mongodb表
@ApiModel // swagger注解
public class Topic implements Serializable {

  @ApiModelProperty(value = "专题ID")
  @Id
  @Field("_id")
  private String id;

  @ApiModelProperty(value = "专题名称")
  @Field("topicName")
  private String topicName;

  @ApiModelProperty(value = "品牌")
  @Field("isDelete")
  private Integer isDelete; // 0表示未删除/上架，1表示删除/下架；

  @ApiModelProperty(value = "轮播图图片路径")
  @Field("images")
  private List<String> images;//"/images/imageName.jpg"

  // 引用商品信息
  @DBRef
  private ObjectId[] productIds;

  private List<Product> product;
}
