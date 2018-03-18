package com.tf.service;
import com.tf.entity.TProvinceCity;
import com.tf.entity.TProvinceCityExample;
import com.tf.mapper.TProvinceCityMapper;
import com.tf.utils.StaticDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wugq on 2018/3/15.
 */
@Component
public class InitServiceData {
    @Autowired
    private TProvinceCityMapper provinceCityMapper;
    @Autowired
    @PostConstruct
    public void initLoadData(){
        TProvinceCityExample cityExample = new TProvinceCityExample();
        TProvinceCityExample.Criteria cond = cityExample.createCriteria();
        cond.andLevelEqualTo(0);
        cityExample.setOrderByClause(" code asc");
        List<TProvinceCity> provinces =this.provinceCityMapper.selectByExample(cityExample);
        StaticDataMap.staticProvinces = provinces;




    }

}
