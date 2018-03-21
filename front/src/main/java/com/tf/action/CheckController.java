package com.tf.action;

import com.alibaba.fastjson.JSONObject;
import com.tf.entity.BizCheckDetail;
import com.tf.entity.BizCheckPlanExample;
import com.tf.param.BizCheckDetailResponse;
import com.tf.param.Pager;
import com.tf.service.CheckService;
import com.tf.utils.ResultR;
import com.tf.utils.UUIDGenerator;
import com.tf.web.config.ErrCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Api(value = "巡店API")
@RestController
public class CheckController extends BaseController{

    //上传路径
    @Value(value="${yxd.uploadPath}")
    private String uploadPath;

    @Value(value="${yxd.imageUrl}")
    private String imageUrl;

    @Value(value="${yxd.imageUrlPreffix}")
    private String imageUrlPreffix;

    @Autowired
    private CheckService checkService;

    @ApiOperation(notes = "获取计划列表", value = "获取计划列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token",
                    required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "/check/plan/list/{status}", method = {RequestMethod.GET})
    public ResultR getPlanList(@PathVariable Integer status,
                               @RequestHeader Integer limit, @RequestHeader Integer offset) {
        ResultR r = new ResultR();
        BizCheckPlanExample example = new BizCheckPlanExample();

        example.createCriteria().andCheckStatusEqualTo(status).andCheckUseridEqualTo(this.getCurrent().getId());
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token",
                    required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "/check/plan/{id}", method = {RequestMethod.GET})
    public ResultR getPlan(@PathVariable Long id) {
        ResultR r = new ResultR();
        BizCheckDetailResponse checkDetailResponse = this.checkService.findPlanById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", checkDetailResponse);
        r.setData(jsonObject);
        return r;
    }

