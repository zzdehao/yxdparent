package com.tf.biz.dataimp;

import com.tf.biz.check.entity.BizCheckPlan;
import com.tf.biz.check.entity.BizCheckPlanExample;
import com.tf.biz.check.mapper.BizCheckPlanMapper;
import com.tf.biz.check.param.BizCheckDetailResponse;
import com.tf.biz.common.StaticDataMap;
import com.tf.biz.dataimp.entity.BizImportUser;
import com.tf.biz.dataimp.entity.BizImportUserExample;
import com.tf.biz.dataimp.mapper.BizImportUserMapper;
import com.tf.biz.imp.ImportService;
import com.tf.biz.imp.constant.ImportEnum;
import com.tf.biz.imp.entity.BizImportBatch;
import com.tf.biz.imp.entity.BizImportBatchExample;
import com.tf.biz.imp.mapper.BizImportBatchMapper;
import com.tf.biz.imp.pojo.FilePath;
import com.tf.biz.store.entity.BizStore;
import com.tf.biz.store.entity.BizStoreExample;
import com.tf.biz.store.mapper.BizStoreMapper;
import com.tf.common.utils.MD5;
import com.tf.common.utils.ObjectExcelRead;
import com.tf.tadmin.entity.*;
import com.tf.tadmin.mapper.AdminMapper;
import com.tf.tadmin.mapper.RoleMapper;
import com.tf.tadmin.service.BaseService;
import com.tf.tadmin.shiro.ShiroUtils;
import com.tf.tadmin.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wugq on 2018/3/6.
 */
@Service
@Transactional
public class DataImpService extends BaseService {

    @Value("${init.password}")
    private String initPassword;

    @Value("${init.roleCode}")
    private String initRoleCode;
    @Autowired
    private BizImportUserMapper importUserMapper;
    @Autowired
    private ImportService importService;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BizImportBatchMapper batchMapper;
    @Autowired
    private BizStoreMapper bizStoreMapper;
    @Autowired
    private BizCheckPlanMapper checkPlanMapper;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Long getStoreByChannelCodeCount(String channelCode) {
        BizStoreExample express = new BizStoreExample();
        BizStoreExample.Criteria queryExpress = express.createCriteria();
        queryExpress.andChannelCodeEqualTo(channelCode);
        Long count = bizStoreMapper.countByExample(express);
        return count;
    }

    public Pager<BizImportUser> queryUserList(Integer start, Map<String, Object> param) {
        //limit ${start},${rows}
        int rows = Constants.PAGE_SIZE;
        Pager<BizImportUser> pager = new Pager<BizImportUser>();
        //组件查询条件
        BizImportUserExample express = new BizImportUserExample();
        express.setLimit(rows);
        express.setOffset(start);
        // limit 4 offset 9 4表示返回4行，9表示从表的第十行开始
        express.setOrderByClause(" create_time desc ");
        //增加查询条件
        BizImportUserExample.Criteria queryExpress = express.createCriteria();
        List<BizImportUser> list = importUserMapper.selectByExample(express);
        Long count = importUserMapper.countByExample(express);
        pager.setRows(list);
        pager.setTotal(count.intValue());
        return pager;
    }

    private String getTrim(Map data, String var) {
        Object obj = data.get(var);
        if (obj == null) {
            return "";
        } else {
            return (String) obj;
        }
    }

