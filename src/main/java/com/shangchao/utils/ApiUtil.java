package com.shangchao.utils;

import com.shangchao.commons.CodeAndMsgEnum;

import java.util.HashMap;
import java.util.Map;

public class ApiUtil {
  public static Map success(String msg) {
    Map result = new HashMap<>(4);
    result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
    result.put("msg", msg);
    return result;
  }

  public static Map success(String msg, Map data) {
    Map result = new HashMap<>(4);
    result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
    result.put("msg", msg);
    result.put("data", data);
    return result;
  }

  public static Map fail(String msg) {
    Map result = new HashMap<>(4);
    result.put("code", CodeAndMsgEnum.ERROR.getcode());
    result.put("msg", msg);
    return result;
  }
}
