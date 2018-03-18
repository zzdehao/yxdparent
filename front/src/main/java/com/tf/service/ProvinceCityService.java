package com.tf.service;
import com.tf.entity.TProvinceCity;
import com.tf.entity.TProvinceCityExample;
import com.tf.mapper.TProvinceCityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by wugq on 2018/3/18.
 */
@Service
@Transactional
public class ProvinceCityService {

    private  static List<TProvinceCity> staticProvinces = new ArrayList<TProvinceCity>();
    @Autowired
    private TProvinceCityMapper provinceCityMapper;

    public List<TProvinceCity> getProvinces(){
        TProvinceCityExample cityExample = new TProvinceCityExample();
        TProvinceCityExample.Criteria cond = cityExample.createCriteria();
        cond.andLevelEqualTo(0);
        cityExample.setOrderByClause(" code asc");
        List<TProvinceCity> provinces = staticProvinces;
        if(staticProvinces.size()==0){
              provinces = this.provinceCityMapper.selectByExample(cityExample);
              staticProvinces=provinces;
              return provinces;
        }else{
              return provinces;
        }
    }

    public List<TProvinceCity> getAllCitysByPid(Integer pid){
        TProvinceCityExample cityExample = new TProvinceCityExample();
        TProvinceCityExample.Criteria cond = cityExample.createCriteria();
        cond.andLevelEqualTo(1);
        cond.andParentIdEqualTo(pid);
        cityExample.setOrderByClause(" parent_id asc");
        List<TProvinceCity> provinces = this.provinceCityMapper.selectByExample(cityExample);
        return provinces;
    }
    public TProvinceCity getProvinceCityById(Integer id){
        return this.provinceCityMapper.selectByPrimaryKey(id);
    }












}
