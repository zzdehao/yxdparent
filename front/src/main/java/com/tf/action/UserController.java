package com.tf.action;

import com.tf.utils.ResultR;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户相关接口")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/login", method ={RequestMethod.POST,RequestMethod.GET},
            produces = "application/json;charset=UTF-8")
    @ApiOperation(notes = "登录", value = "查询用户", httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phoneNum", value = "手机号", required = true, dataType = "String", paramType = "String"),
        @ApiImplicitParam(name = "pass", value = "密码", required = true, dataType = "String", paramType = "String")
    })
    /**
     * 1)查询人员是否存在,密码是否正确
     *
     */
    public ResultR login(@RequestParam(value = "phoneNum", required = true) String phoneNum,
                         @RequestParam(value = "pass", required = true) String pass) {
        return null;
    }


}
