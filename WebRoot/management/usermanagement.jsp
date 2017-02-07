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

		<title>后台管理</title>
		<link href="<%=basePath%>management/css/bootstrap.min.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>management/css/font-awesome.min.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>management/css/jQuery.lightninBox.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>management/css/creativebuttons.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>management/css/jqvmap.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>management/css/jquery-ui.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>management/css/flipclock.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>management/css/jquery.countdown.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>management/css/clndr.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>management/css/pricing-table.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>management/css/analog-clock.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>management/css/style.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>common/plugins/artDilog/css/ui-dialog.css" rel="stylesheet"
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
			src="<%=basePath%>common/jquery/jquery-1.9.1.js" />
</script>
		<script type="text/javascript"
			src="<%=basePath%>common/js/forum_common.js" />
</script>
		<script type="text/javascript"
			src="<%=basePath%>common/js/Chart.js" />
</script>
		<script type="text/javascript"
			src="<%=basePath%>common/plugins/artDilog/dialog-min.js" />
</script>
		<script type="text/javascript"
			src="<%=basePath%>management/js/userManagement.js" />
</script>
	</head>

	<body>
		<section class="ui-w3ls">
		<!-- Static navbar -->
		<nav class="navbar navbar-default">
		<div class="search-w3ls">
		</div>
		<div class="navbar-header">
			<a class="navbar-brand" href="javacript:void(0);"><h1>
					<i class="fa fa-shield" aria-hidden="true"></i> 销售后台管理系统
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
							src="<%=basePath%>management/image/boss.jpg"
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
				<div class="content_type" val="true" onclick="csm.userManagement.setTab('hpgl')">货品管理</div>
				<div class="content_type" val="false" onclick="csm.userManagement.setTab('xsry')">人员管理</div>
				<div class="content_type" val="false" onclick="csm.userManagement.setTab('yjfx')">业绩分析</div>
				<div class="content_type" val="false" onclick="csm.userManagement.setTab('qhtx')">缺货提醒</div>
			</div>
			<div>
				<div class="content_right_top" id="content_right_top"></div>
				<div class="content_right" id="content_right"></div>
			</div>
		</div>
	</body>
</html>