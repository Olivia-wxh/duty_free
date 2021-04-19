package com.shangchao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "ScAndroid对象")
public class ScAndroid implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "下载地址")
    private String downloadUrl;

    @ApiModelProperty(value = "版本号")
    private String versionCode;

    @ApiModelProperty(value = "更新内容")
    private String updateInfo;
}
