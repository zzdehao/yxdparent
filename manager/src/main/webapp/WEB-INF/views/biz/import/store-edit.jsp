<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
 <%
	String ctx = request.getContextPath();
	request.setAttribute("ctx", ctx) ;
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title>店铺编辑</title>
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.store.css" rel="stylesheet" type="text/css" />
<link href="lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="pd-5">
	<form action="import/validChannelCode" method="post"
		  class="form form-horizontal" id="form-store-add">
		<input type="hidden" name="id" id = "id"  value="${store.id}">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>渠道编码：</label>
			<div class="formControls col-5">
				<c:if test="${empty store.id}">
				<input type="text" class="input-text" ajaxurl="import/validChannelCode"
					   value="${store.channelCode}" placeholder="渠道编码"
					   id="channelCode" name="channelCode" datatype="*2-16"
					   nullmsg="渠道编码不能为空"
					   sucmsg="渠道编码验证通过！"  errormsg="渠道编码已经存在！">
				</c:if>
				<c:if test="${not empty store.id}">
				<input type="text" class="input-text"  readonly
					   value="${store.channelCode}" placeholder="渠道编码"
					   id="channelCode" name="channelCode" datatype="*2-16"
					   nullmsg="渠道编码不能为空">
				</c:if>
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>渠道名称：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.channelName}" placeholder=""
					   id="channelName" name="channelName" datatype="*2-16" nullmsg="渠道名称不能为空">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>省：</label>
			<div class="formControls col-3" id = "provinceDiv">
				<select id="provinceCode"  name="provinceCode" class="input-text" style="width: 150px;">
					<option value="" id="oppro">请选择省份:</option>
				</select>
			</div>
			<div class="formControls col-3" id = "cityDiv">
				<span class="c-red">*</span>市：
				<select id="cityCode" name="cityCode" class="input-text" style="width: 145px;">
					<option value="">请选择城市:</option>
				</select>
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>店铺编码：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.storeCode}" placeholder=""
					   id="storeCode" name="storeCode">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>店铺名称：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.storeName}" placeholder=""
					   id="storeName" name="storeName">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>平台商编码：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.platformCode}" placeholder=""
					   id="platformCode" name="platformCode">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>平台商名称：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.platformName}" placeholder=""
					   id="platformName" name="platformName">
			</div>
			<div class="col-4"></div>
		</div>

		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>区县分公司编码：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.companyCode}" placeholder=""
					   id="companyCode" name="companyCode" datatype="*2-16" nullmsg="区县分公司编码不能为空">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>区县分公司名称：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.companyName}" placeholder=""
					   id="companyName" name="companyName" datatype="*2-16" nullmsg="区县分公司名称不能为空">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>渠道类型：</label>
			<div class="formControls col-5">
				<span class="select-box">
				  <select class="select"  size="1" name="channelType" id="channelType">
					<option value="11">自有渠道</option>
					<option value="12">社会渠道</option>
					<option value="13">小微渠道</option>
				  </select>
			    </span>
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">详细地址：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.addressDetail}" placeholder=""
					   id="addressDetail" name="addressDetail" datatype="*2-16" nullmsg="详细地址不能为空">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">是否是有效渠道：</label>
			<div class="formControls skin-minimal col-xs-8 col-sm-9">
				<div class="formControls col-5">
				<span class="select-box">
				  <select class="select" size="1" name="isValidChannel" id="isValidChannel">
					<option value="1" selected>是</option>
					<option value="0">否</option>
				  </select>
			    </span>
				</div>
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">渠道经理：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.channelManagerName}" placeholder=""
					   id="channelManagerName" name="channelManagerName" datatype="*2-16" nullmsg="渠道经理不能为空">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">渠道经理电话：</label>
			<div class="formControls col-5">
				<input type="text" class="input-text"
					   value="${store.channelManagerPhone}" placeholder=""
					   id="channelManagerPhone" name="channelManagerPhone" datatype="*2-16" nullmsg="渠道经理电话不能为空">
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">备注：</label>
			<div class="formControls col-5">
				<textarea name="remark" cols="12" rows="4" class="textarea"  placeholder="说点什么...100个字符以内" dragonfly="true" onKeyUp="textarealength(this,50)">${store.remark}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/50</p>
			</div>
			<div class="col-4"></div>
		</div>
		<div class="row cl">
			<div class="col-9 col-offset-4">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				&nbsp;&nbsp;
				<input class="btn btn-primary radius" onclick="javascript:backgo();" type="button" value="&nbsp;&nbsp;返回&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</div>
