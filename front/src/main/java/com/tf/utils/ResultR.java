package com.tf.utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.web.config.ErrCode;

import java.util.List;
public class ResultR {

    ErrCode errCode;
    String message;
    JSONObject data;

    public ResultR() {
        this.errCode = ErrCode.NO_LOGIN;
    }


    public ResultR(ErrCode errCode) {
        this.errCode = errCode;
    }

    public ResultR(ErrCode errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    public ResultR(ErrCode errCode, String message, JSONObject data) {
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

    public String getResult() {
        JSONObject obj = new JSONObject();
        if (message == null) {
            message = errCode.getnContent();
        }
        if (data == null) {
            data = new JSONObject();
        }
        obj.put("code", errCode.getnCode());
        obj.put("message", message);
        obj.put("status", "true");
        obj.put("data", data);
//        System.out.println(obj.toJSONString());
        return obj.toJSONString();
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        if (message == null) {
            message = errCode.getnContent();
        }
        if (data == null) {
            data = new JSONObject();
        }
        return "ResultR{" +
                "errCode=" + errCode.getnCode() +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
