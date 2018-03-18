package com.tf.action;
import com.alibaba.fastjson.JSONObject;
import com.tf.utils.ResultR;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by wugq on 2018/3/18.
 */
@RestController
public class TestController{
    @GetMapping(value = "/test")
    public ResultR test(){
        ResultR r =  new ResultR();
        JSONObject data = new JSONObject();
        data.put("key","hello");
        r.setData(data);
        return r;
    }
}