    @ApiOperation(notes = "入参描述:<br/>{\n" +
            "<br/>\"checkTime\": \"2018-03-20T02:21:59.119Z\", #检查时间\n" +
            "<br/>\"planBatchId\": 0, #计划批次ID\n" +
            "<br/>\"planBatchName\": \"string\", #计划批次名称\n" +
            "<br/>\"planId\": 0, #计划ID\n" +
            "<br/>\"storeAddress\": \"string\", #店铺详细地址\n" +
            "<br/>\"storeCheckCityCode\": \"string\", #检查城市编码\n" +
            "<br/>\"storeCheckCityName\": \"string\", #检查城市名称\n" +
            "<br/>\"storeCheckProvinceCode\": \"string\", #检查省份编码\n" +
            "<br/>\"storeCheckProvinceName\": \"string\", #检查省份名称\n" +
            "<br/>\"storeCheckCountryCode\": \"string\", #店铺实际巡查地址乡编码\n" +
            "<br/>\"storeCheckCountryName\": \"string\", #店铺实际巡查地址乡名称\n" +
            "<br/>\"storeCheckaddress\": 0, #检查详细地址\n" +
            "<br/>\"storeId\": 0, #店铺ID\n" +
            "<br/>\"storeCode\": \"string\", #店铺编码\n" +
            "<br/>\"storeName\": \"string\", #店铺名称\n" +
            "<br/>\"checkLongitude\": \"string\",  #经度\n" +
            "<br/>\"checkLatitude\": \"string\", #维度\n" +
            "<br/>\"storeExistsok\": 0, #店铺是否存在\n" +
            "<br/>\"storeRealnameok\": 0, #店铺实际名称是否相符\n" +
            "<br/>\"storeRealname\": 0, #厅店名称\n" +
            "<br/>\"bizCount\": 0, #业务台席数量\n" +
            "<br/>\"storeRegiontype\": 0, #店铺地域类型\n" +
            "<br/>\"storeMendiantype\": 0, #店铺门店类型\n" +
            "<br/>\"storeYtsqtype\": 0, #店铺类型业态商圈\n" +
            "<br/>\"storeAreatype\": 0,#店铺面积类型\n" +
            "<br/>\"storeMemberstype\": 0, #店铺人员规模\n" +
            "<br/>\"storeNmonthChangeok\": 0, #近一个月是否变更\n" +
            "<br/>\"storeBusyUsercount\": 0, #忙时人数\n" +
            "<br/>\"storeFreeUsercount\": 0, #闲时人数\n" +
            "<br/>\"storeMemberBusscope\": 0, #营业员受理业务程度-熟练\n" +
            "<br/>\"storeMemberTaocanScope\": 0, #营业员对内部套餐策略数量程度\n" +
            "<br/>\"storeMemberTerminalPolicy\": 0, #终端营销策略-终端熟练\n" +
            "<br/>\"storeMemeberActivesellok\": 0, #对客户是否主动营销\n" +
            "<br/>\"existTerminalOk\": 0, #店内是否有终端销售\n" +
            "<br/>\"store4gok\": 0, #是否推出4g\n" +
            "<br/>\"storeAllnetok\": 0, #是否推出全网通\n" +
            "<br/>\"storeFirstRecdTerminal\": 0, #是否主推机型\n" +
            "<br/>\"storeHealthok\": 0, #是否干净卫生\n" +
            "<br/>\"storeConductok\": 0, #宣传是否合规\n" +
            "<br/>\"storeDonglineok\": 0, #店内动线是否设计合规\n" +
            "<br/>\"storeMonthSalecount\": 0, #月销售终端数量\n" +
            "<br/>\"storeDifExpandability\": 0, #异网发展能力\n" +
            "<br/>\"storeDoortouok\": 0, #是否联通门头\n" +
            "<br/>\"storeDengxiangok\": 0, #是否联通灯箱\n" +
            "<br/>\"storeBrandok\": 0, #是否联通标牌\n" +
            "<br/>\"storeQrcode\": 0, #是否联通二维码\n" +
            "<br/>\"storeRealnameNoticeok\": 0, #是否有实名制公告\n" +
            "<br/>\"storeBackwall\": 0, #是否联通背景墙\n" +
            "<br/>\"storeBartie\": 0, #是否联通柜台贴\n" +
            "<br/>\"storeZqOppok\": 0, #是否opp专区\n" +
            "<br/>\"storeZqJinliok\": 0, #是否有金立专柜\n" +
            "<br/>\"storeZqVivook\": 0, #是否vivo专柜\n" +
            "<br/>\"storeZqHuaweiok\": 0, #是否为华为专柜\n" +
            "<br/>\"storeZqSamsongok\": 0, #是否三星专柜\n" +
            "<br/>\"storeZqAppleok\": 0, #是否苹果专柜\n" +
            "<br/>\"storeZqMeizuok\": 0, #是否魅族\n" +
            "<br/>\"otherArea\": 0, #其他专区\n" +
            "<br/>\"storeZq2g3gok\": 0, #是否2g,3g专柜\n" +
            "<br/>\"storeKccheckOutcount\": 0, #社会机型库存数量\n" +
            "<br/>\"storeKccheckSelfcount\": 0, #自由机型库存数量\n" +
            "<br/>\"stroeFrontImage2\": \"string\", #店铺门头正面1\n" +
            "<br/>\"storeFrontImage1\": \"string\", #店铺门头正面2\n" +
            "<br/>\"stroeLinjieImage1\": \"string\", #店铺临街环境1\n" +
            "<br/>\"stroeLinjieImage2\": \"string\", #店铺临街环境2\n" +
            "<br/>\"stroeLinjieImage3\": \"string\", #店铺临街环境3\n" +
            "<br/>\"storeGuitaiImage1\": \"string\", #店铺柜台1\n" +
            "<br/>\"storeGuitaiImage2\": \"string\" #店铺柜台2\n" +
            "}", value = "提交巡检信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token",
                    required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "/check/plan/check", method = {RequestMethod.POST})
    public ResultR addCheck(@RequestBody BizCheckDetail checkDetail) {
        ResultR r = new ResultR();
        checkDetail.setCreateTime(new Date());
        checkDetail.setCheckUserId(this.getCurrent().getId().longValue());
        checkDetail.setCheckUserName(this.getCurrent().getName());
        checkDetail.setCheckUserPhone(this.getCurrent().getTel());
        this.checkService.addDetail(checkDetail);
        return r;
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @ApiOperation(notes = "上传图片", value = "上传图片", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token",
                    required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "/check/uploadImage", method = {RequestMethod.POST})
    public ResultR uploadImage(@RequestParam MultipartFile file){
        String fileName = UUIDGenerator.getUUID();
        String oldName = file.getOriginalFilename();
        if (file.getOriginalFilename().lastIndexOf(".") > 0) {
            fileName += oldName.substring(oldName.lastIndexOf("."));
            oldName = oldName.substring(0, oldName.lastIndexOf("."));
        }

        String imageWebUrl = imageUrl+imageUrlPreffix+fileName;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadPath, fileName));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        ResultR r = new ResultR(ErrCode.SUCCESS);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imageWebUrl",imageWebUrl);
        jsonObject.put("oldName",oldName);
        jsonObject.put("fileName",(imageUrlPreffix+fileName));
        r.setData(jsonObject);
        return r;
    }


}
