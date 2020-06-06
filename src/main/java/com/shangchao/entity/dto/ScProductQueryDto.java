package com.shangchao.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScProductQueryDto implements Serializable {

    private Integer pageNumber;

    private Integer pageSize;

}
