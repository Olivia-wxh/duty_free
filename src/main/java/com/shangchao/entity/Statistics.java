package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel // swagger注解
public class Statistics implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "总销售额")
    private Double salePrice;

    @ApiModelProperty(value = "净利润")
    private Double profit;

    @ApiModelProperty(value = "年份")
    private Integer year;

    @ApiModelProperty(value = "月份")
    private Integer month;
}
