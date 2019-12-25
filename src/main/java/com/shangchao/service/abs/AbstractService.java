package com.shangchao.service.abs;

import com.shangchao.utils.PaginationEntity;

import java.util.HashMap;
import java.util.Map;

/*
 */
public class AbstractService {
  public Map<String, Object> handleParams(PaginationEntity<? extends PaginationEntity> page) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("start", page.getStart());
    params.put("pageSize", page.getPageSize());
    return params;
  }
}
