﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;   
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=contextPath%>/static/plugins/H-ui.admin/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui.admin/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<title>错误页面</title>
</head>
<body>
<section class="container-fluid page-404 minWP text-c">
	<p class="error-title"><i class="Hui-iconfont va-m" style="font-size:80px">&#xe688;</i>
		<span class="va-m"> 出错啦！</span>
	</p>
	<p class="error-description">不好意思，系统异常，请稍后再试~</p>
	<p class="error-info">您可以：
		<a href="javascript:;" onclick="history.go(-1)" class="c-primary">&lt; 返回上一页</a>
		<span class="ml-20">|</span>
		<a href="<%=contextPath%>/public/loginPage" class="c-primary ml-20">去登入页 &gt;</a>
	</p>
</section>
</body>
</html>