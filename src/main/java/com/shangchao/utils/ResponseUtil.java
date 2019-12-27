package com.shangchao.utils;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.commons.CodeAndMsgEnum;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static JSONObject success(String msg) {
        JSONObject result = new JSONObject();
        result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
        result.put("msg", msg);
        return result;
    }

    public static JSONObject success(Object data) {
        JSONObject result = new JSONObject();
        result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
        result.put("data", data);
        return result;
    }

    public static JSONObject success(String msg, Object data) {
        JSONObject result = new JSONObject();
        result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    public static JSONObject fail(String msg) {
        JSONObject result = new JSONObject();
        result.put("code", CodeAndMsgEnum.ERROR.getcode());
        result.put("msg", msg);
        return result;
    }
}
