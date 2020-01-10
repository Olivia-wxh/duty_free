package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data // 使用@Data注解，实体类可以省略get set方法
@Document(collection = "sc_usd_rate") // 在实体添加@Document注解，collection= "对应的表名"
public class ExchangeRate {

    @Id // 在属性上添加@Filed注解，值为对应的字段名
    @Field("_id")
    private String id;

    @Field("cny")
    private String cny;

    @Field("jpy")
    private String jpy;

    @Field("gbp")
    private String gbp;

    @Field("hkd")
    private String hkd;

    @Field("cad")
    private String cad;

    @Field("eur")
    private String eur;

    @Field("krw")
    private String krw;

    @Field("aud")
    private String aud;

    @Field("sgd")
    private String sgd;

    @Field("thb")
    private String thb;

    @Field("twd")
    private String twd;

    @Field("brl")
    private String brl;

    @Field("rub")
    private String rub;

    @Field("try")
    private String TRY;

    @Field("pln")
    private String pln;

    @Field("zar")
    private String zar;

    @Field("idr")
    private String idr;

    @Field("myr")
    private String myr;

    @Field("clp")
    private String clp;

    @Field("addtime")
    private String addTime;
}
