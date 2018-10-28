<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<!-- 公共css资源 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugins/H-ui.admin/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui.admin/css/style.css" />

<!-- 全局属性定义 -->
<script>var root = "<%=contextPath %>";	</script>

<!-- 公共js资源 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/layer/2.4/layer.js"></script><!-- layer的js -->
<script type="text/javascript" src="<%=contextPath%>/static/common/js/base.js"></script><!-- 自定义js的方法 -->