<%@include file="/footer.jsp" %>
<script type="text/javascript">
	function backgo(){
		window.location.href="import/toStoreList";
	}
	function loadP(provinceCode) {
		if(provinceCode=='all'){
			return;
		}
		$.ajax({
			type : "get",
			url : "yewu/province/getProvince",
			cache : false,
			async : true,
			data:{code:''},
			dataType : "json",
			success : function(datas) {
				if (datas.length > 0) {
					for ( var i = 0; i < datas.length; i++) {
						var code = datas[i].code;
						var name=datas[i].name;
						if(provinceCode) {
							if(code==provinceCode) {
								$("#provinceCode").append("<option value=" + code + " selected>" + name + "</option>");
							}else{
								$("#provinceCode").append("<option value=" + code + ">" + name + "</option>");
							}
						}else{
							$("#provinceCode").append("<option value=" + code + ">" + name + "</option>");
						}
					}
				}
			},
			error : function() {
				alert("operation failed!");
			}
		});
	}
	function loadC(cityCode,provinceCode) {
		if(!provinceCode){
			return;
		}
		if(provinceCode=='all'){
			return;
		}
		$.ajax({
			type : "get",
			url : "yewu/province/getCity",
			cache : false,
			async : true,
			data:{provinceCode:provinceCode},
			dataType : "json",
			success : function(datas) {
				if (datas.length > 0) {
					$("#cityCode").empty();
					$("#cityCode").append("<option value=0>请选择市</option>");
					for ( var i = 0; i < datas.length; i++) {
						var code = datas[i].code;
						var name=datas[i].name;
						if(provinceCode!='') {
							if(code==cityCode) {
								$("#cityCode").append("<option value=" + code + " selected>" + name + "</option>");
							}else{
								$("#cityCode").append("<option value=" + code + ">" + name + "</option>");
							}
						}else{
							$("#cityCode").append("<option value=" + code + ">" + name + "</option>");
						}
					}
				}
			},
			error : function() {
				alert("operation failed!");
			}
		});
	}
	$(function(){

		loadP('${store.provinceCode}');
		loadC('${store.cityCode}','${store.provinceCode}');
		$("#provinceCode").change(function(){
			var pcode = $("#provinceCode").val();
			//$("#cityCode").empty();
			loadC('',pcode);
		});

		var id = $("#id").val();
		var channelType = '${store.channelType}';
		var isValidChannel = '${store.isValidChannel}';
		$("#channelType").val(channelType);
		$("#isValidChannel").val(isValidChannel);

		$("#form-store-add").Validform({
			tiptype:2,
			callback:function(form){
				var index = parent.layer.load();
				$.ajax({
					url:"<%=request.getContextPath()%>/import/saveStore" ,
					type:'post',
					async:true ,
					cache:false ,
					data:$(form).serialize(),
					dataType:"json",
					success:function(data){
						parent.layer.close(index);
						if(data.s == true){
							index = parent.layer.getFrameIndex(window.name);
							parent.layer.msg("保存成功,正在刷新数据请稍后……",{icon:1,time: 2000,shade: [0.1,'#fff']},function(){
								/**
								parent.$(".show_iframe:visible > iframe").contents().find("#search").click() ;
								parent.layer.close(index);
								 **/
								window.location.href='import/toStoreList'
							}) ;

						}else{
							parent.layer.alert(data.m , {icon: 2,title:"系统提示"});
						}
					},
				}) ;
				return false ;
			}
		});
	});
</script>
</body>
</html>