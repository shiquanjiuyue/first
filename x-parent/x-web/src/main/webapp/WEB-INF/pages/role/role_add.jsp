<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/static/common/jsp/resource.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />

<title>角色添加</title>
<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-admin-role-add">
		<c:if test="${!empty role }"><input type="hidden" class="input-text" value='${role.roleSn }' placeholder="" id="roleSn" name="roleSn"></c:if>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value='<c:if test="${!empty role }">${role.roleName }</c:if>' placeholder="" id="roleName" name="roleName">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="<c:if test="${!empty role }">${role.roleMark }</c:if>" placeholder="" id="" name="roleMark">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-2">授权：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<c:forEach items="${resultList }" var="permissionMap">
					<dl class="permission-list">
					<dt>
						<label><input type="checkbox" id="user-Character-0">${permissionMap.totalName }</label>
					</dt>
					<dd><dl class="cl permission-list2">
						<c:forEach items="${permissionMap.permissionList}" var="permission">
							<label class="mr-15"><input type="checkbox" <c:if test="${!empty role && fn:contains(role.permissionList, permission)}">checked="checked"</c:if> value="${permission.permissionSn }" name="permissionSnArr" id="user-Character-0-0-0">${permission.name }</label>
						</c:forEach>
					</dl></dd>
					</dl>
				</c:forEach>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-7 col-sm-8 col-xs-offset-3 col-sm-offset-2">
				<button type="submit" class="btn btn-success radius"><i class="icon-ok"></i> 确定</button>
			</div>
		</div>
	</form>
</article>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){
	$(".permission-list dt input:checkbox").click(function(){
		$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
	});
	$(".permission-list2 dd input:checkbox").click(function(){
		var l =$(this).parent().parent().find("input:checked").length;
		var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
		if($(this).prop("checked")){
			$(this).closest("dl").find("dt input:checkbox").prop("checked",true);
			$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
		}
		else{
			if(l==0){
				$(this).closest("dl").find("dt input:checkbox").prop("checked",false);
			}
			if(l2==0){
				$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
			}
		}
	});
	
	$("#form-admin-role-add").validate({
		rules:{
			roleName:{
				required:true,
			},
			roleMark:{
				required:true,
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
			type: 'post',
			async: false,
			url: root + "/x/role/updateRolePermission",
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
			parent.reload(); // 父页面的表格刷新
		}
	});
});

</script>
</body>
</html>