package com.shangchao.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data // 使用@Data注解，实体类可以省略get set方法
@Document(collection = "sc_product") // 在实体添加@Document注解，collection= "对应的表名"
@ApiModel // swagger注解
@CompoundIndexes(
{
    @CompoundIndex(name = "_id",def = "{'id':1}")
})
public class Product implements Serializable, Comparable {

    @ApiModelProperty(value = "产品id")
    @Id // 在属性上添加@Filed注解，值为对应的字段名
    @Field("_id")
    private String id;

    @ApiModelProperty(value = "产品名称")
    @Field("productName")
    private String productName;

    @ApiModelProperty(value = "图片src")
    @Field("images")
    private List<String> images;

    @ApiModelProperty(value = "品牌ID")
    @Field("brandId")
    private String brandId;

    @ApiModelProperty(value = "品牌名称")
    @Field("brandName")
    private String brandName;

    @ApiModelProperty(value = "价格")
    @Field("price")
    private Double price;

    @ApiModelProperty(value = "人民币原始价格")
//    @Field("price")
    private Double priceRMB;

    @ApiModelProperty(value = "售卖价格")
    @Field("salePrice")
    private Double salePrice;

    @ApiModelProperty(value = "折扣")
    @Field("priceOff")
    private Double priceOff;

    @ApiModelProperty(value = "产品聚合信息")
    @Field("infos")
    private String[][] infos;

    @ApiModelProperty(value = "规格")
    @Field("specification")
    private String specification;

    @Field("productInfo")
    private String productInfo;

    @ApiModelProperty(value = "产品来源")
    @Field("source")
    private String source;

    @ApiModelProperty(value = "数据源标识")
    @Field("doid")
    private String doid;

    @ApiModelProperty(value = "star")
    @Field("star")
    private String star;

    @ApiModelProperty(value = "num")
    @Field("num")
    private String num;

    @ApiModelProperty(value = "catId1")
    @Field("catId1")
    private String catId1;

    @ApiModelProperty(value = "catId2")
    @Field("catId2")
    private String catId2;

    @ApiModelProperty(value = "catId3")
    @Field("catId3")
    private String catId3;

    @ApiModelProperty(value = "catStr")
    @Field("catStr")
    private String[] catStr;

//    @ApiModelProperty(value = "主题")
//    @Field("title")
//    private String title;

    //以下属性是返回前端时计算后赋值，不入库


    @ApiModelProperty(value = "折后美元价格")
    private Double dollar;

    @ApiModelProperty(value = "折后人民币价格")
    private Double rmb;

    @Override
    public int compareTo(Object o) {
        String s1 = this.brandName.toLowerCase();
        String s2 = ((Product) o).getBrandName().toLowerCase();
        for (int i = 0; i < s1.length()-1; i++) {
            if (s1.substring(i, i+1).compareTo(s2.substring(i, i+1)) > 0) {
                return 1;
            } else if (s1.substring(i, i+1).compareTo(s2.substring(i, i+1)) == 0) {
                continue;
            } else {
                return -1;
            }
        }
        return 0;
    }
}
