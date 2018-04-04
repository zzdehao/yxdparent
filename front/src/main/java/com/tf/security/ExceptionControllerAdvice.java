package com.tf.security;

import com.tf.utils.ReturnException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("errCode", 5000);
        map.put("message", ex.getMessage());
        return map;
    }

    @ResponseBody
    @ExceptionHandler(value = ReturnException.class)
    public Map myErrorHandler(ReturnException ex) {
        Map map = new HashMap();
        map.put("errCode", ex.getCode());
        map.put("message", ex.getMsg());
        return map;
    }

}
