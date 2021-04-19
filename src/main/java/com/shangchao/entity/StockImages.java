package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data // 使用@Data注解，实体类可以省略get set方法
@ApiModel // swagger注解
public class StockImages {

        @ApiModelProperty(value = "主键-产品id")
        private Integer id;

        @ApiModelProperty(value = "主键-产品id")
        private String productId;

        @ApiModelProperty(value = "主键-产品id")
        private String imageUrl;
}
