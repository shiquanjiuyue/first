<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/static/common/jsp/resource.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理 <span class="c-gray en">&gt;</span> 用户列表<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="Huiform text-c">
		<input type="text" class="input-text" style="width:250px" placeholder="登入账号" id="search_login_name">
		<input type="text" class="input-text" style="width:250px" placeholder="用户名称" id="search_user_name">
		<button type="button" class="btn btn-success" onclick="reload()"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"> <a href="javascript:;" onclick="batchDelete()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a class="btn btn-primary radius" href="javascript:;" onclick="admin_user_add('添加用户','<%=contextPath%>/x/user/toUserAddPage','800')"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a> </span> </div>
	<table id="user_table" class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="11">用户管理</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" value="" name=""></th>
				<th style="display:none">ID</th>
				<th width="200">用户名</th>
				<th width="200">登入账号</th>
				<th width="200">性别</th>
				<th width="200">手机</th>
				<th width="200">邮件</th>
				<th width="200">身份</th>
				<th width="200">上次登入时间</th>
				<th width="200">创建时间</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody class="text-c">
		</tbody>
	</table>
</div>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/js/user_list.js"></script> 
</body>
</html>