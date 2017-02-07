<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>货物管理</title>
		<link href="<%=basePath%>platform/css/bootstrap.min.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>platform/css/font-awesome.min.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>platform/css/jQuery.lightninBox.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>platform/css/creativebuttons.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>platform/css/jqvmap.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>platform/css/jquery-ui.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>platform/css/flipclock.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>platform/css/jquery.countdown.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>platform/css/clndr.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>platform/css/pricing-table.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>platform/css/analog-clock.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>platform/css/style.css" rel="stylesheet"
			type="text/css" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript"
			src="<%=basePath%>common/jquery/jquery-1.8.0.js" />
</script>
		<script type="text/javascript"
			src="<%=basePath%>common/js/forum_common.js" />
</script>
		<script type="text/javascript"
			src="<%=basePath%>platform/js/goodsManagement.js" />
</script>
	</head>

	<body>
		<!--  
    <div class="header">
    	<div class="header_welcome">
    		<div class="header_welcome_user" id="header_welcome_user"></div>
    		<div class="header_welcome_logout"><a class="logout_a" href="<%=basePath%>framework/login/login.jsp">退出</a></div>
    	</div>
    </div>
    <div class="search">
    	<div class="search_out">
    		<div class="search_out_input"><input type="text"/></div>
    		<div class="search_out_btn"><input type="button" value="查询"/></div>
    	</div>
    </div>
    <div class="content">
    </div>
    -->
		<section class="ui-w3ls">
		<!-- Static navbar -->
		<nav class="navbar navbar-default">
		<div class="search-w3ls">
				<div class="input-group">
					<input type="text" class="form-control" name="search" id="search2"
						placeholder="Search"  />
					<span class="input-group-btn"  onclick="csm.goodsManagement.queryByName()">
						<button class="btn btn-default">
							<i class="fa fa-search"></i>
						</button> </span>
				</div>
		</div>
		<div class="navbar-header">
			<a class="navbar-brand" href="javacript:void(0);"><h1>
					<i class="fa fa-shield" aria-hidden="true"></i> 化妆品销售管理
				</h1>
			</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="<%=basePath%>framework/login/login.jsp" class="logout">注销
					</a>
				</li>
				<li class="dropdown">
					<a href="javacript:void(0);" class="dropdown-toggle"
						data-toggle="dropdown"><img
							src="<%=basePath%>platform/image/saler.jpg"
							class="img-circle img-responsive" alt="">
					</a>
				</li>
			</ul>
		</div>
		<!--/.nav-collapse -->
		</nav>
		</section>
		<div class="content">
			<div class="content_left">
				<div class="content_type" val="true" onclick="csm.goodsManagement.searchByType(this,'all')">全部</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'mm')">面膜</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'sfs')">爽肤水</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'ms')">面霜</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'xz')">卸妆</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'jy')">精油</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'cg')">唇膏</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'xs')">香水</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'fdy')">粉底液</div>
				<div class="content_type" val="false" onclick="csm.goodsManagement.searchByType(this,'bbs')">BB霜</div>
			</div>
			<div class="content_right" id="content_right"></div>
		</div>
	</body>
</html>