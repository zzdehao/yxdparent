package com.tf.mapper;

import com.tf.entity.TProvinceCity;
import com.tf.entity.TProvinceCityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProvinceCityMapper {
    long countByExample(TProvinceCityExample example);

    int deleteByExample(TProvinceCityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TProvinceCity record);

    int insertSelective(TProvinceCity record);

    List<TProvinceCity> selectByExample(TProvinceCityExample example);

    TProvinceCity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TProvinceCity record, @Param("example") TProvinceCityExample example);

    int updateByExample(@Param("record") TProvinceCity record, @Param("example") TProvinceCityExample example);

    int updateByPrimaryKeySelective(TProvinceCity record);

    int updateByPrimaryKey(TProvinceCity record);
}