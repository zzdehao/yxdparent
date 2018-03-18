package com.tf.action;

import com.alibaba.fastjson.JSONObject;
import com.tf.entity.TAdmin;
import com.tf.security.TokenAuthenticationService;
import com.tf.service.MD5;
import com.tf.service.UserService;
import com.tf.utils.ResultR;
import com.tf.web.config.ErrCode;
import com.tf.web.config.YxdConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户相关接口")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method ={RequestMethod.POST,RequestMethod.GET},
            produces = "application/json;charset=UTF-8")
    @ApiOperation(notes = "登录", value = "查询用户", httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phoneNum", value = "手机号",
                required = true, dataType = "String", paramType = "String"),
        @ApiImplicitParam(name = "pass", value = "密码",
                required = true, dataType = "String", paramType = "String")
    })
    public ResultR login(@RequestParam(value = "phoneNum", required = true) String phoneNum,
                         @RequestParam(value = "pass", required = true) String pass) {
        ResultR r = new ResultR();
        TAdmin tAdmin = this.userService.getAdminByTel(phoneNum);
        if(tAdmin!=null){
            r.setErrCode(ErrCode.NO_EXISTS);
            return r;
        }else{
            //判断密码
            String qpass = tAdmin.getPassword();
            String fpass = MD5.getMD5(pass);
            if(qpass.equals(fpass)){
                TokenAuthenticationService tokenAuthenticationService = new TokenAuthenticationService();
                String token = tokenAuthenticationService.addAuthentication(tAdmin.getTruename(),
                        "ROLE_WXUSER", phoneNum, tAdmin.getId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(YxdConfig.TOKEN_HEADER_STRING, token);
                r.setErrCode(ErrCode.SUCCESS);
                r.setData(jsonObject);
                return r;
            }else{
                r.setErrCode(ErrCode.NO_PASS);
                return r;
            }
        }
    }


}
