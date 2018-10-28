<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/static/common/jsp/resource.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>用户注册</title>
<meta name="keywords" content="用户注册 大会员">
<meta name="description" content="欢迎注册时泉久月的测试网站">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-admin-add">
	<c:if test="${!empty user }"><input type="hidden" value="${user.userSn }" name="userSn"></c:if>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>账号</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="<c:if test="${!empty user }">${user.loginName }</c:if>" placeholder="" id="loginName" name="loginName">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登入密码</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" value="<c:if test="${!empty user }">${user.password }</c:if>" placeholder="密码" id="password" name="password">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>确认密码</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off"  placeholder="与登入密码保持一致" id="password2" name="password2" value="<c:if test="${!empty user }">${user.password }</c:if>">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="sex" type="radio" id="sex-man" <c:if test="${empty user || user.sex == 1 }">checked</c:if> value="1">
				<label for="sex-1">男</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="sex-woman" name="sex" value="0" <c:if test="${!empty user && user.sex == 0 }">checked</c:if>>
				<label for="sex-2">女</label>
			</div>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value='<c:if test="${!empty user }">${user.phone }</c:if>' placeholder="" id="phone" name="phone">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>邮箱</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" placeholder="@" name="email" id="email" value="<c:if test="${!empty user }">${user.email }</c:if>">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">角色</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="role.roleSn" size="1">
				<c:forEach items="${roleList }" var="role">
					<option <c:if test="${!empty user && user.role.roleSn == role.roleSn }">selected</c:if> value="${role.roleSn }">${role.roleMark }</option>
				</c:forEach>
			</select>
			</span> </div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;取消&nbsp;&nbsp;" onclick="closeDiv()">
		</div>
	</div>
	</form>
</article>

<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#form-admin-add").validate({
		rules:{
			loginName:{
				required:true,
				minlength:4,
				maxlength:16
			},
			password:{
				required:true,
			},
			password2:{
				required:true,
				equalTo: "#password"
			},
			sex:{
				required:true,
			},
			phone:{
				required:true,
				isPhone:true,
			},
			email:{
				required:true,
				email:true,
			},
			role:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				type: 'post',
				url: root + "/x/user/add",
				dataType: "json",
				success: function(data){
					if(data.error == -1){
						layer.msg(data.message, {icon:2,time:1000}, function(){
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						});
					}else{
						layer.msg(data.message, {icon:1,time:1000}, function(){
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						});
					}
				},
                error: function(XmlHttpRequest, textStatus, errorThrown){
					layer.msg('请求异常!', {icon:2,time:1000}, function(){
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					});
				}
			});
			parent.reload()
		}
	});
});

/** 关闭添加页面 */
function closeDiv(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
</script> 
</body>
</html>