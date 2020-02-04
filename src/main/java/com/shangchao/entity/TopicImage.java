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
@Document(collection = "sc_topic_image") // 对应mongodb表
@ApiModel // swagger注解
public class TopicImage implements Serializable {

  @ApiModelProperty(value = "ID")
  @Id
  @Field("_id")
  private String id;

  @ApiModelProperty(value = "轮播图url")
  @Field("imageUrl")
  private String imageUrl;//"/images/imageName.jpg"

  private String topicId;
}
