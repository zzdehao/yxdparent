package com.tf.service;

import com.tf.entity.*;
import com.tf.mapper.BizCheckDetailMapper;
import com.tf.mapper.BizCheckPlanMapper;
import com.tf.mapper.TAdminMapper;
import com.tf.param.BizCheckDetailResponse;
import com.tf.param.Pager;
import com.tf.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by wugq on 2018/3/18.
 */

@Service
@Transactional
public class CheckService {


    @Autowired
    private BizCheckPlanMapper bizCheckPlanMapper;

    @Autowired
    private BizCheckDetailMapper bizCheckDetailMapper;

    @Autowired
    private StoreService storeService;


    @Transactional(readOnly = true)
    public BizCheckDetailResponse findPlanById(Long id){
        BizCheckPlan checkPlan = this.bizCheckPlanMapper.selectByPrimaryKey(id);
        BizStore store = this.storeService.getStoreById(checkPlan.getStoreId().intValue());
        BizCheckDetailResponse checkDetailColResponse = new BizCheckDetailResponse();
        checkDetailColResponse.setBizCheckPlan(checkPlan);
        checkDetailColResponse.setBizStore(store);
        if(checkPlan.getCheckStatus().intValue() == 2){
            BizCheckDetailExample detailExample = new BizCheckDetailExample();
            detailExample.createCriteria().andPlanIdEqualTo(checkPlan.getId());
            detailExample.setLimit(1);
            detailExample.setOrderByClause("check_time desc");
            List<BizCheckDetail> list = this.bizCheckDetailMapper.selectByExample(detailExample);
            if(!CollectionUtils.isEmpty(list)){
                checkDetailColResponse.setBizCheckDetail(list.get(0));
            }
        }
        return checkDetailColResponse;
    }

    @Transactional(readOnly = true)
    public Pager<BizCheckDetailResponse> findPlanList(BizCheckPlanExample planExample){
        Pager pager = new Pager();
        Long count = this.bizCheckPlanMapper.countByExample(planExample);
        if (count.longValue() == 0) {
            return pager;
        }
        pager.setTotal(count.intValue());
        List<BizCheckPlan> planList = this.bizCheckPlanMapper.selectByExample(planExample);
        if (CollectionUtils.isEmpty(planList)) {
            return pager;
        }
        List<Long> ids = new ArrayList();
        for(int i = 0; i < planList.size(); i++){
            ids.add(planList.get(i).getStoreId().longValue());
        }
        BizStoreExample example = new BizStoreExample();
        example.createCriteria().andIdIn(ids);
        List<BizStore> storeList = this.storeService.getStoreExample(example);
        Map<Long, BizStore> storeMap = new HashMap();
        for(int i = 0; i < storeList.size(); i++){
            BizStore store = storeList.get(i);
            storeMap.put(store.getId(), store);
        }
        List<BizCheckDetailResponse> checkDetailResponseList = new ArrayList();
        for(int i = 0; i < planList.size(); i++){
            BizCheckDetailResponse checkDetailResponse = new BizCheckDetailResponse();
            checkDetailResponseList.add(checkDetailResponse);
            BizCheckPlan checkPlan = planList.get(i);
            checkDetailResponse.setBizCheckPlan(checkPlan);
            if(checkPlan.getLastCheckTime() != null){
                if(StringUtil.isNow(checkPlan.getLastCheckTime())){
                    checkDetailResponse.setIsCanEdit(1);
                }else{
                    checkDetailResponse.setIsCanEdit(2);
                }
            }
            checkDetailResponse.setBizStore(storeMap.get(checkPlan.getStoreId().longValue()));
        }
        pager.setRows(checkDetailResponseList);
        return pager;
    }



    public void addDetail(BizCheckDetail checkDetail){
        checkDetail.setCreateTime(new Date());
        this.bizCheckDetailMapper.insertSelective(checkDetail);
        BizCheckPlan checkPlan = new BizCheckPlan();
        checkPlan.setId(checkDetail.getPlanId());
        checkPlan.setCheckStatus(2);
        checkPlan.setLastCheckTime(checkDetail.getCheckTime());
        this.bizCheckPlanMapper.updateByPrimaryKeySelective(checkPlan);
        BizStore bizStore = new BizStore();
        bizStore.setId(checkDetail.getStoreId());
        bizStore.setLastCheckTime(checkDetail.getCheckTime());
        this.storeService.update(bizStore);
    }

    public void updateDetail(BizCheckDetail checkDetail){
        this.bizCheckDetailMapper.updateByPrimaryKeySelective(checkDetail);

    }

}
