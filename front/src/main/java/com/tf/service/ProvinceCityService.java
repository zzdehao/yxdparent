package com.tf.service;
import com.tf.entity.TProvinceCity;
import com.tf.entity.TProvinceCityExample;
import com.tf.mapper.TProvinceCityMapper;
import com.tf.param.District;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by wugq on 2018/3/18.
 */
@Service
@Transactional
public class ProvinceCityService {

    private  static List<TProvinceCity> staticProvinces = new ArrayList<TProvinceCity>();
    private  static Map<Integer,List<TProvinceCity>> staticCities = new HashMap<Integer,List<TProvinceCity>>();
    @Autowired
    private TProvinceCityMapper provinceCityMapper;

    public List<TProvinceCity> getProvinces(){
        TProvinceCityExample cityExample = new TProvinceCityExample();
        TProvinceCityExample.Criteria cond = cityExample.createCriteria();
        cond.andLevelEqualTo(0);
        cityExample.setOrderByClause(" code asc");
        List<TProvinceCity> provinces = staticProvinces;
        if(provinces==null || provinces.size()==0){
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
        //this.provinceCityMapper.selectByExample(cityExample);
        List<TProvinceCity> citys = staticCities.get(pid);
        if(citys==null||citys.size()==0){
            citys = this.provinceCityMapper.selectByExample(cityExample);
            if(citys!=null &&citys.size()>0) {
                staticCities.put(pid, citys);
            }
            return citys;
        }else {
            return citys;
        }
    }
    public TProvinceCity getProvinceCityById(Integer id){
        return this.provinceCityMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    public List<District> getAllDistrictTree(){
        TProvinceCityExample example = new TProvinceCityExample();
        example.setOrderByClause("code");
        List<TProvinceCity> list = this.provinceCityMapper.selectByExample(example);
        List<District> treeList = new ArrayList();
        LinkedList<District> link = new LinkedList();
        for(int i = 0, size = list.size(); i < size; i++){
            TProvinceCity provinceCity = list.get(i);
            District district = new District();
            BeanUtils.copyProperties(provinceCity, district);
            District last;
            try {
                last = link.getFirst();
            }catch(Exception ex){
                link.push(district);
                treeList.add(district);
                continue;
            }
            if(last.getId().intValue() == district.getParentId().intValue()){
                link.push(district);
                last.add(district);
            }else{
                link.pop();
                i--;
            }

        }
        return treeList;
    }

}
