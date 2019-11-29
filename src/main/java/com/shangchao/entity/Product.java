package com.shangchao.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data//使用@Data注解，实体类可以省略get set方法
@Document(collection = "product")//在实体添加@Document注解，collection= "对应的表名"
public class Product {
    @Field("id")//在属性上添加@Filed注解，值为对应的字段名
    private Long id;

    @Field("imgsrc")
    private String imgsrc;

    @Field("title")
    private String title;

    @Field("price")
    private String price;

    @Field("productId")
    private Long productId;

    @Field("productInfo")
    private String productInfo;

    @Field("priceOff")
    private String priceOff;

    @Field("discountD")
    private String discountD;

    @Field("discountR")
    private String discountR;

    @Field("title")
    private String disShopNo1;

    @Field("title")
    private String star;

    @Field("title")
    private String num;

}
