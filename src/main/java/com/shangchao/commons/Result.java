package com.shangchao.commons;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Result {
    public Result() {
    }
    public Result(boolean success) {
        this.success = success;
    }
    private boolean success;
    private String errCode;
    private String errMsg;
    private Object data;

    /**
     * buildSuccess
     */
    public static Result buildSuccess() {
        return buildSuccess("", null);
    }
    public static Result buildSuccess(Object data) {
        return buildSuccess(null, data);
    }
    public static Result buildSuccess(String message, Object data) {
        Result resultDO = new Result(true);
        resultDO.errMsg = message;
        resultDO.data = data;
        resultDO.errCode = "200";
        return resultDO;
    }
    /**
     * buildError
     */
    public static Result buildError() {
        return buildError("", "500");
    }
    public static Result buildError(String message) {
        return buildError(message, "500");
    }
    public static Result buildError(Object data) {
        return buildError(null,"500", data);
    }
    public static Result buildError(String message,String code) {
        return buildError(message, code,null);
    }

    public static Result buildError(String message, String code,Object data) {
        Result resultDO = new Result(false);
        resultDO.errMsg = message;
        resultDO.data = data;
        resultDO.errCode = code;
        return resultDO;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }






}
