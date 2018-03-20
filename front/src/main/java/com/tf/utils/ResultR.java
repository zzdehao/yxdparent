package com.tf.utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.web.config.ErrCode;

import java.util.List;
public class ResultR {

    String errCode;
    String message;
    JSONObject data;

    public ResultR() {
        this.errCode = ErrCode.SUCCESS.getnCode();
    }


    public ResultR(ErrCode errCode) {
        this.errCode = errCode.getnCode();
        this.message = errCode.getnContent();
    }

    public ResultR(ErrCode errCode, String message) {
        this.errCode = errCode.getnCode();
        this.message = message;
    }

    public ResultR(ErrCode errCode, String message, JSONObject data) {
        this.errCode = errCode.getnCode();
        this.message = message;
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(ErrCode errCode) {
        this.errCode = errCode.getnCode();
        this.message = errCode.getnContent();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void setData(JSONArray data) {
        this.data = new JSONObject();
        this.data.put("data", data);
    }

    public void setData(List<?> list) {
        JSONArray jsonArray = new JSONArray();
        if (list == null || list.isEmpty()) {
            return;
        }

        for (Object object : list) {
            jsonArray.add(object);
        }
        this.data = new JSONObject();
        this.data.put("data", jsonArray);
    }



}
