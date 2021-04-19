package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data // 使用@Data注解，实体类可以省略get set方法
@ApiModel // swagger注解
public class Stock implements Serializable, Comparable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "单价")
    private Double unitPrice;

    @ApiModelProperty(value = "简介")
    private String infos;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "图片src")
    private List<StockImages> imagesList;


    @Override
    public int compareTo(Object o) {
        String s1 = this.brandName.toLowerCase();
        String s2 = ((Stock) o).getBrandName().toLowerCase();
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
