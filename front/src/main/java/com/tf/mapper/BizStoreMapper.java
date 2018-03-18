package com.tf.mapper;

import com.tf.entity.BizStore;
import com.tf.entity.BizStoreExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BizStoreMapper {
    long countByExample(BizStoreExample example);

    int deleteByExample(BizStoreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BizStore record);

    int insertSelective(BizStore record);

    List<BizStore> selectByExample(BizStoreExample example);

    BizStore selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BizStore record, @Param("example") BizStoreExample example);

    int updateByExample(@Param("record") BizStore record, @Param("example") BizStoreExample example);

    int updateByPrimaryKeySelective(BizStore record);

    int updateByPrimaryKey(BizStore record);
}