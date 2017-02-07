<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户登录</title>
    <link href="<%=basePath %>framework/login/css/login.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <script type="text/javascript" src="<%=basePath %>common/jquery/jquery-1.8.0.js"/></script>
  <script type="text/javascript" src="<%=basePath %>common/js/forum_common.js"/></script>
  <script type="text/javascript" src="<%=basePath %>framework/login/js/login.js"/></script>
  <style type="text/css">
  
  </style>
  </head>
  
  <body>
  <div class="content">
  <p>化妆品销售管理系统</p>
  	<form action="<%=basePath %>login/loginAC!loginAction.do" method="post" id="user_login_form" class="loginform">
     	<!-- 用户名<input type="text" name="userName" id="LoginuserName" />
     	密码<input type="text" name="password" id="LoginPassword" />
     	<input type="button" id="loginBTN" value="登录" onclick="forum.login.login();" />
     	-->
     	<div class="cell-icon-a">
              <input type="text" autocomplete="off" name="userName" id="LoginuserName" style="background-color:#fffafa;" placeholder="请输入账号">
            </div>
            <div class="cell-icon-b">
              <input type="password" autocomplete="off" name="password" id="LoginPassword" style="background-color:#fffafa;"placeholder="请输入密码" onkeydown="csm.login.enterKeydown()">
            </div>
            <div class="btn-warpa">
              <input type="button" value="登录" id="login_submit" onclick="csm.login.login();">
            </div>
            <div class="btn-warpb">
              <input type="reset" value="重置" id="login_submit">
            </div>
    </form>
  </div>
  </body>
</html>
