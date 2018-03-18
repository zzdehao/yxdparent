package com.tf.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
/**
 * Created by wugq on 2018/3/15.
 */
@Component
public class InitServiceData {
    @Autowired
    private ProvinceCityService provinceCityService;
    @Autowired
    @PostConstruct
    public void initLoadData(){

    }

}
