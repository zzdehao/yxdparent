package com.tf.action;

import com.tf.utils.ResultR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date:2018/01/02
 * @time:14:08
 * @author:Zeff
 * @description:description
 */
@Api(value = "城市信息接口")
@RestController
@RequestMapping(value = "/provinceCity")
public class ProvinceCityController {

    @RequestMapping(value = "/getCityByLevel", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ApiOperation(notes = "通过level获取城市", value = "通过level获取城市", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "level", value = "等级", required = true, defaultValue = "0", dataType = "int", paramType = "query")
    })
    public ResultR getCityByLevel(@RequestParam(value = "level", defaultValue = "0", required = true) Integer level) throws Exception {

        ResultR resultR = new ResultR();

        return resultR;
    }


}
