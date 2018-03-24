package com.tf.service;

import com.tf.entity.BizStore;
import com.tf.entity.BizStoreExample;
import com.tf.mapper.BizStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StoreService {

    @Autowired
    private BizStoreMapper bizStoreMapper;

    @Transactional(readOnly = true)
    public BizStore getStoreById(Integer id){
        return this.bizStoreMapper.selectByPrimaryKey(Long.parseLong(id.toString()));
    }

    @Transactional(readOnly = true)
    public List<BizStore> getStoreExample(BizStoreExample example){
        return this.bizStoreMapper.selectByExample(example);
    }

}
