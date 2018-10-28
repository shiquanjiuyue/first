<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
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
<script>var root = "<%=contextPath %>";	</script>
<link href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/static/plugins/H-ui.admin/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<title>后台登录 </title>
<meta name="keywords" content="啦啦啦">
<meta name="description" content="本网站由时泉久月开发">
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" id="userLoginForm" action="${pageContext.request.contextPath }/public/login" method="post">
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-8">
          <input id="loginName" name="loginName" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-8">
          <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <label for="online" id="errorMessage" style="color:red">${errorMessage }</label>
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input name="" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">综合管理系统 by 时泉久月</div>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugins/H-ui.admin/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/common/js/base.js"></script>
<script type="text/javascript">
	/* 用户登入 */
	$("#userLoginForm").submit(function (){
		if(!hasLength($("#loginName").val())){
			layer.alert("请输入用户名！", {icon:7});
			$("#errorMessage").empty();
			return false;
		}else if(!hasLength($("#password").val())){
			layer.alert("请输入密码！", {icon:7});
			$("#errorMessage").empty();
			return false;
		}
		return true;
	});
	
</script>
</body>
</html>