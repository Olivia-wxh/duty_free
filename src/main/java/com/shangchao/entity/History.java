package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data // 使用@Data注解，实体类可以省略get set方法
@ApiModel // swagger注解
public class History {

    @ApiModelProperty(value = "主键id")
    @Id // 在属性上添加@Filed注解，值为对应的字段名
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    @Field("userId")
    private Integer userId;

    @ApiModelProperty(value = "搜索内容")
    @Field("search")
    private String search;

    @ApiModelProperty(value = "搜索内容排序")
    @Field("search")
    private Integer orderSearch;
}
