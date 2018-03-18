package com.tf.action;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.tf.entity.TAdmin;
import com.tf.entity.TProvinceCity;
import com.tf.service.ProvinceCityService;
import com.tf.utils.Result;
import com.tf.utils.ResultR;
import com.tf.utils.StaticDataMap;
import com.tf.web.config.ErrCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@Api(value = "城市信息接口")
@RestController
public class ProvinceCityController extends BaseController {
    @Autowired
    private ProvinceCityService provinceCityService;

    @RequestMapping(value = "/getAllProvinces", method = {RequestMethod.POST,RequestMethod.GET},
            produces = "application/json;charset=UTF-8")
    @ApiOperation(notes = "获得所有省份信息", value = "获得所有省份信息", httpMethod = "POST")
    public ResultR getAllProvinces() throws Exception {
        TAdmin current = getCurrent();
        List<TProvinceCity> provinceCities = StaticDataMap.staticProvinces;
        ResultR resultR = new ResultR(ErrCode.SUCCESS);
        resultR.setData(provinceCities);
        return resultR;
    }
    @RequestMapping(value = "/getAllProvinceCitys", method = {RequestMethod.POST,RequestMethod.GET},
            produces = "application/json;charset=UTF-8")
    @ApiOperation(notes = "获得所有城市信息", value = "获得所有城市信息", httpMethod = "POST")
    public Result getAllProvinceCitys() throws Exception {
        List<TProvinceCity> provinceCities = StaticDataMap.staticProvinces;
        List<TProvinceCity> mapData =new ArrayList<TProvinceCity>();
        for(TProvinceCity province:provinceCities){
            List<TProvinceCity> citys = this.provinceCityService.getAllCitysByPid(province.getId());
            province.setChildList(citys);
            mapData.add(province);
        }
        Result resultR = new Result(ErrCode.SUCCESS);
        resultR.setData(mapData);
        return resultR;
    }

}