    /**
     * 上传文件以及解析
     * 放入临时表
     * 并放入用户表
     * 注意:
     * 初始密码为:11111111 初始角色为:general
     * 初始导入 （省   市   姓名  手机号）
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveImpUserData(MultipartFile multipartFile,
                                   FilePath filePath, Map<String, Object> param)
            throws IOException, InvalidFormatException {

        Map<String, String> provinceAndCityMap = StaticDataMap.provinceAndCityNameToCodeMapping;
        String batchName = importService.createBatchId(ImportEnum.ImportType.USER.getTypeName());
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("batchName", batchName);
        Long batchId = this.importService.save(multipartFile, filePath, ImportEnum.ImportType.USER.getCode(),
                paramMap);
        //解析文件
        InputStream inputStream = multipartFile.getInputStream();
        /**
         * @param startrow  //开始行号
         * @param startcol  //开始列号
         * @param endcol   //结束列号
         * @param sheetnum //sheet
         */
        List<Map> readDatas = (List) ObjectExcelRead.readExcelInputStream(inputStream, 2, 0, 4, 0);
        System.out.println("readDatas:" + readDatas.size());
        try {
            List<BizImportUser> importData = new ArrayList<BizImportUser>();
            List<Admin> userData = new ArrayList<Admin>();
            if (readDatas != null && readDatas.size() > 0) {
                Role role = roleMapper.getByRoleCode(initRoleCode);
                BizImportUser userPo = null;
                Admin admin = null;
                for (Map data : readDatas) {
                    //省
                    String pName = getTrim(data, "var0");
                    //市

                    String cityName = getTrim(data, "var1");
                    //姓名
                    String uname = getTrim(data, "var2");
                    //手机号
                    String tel = getTrim(data, "var3");
                    userPo = new BizImportUser();
                    userPo.setBatchId(batchId);
                    admin = new Admin();
                    admin.setBlz2(batchName);
                    admin.setBlz1(batchId.toString());
                    if (StringUtils.isEmpty(uname) && StringUtils.isEmpty(tel)) {
                        continue;
                    }
                    userPo.setUserName(uname);//姓名
                    userPo.setUserId("");//UserID
                    userPo.setProvinceName(pName);//省（I级）_2
                    userPo.setProvinceCode(Integer.parseInt(provinceAndCityMap.get(pName)));
                    userPo.setCityName(cityName);//地市（二级）_3
                    userPo.setCityCode(Integer.parseInt(provinceAndCityMap.get(cityName)));
                    userPo.setThreelevelName("");
                    userPo.setFourLevelname("");//部门/团队/网格（四级）_1
                    userPo.setFiveLevelphone(tel);//联系电话（五级）
                    userPo.setSecondPhone(tel);//第二联系电话
                    userPo.setDutyName("");//职务
                    userPo.setGridName("");//网格名称
                    userPo.setGridCode("");//网格代码
                    userPo.setDataUpdatetime(new Date());//数据更新时间
                    userPo.setQita1("");//其它信息1
                    userPo.setQita1("");
                    userPo.setQita2("");//其它信息2

                    admin.setUserType("00");
                    admin.setTrueName(uname);
                    admin.setNickname(uname);
                    admin.setProvinceName(userPo.getProvinceName());
                    admin.setProvinceCode(provinceAndCityMap.get(pName));
                    admin.setCityName(userPo.getCityName());
                    admin.setCityCode(provinceAndCityMap.get(userPo.getCityName()));
                    admin.setBusTypename("");
                    admin.setDeptName("");
                    admin.setTel(tel);
                    //导入人员账号name为tel
                    admin.setName(tel);
                    admin.setTel2(userPo.getSecondPhone());
                    admin.setDutyName("");
                    admin.setGridName("");
                    admin.setGridCode("");
                    admin.setDataupDate(userPo.getDataUpdatetime());
                    admin.setQita2("");

                    admin.setRoleCode(initRoleCode);
                    admin.setPassword(MD5.getMD5(initPassword));

                    //重复人员更新临表其他字段qita
                    getQitaFlag(userPo);
                    userData.add(admin);
                    importData.add(userPo);
                }
                //保存数据
                final Date now = new Date();
                final SessionUser sessionUser = ShiroUtils.getSessionUser();
                final int userId = sessionUser.getId();
                final String name = sessionUser.getName();
                importData.forEach(s -> {
                    s.setBatchId(batchId);
                    s.setCreateTime(now);
                    s.setCreateUserId(userId);
                    s.setCreateUserName(name);
                    this.importUserMapper.insertSelective(s);
                });
                //生成用户与角色信息
                userData.forEach(u -> {
                    //生成用户信息(校验手机号是否存在)
                    Admin qadmin = this.adminMapper.queryByLogin(u.getTel());
                    if (qadmin == null) {
                        this.adminMapper.insert(u);
                        UserRole urole = new UserRole();
                        urole.setRoleId(role.getId());
                        urole.setUserId(u.getId());
                        //生成角色人员信息
                        this.roleMapper.userRoleInsert(urole);
                    } else {
                        Admin uadmin = new Admin();
                        uadmin.setId(qadmin.getId());
                        uadmin.setBlz3("此人员存在,重复导入");
                        this.adminMapper.update(uadmin);
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     * 重复人员更新临表其他字段qita
     *
     * @param users
     * @return
     */
    private void getQitaFlag(BizImportUser users) {
        Admin qadmin = this.adminMapper.queryByLogin(users.getFiveLevelphone());
        if (qadmin != null) {
            users.setQita2("此人员已经存在");
        }
    }

    /**
     * 上传文件列表
     *
     * @param start
     * @param param
     * @return
     */
    public Pager<BizImportBatch> queryUpLoadFileList(Integer start, Map<String, Object> param) {
        //limit ${start},${rows}
        int rows = Constants.PAGE_SIZE;
        Pager<BizImportBatch> pager = new Pager<BizImportBatch>();
        //组件查询条件
        BizImportBatchExample express = new BizImportBatchExample();
        express.setLimit(rows);
        express.setOffset(start);
        // limit 4 offset 9 4表示返回4行，9表示从表的第十行开始
        express.setOrderByClause(" create_time desc ");
        //增加查询条件
        BizImportBatchExample.Criteria queryExpress = express.createCriteria();
        queryExpress.andIsDeletedIsNull();
        if (param != null) {
            Object obj = param.get("importTypes");
            if (obj != null) {
                List<Integer> importTypes = (List<Integer>) obj;
                queryExpress.andImportTypeIn(importTypes);
            }
            Object importType = param.get("importType");
            if (importType != null) {
                queryExpress.andImportTypeEqualTo((Integer) importType);
            }
        }
        List<BizImportBatch> list = batchMapper.selectByExample(express);
        Long count = batchMapper.countByExample(express);
        pager.setRows(list);
        pager.setTotal(count.intValue());
        return pager;
    }

    /**
     * 导入计划
     *
     * @param multipartFile
     * @param filePath
     * @param param
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public boolean saveImportCheckPlanData(MultipartFile multipartFile,
                                           FilePath filePath,
                                           Map<String, Object> param,
                                           ImportEnum.ImportType importType)
            throws IOException, InvalidFormatException {
        /**
         * SELF_CHANNEL_PLAN(31, "自有渠道","巡检计划"),
         WORLD_CHANNEL_PLAN(32, "社会渠道","巡检计划"),
         SMALL_CHANNEL_PLAN(33, "小微渠道","巡检计划");
         */
        String minDate = (String) param.get("minDate");
        String maxDate = (String) param.get("maxDate");
        String dateSuffix = " 00:00:00";
        minDate = minDate + dateSuffix;
        maxDate = maxDate + dateSuffix;

        Map<String, String> paramMap = new HashMap<String, String>();
        String batchName = importService.createBatchId(importType.getTypeName());
        paramMap.put("batchName", batchName);
        Long batchId = this.importService.save(multipartFile,
                filePath, importType.getCode(),
                paramMap);
        //解析文件
        InputStream inputStream = multipartFile.getInputStream();
        List<Map> readDatas = (List) ObjectExcelRead.readExcelInputStream(inputStream, 1, 0, 11, 0);
        List<BizCheckPlan> planData = new ArrayList<BizCheckPlan>();
        if (readDatas != null && readDatas.size() > 0) {
            BizCheckPlan plan = null;
            for (Map data : readDatas) {
                plan = new BizCheckPlan();
                plan.setBatchId(batchId);
                plan.setBatchName(batchName);
                try {
                    Date end = formatter.parse(maxDate);
                    Date st = formatter.parse(minDate);
                    plan.setCheckStartDate(st);
                    plan.setCheckEndDate(end);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //检查数据项  渠道编码
                String channelCode = (String) data.get("var2");
                //渠道名称
                String channelName = (String) data.get("var3");
                if (StringUtils.isEmpty(channelCode) || StringUtils.isEmpty(channelName)) {
                    continue;
                }
                /**
                 省
                 市
                 渠道编码
                 渠道名称
                 渠道类型
                 店铺编码
                 店铺名称
                 店铺详细地址
                 备注
                 巡店人
                 巡店人电话
                 */
                plan.setProvinceCode(null);
                plan.setProvinceName((String) data.get("var0"));
                plan.setCityCode(null);
                plan.setCityName((String) data.get("var1"));
                plan.setChannelType(importType.getCode());
                plan.setChannelCode((String) data.get("var2"));
                plan.setChannelName((String) data.get("var3"));
                plan.setStoreCode((String) data.get("var4"));
                plan.setStoreName((String) data.get("var5"));
                plan.setStoreAddress((String) data.get("var6"));
                plan.setStoreTypeName(importType.getTypeName());
                plan.setCheckUserName((String) data.get("var9"));
                plan.setChannelUserTel((String) data.get("var10"));
                System.out.println("checkEndDate:" + plan.getCheckEndDate());
                planData.add(plan);
            }
            //保存数据
            final Date now = new Date();
            final SessionUser sessionUser = ShiroUtils.getSessionUser();
            final int userId = sessionUser.getId();
            final String name = sessionUser.getName();
            planData.forEach(s -> {
                s.setBatchId(batchId);
                s.setCreateTime(now);
                s.setCreateUserId(userId);
                s.setCreateUserName(name);
                this.checkPlanMapper.insertSelective(s);
            });
        }
        return true;
    }

    /**
     * 查询店铺信息
     * @param start
     * @param param
     * @return
     */
    public Pager<BizStore> queryStoreList(Integer start, Map<String, Object> param) {
        //limit ${start},${rows}
        int rows = Constants.PAGE_SIZE;
        Pager<BizStore> pager = new Pager<BizStore>();
        String key = (String) param.get("key");
        Integer channelType = (Integer) param.get("channelType");
        //组件查询条件
        BizStoreExample express = new BizStoreExample();
        express.setLimit(rows);
        express.setOffset(start);
        express.setOrderByClause(" create_time desc ");
        //增加查询条件where channel_type=1 and ()效果
        BizStoreExample.Criteria cc = express.createCriteria();
        if (!StringUtils.isEmpty(key)) {
            cc.andChannelNameLike("%" + key + "%");
            BizStoreExample.Criteria c2 = express.createCriteria().andChannelCodeLike("%" + key + "%");
            BizStoreExample.Criteria c3 = express.createCriteria().andStoreNameLike("%" + key + "%");
            if (channelType != -1) {
                cc.andChannelTypeEqualTo(channelType);
                c2.andChannelTypeEqualTo(channelType);
                c3.andChannelTypeEqualTo(channelType);
            }
            express.or(c2);
            express.or(c3);
        } else {
            if (channelType != -1) {
                cc.andChannelTypeEqualTo(channelType);
            }
        }
        List<BizStore> list = bizStoreMapper.selectByExample(express);
        Long count = bizStoreMapper.countByExample(express);
        pager.setRows(list);
        pager.setTotal(count.intValue());
        return pager;
    }

    public Pager<BizCheckPlan> queryPlanList(Integer start, Map<String, Object> param) {
        //limit ${start},${rows}
        int rows = Constants.PAGE_SIZE;
        Pager<BizCheckPlan> pager = new Pager<BizCheckPlan>();
        Integer batchId = (Integer) param.get("batchId");
        //组件查询条件
        BizCheckPlanExample express = new BizCheckPlanExample();
        if(start!=null) {
            express.setLimit(rows);
            express.setOffset(start);
        }
        express.setOrderByClause(" create_time desc ");
        if (batchId != -99) {
            express.createCriteria().andBatchIdEqualTo(Long.parseLong(batchId.toString()));
        }
        if(start!=null) {
            express.createCriteria().andCheckStatusEqualTo(ImportEnum.PlanState.NoFabu.getCode());
        }
        //增加查询条件where channel_type=1 and ()效果
        List<BizCheckPlan> list = checkPlanMapper.selectByExample(express);
        Long count = checkPlanMapper.countByExample(express);
        pager.setRows(list);
        pager.setTotal(count.intValue());
        return pager;
    }

    public int delStore(Integer id) {
        return this.bizStoreMapper.deleteByPrimaryKey(Long.parseLong(id.toString()));
    }

    public int delXuser(Integer id) {
        try {
            this.importUserMapper.deleteByPrimaryKey(Long.parseLong(id.toString()));
            this.adminMapper.delete(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    /**
     * 发布计划
     *
     * @param batchId
     * @return
     */
    public int updatePlanFabu(Long batchId) {
        BizImportBatch batch = new BizImportBatch();
        batch.setId(batchId);
        batch.setState(ImportEnum.PlanState.Fabu.getCode());
        this.batchMapper.updateByPrimaryKeySelective(batch);
        BizCheckPlanExample checkPlanExample = new BizCheckPlanExample();
        BizCheckPlanExample.Criteria criteria = checkPlanExample.createCriteria();
        criteria.andBatchIdEqualTo(batchId);
        BizCheckPlan plan = new BizCheckPlan();
        plan.setCheckStatus(ImportEnum.PlanState.Fabu.getCode());
        return checkPlanMapper.updateByExampleSelective(plan, checkPlanExample);

    }

    public int update(BizImportBatch bizImportBatch) {
        return this.batchMapper.updateByPrimaryKeySelective(bizImportBatch);
    }

    @Transactional(readOnly = true)
    public XSSFWorkbook createPlanExcel(List<BizCheckPlan> checkPlanList) {
        return buildExcel(checkPlanList);
    }

    private XSSFWorkbook buildExcel(List<BizCheckPlan> checkPlanList) {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet("出未完成的任务");
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;
        int n = 0;
        cell = headRow.createCell(n++);
        cell.setCellValue("批次号");
        cell = headRow.createCell(n++);
        cell.setCellValue("批次名称");
        cell = headRow.createCell(n++);
        cell.setCellValue("渠道名称");
        cell = headRow.createCell(n++);
        cell.setCellValue("渠道类型");
        cell = headRow.createCell(n++);
        cell.setCellValue("店铺编号");
        cell = headRow.createCell(n++);
        cell.setCellValue("店铺名称");
        cell = headRow.createCell(n++);
        cell.setCellValue("省");
        cell = headRow.createCell(n++);
        cell.setCellValue("市");
        cell = headRow.createCell(n++);
        cell.setCellValue("店铺名称");
        cell = headRow.createCell(n++);
        cell.setCellValue("巡检次数");
        cell = headRow.createCell(n++);
        cell.setCellValue("巡检人");
        cell = headRow.createCell(n++);
        cell.setCellValue("巡检人电话");
        cell = headRow.createCell(n++);
        cell.setCellValue("巡检开始日期");
        cell = headRow.createCell(n++);
        cell.setCellValue("巡检结束日期");
        for(int i = 0, size = checkPlanList.size(); i < size; i++) {
            BizCheckPlan plan = checkPlanList.get(i);
            Long batchId = plan.getBatchId();//批次号
            String batchName = plan.getBatchName();//批次名称
            String channelCode = plan.getChannelCode();//渠道编号
            String channelName = plan.getChannelName();//渠道名称
            Integer channelType = plan.getChannelType();//渠道类型
            String provinceName = plan.getProvinceName();//省
            String cityName = plan.getCityName();//市
            String storeNum = plan.getStoreCode();//店铺编号
            String storeName = plan.getStoreName();//店铺名称
            String checkUserName = plan.getCheckUserName();//巡检人
            String channelUserTel = plan.getChannelUserTel();//巡检人电话
            Date checkStartDate = plan.getCheckStartDate();//巡检开始日期
            Date checkEndDate = plan.getCheckEndDate();//巡检结束日期
            int k = 0;
            cell = headRow.createCell(k++);
            cell.setCellValue(batchId);
            cell = headRow.createCell(k++);
            cell.setCellValue(batchName);
            cell = headRow.createCell(k++);
            cell.setCellValue(channelCode);
            cell = headRow.createCell(k++);
            cell.setCellValue(channelName);
            cell = headRow.createCell(k++);
            cell.setCellValue(channelType);
            cell = headRow.createCell(k++);
            cell.setCellValue(provinceName);
            cell = headRow.createCell(k++);
            cell.setCellValue(cityName);
            cell = headRow.createCell(k++);
            cell.setCellValue(storeNum);
            cell = headRow.createCell(k++);
            cell.setCellValue(storeName);
            cell = headRow.createCell(k++);
            cell.setCellValue(checkUserName);
            cell = headRow.createCell(k++);
            cell.setCellValue(channelUserTel);
            cell = headRow.createCell(k++);
            cell.setCellValue(checkStartDate);
            cell = headRow.createCell(k++);
            cell.setCellValue(checkEndDate);
        }
        return workBook;
    }
}
