package com.tf.param;

import com.tf.entity.TProvinceCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public class District extends TProvinceCity {

    List<District> list = new ArrayList();

    public void add(District district){
        this.list.add(district);
    }

    public List<District> getList() {
        return list;
    }

    public void setList(List<District> list) {
        this.list = list;
    }
}
