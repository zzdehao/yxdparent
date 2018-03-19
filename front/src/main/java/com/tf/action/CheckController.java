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
import com.tf.web.config.YxdConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Api(value = "巡店API")
@RestController
public class CheckController {
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

    /**
     * 上传图片
     * @param file
     * @return
     */
    @ApiOperation(notes = "上传图片", value = "上传图片", httpMethod = "POST")
    @RequestMapping(value = "/check/uploadImage", method = {RequestMethod.POST})
    @ResponseBody
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
