package com.tf.action;

import com.alibaba.fastjson.JSONObject;
import com.tf.entity.BizCheckDetail;
import com.tf.entity.BizCheckPlanExample;
import com.tf.param.BizCheckDetailResponse;
import com.tf.param.Pager;
import com.tf.service.CheckService;
import com.tf.utils.ResultR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "巡店API")
@RestController
public class CheckController {

    @Autowired
    private CheckService checkService;

    @ApiOperation(notes = "获取计划列表", value = "获取计划列表", httpMethod = "GET")
    @RequestMapping(value = "/check/plan/list/{status}", method = {RequestMethod.GET})
    public ResultR getPlanList(@PathVariable Integer status,
                               @RequestHeader Integer limit, @RequestHeader Integer offset) {
        ResultR r = new ResultR();
        BizCheckPlanExample example = new BizCheckPlanExample();
        example.createCriteria().andCheckStatusEqualTo(status).andCheckUseridEqualTo(1);
        example.setOffset(offset);
        example.setLimit(limit);
        example.setOrderByClause("check_start_date");
        Pager pager = this.checkService.findPlanList(example);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pager", pager);
        r.setData(jsonObject);
        return r;
    }

    @ApiOperation(notes = "获取计划详情", value = "获取计划详情", httpMethod = "GET")
    @RequestMapping(value = "/check/plan/{id}", method = {RequestMethod.GET})
    public ResultR getPlan(@PathVariable Long id) {
        ResultR r = new ResultR();
        BizCheckDetailResponse checkDetailResponse = this.checkService.findPlanById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", checkDetailResponse);
        r.setData(jsonObject);
        return r;
    }

    @ApiOperation(notes = "获取计划详情", value = "获取计划详情", httpMethod = "POST")
    @RequestMapping(value = "/check/plan/check", method = {RequestMethod.POST})
    public ResultR addCheck(@RequestBody BizCheckDetail checkDetail) {
        ResultR r = new ResultR();
        this.checkService.addDetail(checkDetail);
        return r;
    }

}
