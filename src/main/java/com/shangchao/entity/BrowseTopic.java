package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data // 使用@Data注解，实体类可以省略get set方法
@Document(collection = "browse_topic") // 在实体添加@Document注解，collection= "对应的表名"
@ApiModel // swagger注解
public class BrowseTopic {

  @ApiModelProperty(value = "主键id")
  @Id // 在属性上添加@Filed注解，值为对应的字段名
  //    @Field("_id")
  private String id;

  @ApiModelProperty(value = "用户ID")
  @Field("userId")
  private String userId;

  @ApiModelProperty(value = "专题ID")
  @Field("topicId")
  private String topicId;
}
