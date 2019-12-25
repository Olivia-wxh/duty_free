package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data//使用@Data注解，实体类可以省略get set方法
@Document(collection = "product")//在实体添加@Document注解，collection= "对应的表名"
@ApiModel//swagger注解
public class Product implements Serializable {
    @ApiModelProperty(value = "产品id")
    @Id//在属性上添加@Filed注解，值为对应的字段名
//    @Field("_id")
    private String id;

    @ApiModelProperty(value = "图片src")
    @Field("imgsrc")
    private String imgsrc;

    @ApiModelProperty(value = "主题")
    @Field("title")
    private String title;

    @ApiModelProperty(value = "价格")
    @Field("price")
    private String price;

    @ApiModelProperty(value = "抓取来的产品ID")
    @Field("productId")
    private String productId;

    @ApiModelProperty(value = "产品信息")
    @Field("productInfo")
    private String productInfo;

    @ApiModelProperty(value = "折扣")
    @Field("priceOff")
    private String priceOff;

    @ApiModelProperty(value = "美元价格")
    @Field("discountD")
    private String discountD;

    @ApiModelProperty(value = "人民币价格")
    @Field("discountR")
    private String discountR;

    @ApiModelProperty(value = "disShopNo1")
    @Field("disShopNo1")
    private String disShopNo1;

    @ApiModelProperty(value = "star")
    @Field("star")
    private String star;

    @ApiModelProperty(value = "num")
    @Field("num")
    private String num;

    @ApiModelProperty(value = "所属专题")
    //引用专题信息
    @DBRef
    private Topic topic;

}
