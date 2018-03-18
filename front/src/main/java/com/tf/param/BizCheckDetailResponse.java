package com.tf.param;

import com.tf.entity.BizCheckDetail;
import com.tf.entity.BizCheckPlan;
import com.tf.entity.BizStore;

public class BizCheckDetailResponse {

    private BizCheckPlan bizCheckPlan;

    private BizCheckDetail bizCheckDetail;

    private BizStore bizStore;

    public BizCheckPlan getBizCheckPlan() {
        return bizCheckPlan;
    }

    public void setBizCheckPlan(BizCheckPlan bizCheckPlan) {
        this.bizCheckPlan = bizCheckPlan;
    }

    public BizCheckDetail getBizCheckDetail() {
        return bizCheckDetail;
    }

    public void setBizCheckDetail(BizCheckDetail bizCheckDetail) {
        this.bizCheckDetail = bizCheckDetail;
    }

    public BizStore getBizStore() {
        return bizStore;
    }

    public void setBizStore(BizStore bizStore) {
        this.bizStore = bizStore;
    }

    @Override
    public String toString() {
        return "BizCheckDetailResponse{" +
                "bizCheckDetail=" + bizCheckDetail +
                ", bizStore=" + bizStore +
                "} " + super.toString();
    }
}