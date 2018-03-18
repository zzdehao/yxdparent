package com.tf.utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.web.config.ErrCode;

import java.util.List;

public class Result {

    ErrCode errCode;
    String message;
    Object data;

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {
        this.errCode = ErrCode.NO_LOGIN;
    }


    public Result(ErrCode errCode) {
        this.errCode = errCode;
    }

    public Result(ErrCode errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    public Result(ErrCode errCode, String message, Object data) {
        this.errCode = errCode;
        this.message = message;
        this.data = data;
    }

    public ErrCode getErrCode() {
        return errCode;
    }

    public void setErrCode(ErrCode errCode) {
        this.errCode = errCode;
        this.message = errCode.getnContent();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }


}
