<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.manage.goods.GoodsManage"%>
<%@page import="cn.hurry.manage.unit.UnitManage"%>
<%@page import="cn.hurry.manage.unit.NormsManage"%>
<%@page import="cn.hurry.manage.store.StoreManage"%>
<%@page import="cn.hurry.manage.OperateManage"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	GoodsManage.reload();
	UnitManage.reload();
	NormsManage.reload();
	StoreManage.reload();
	OperateManage.reloadOperaMenuData();
	GoodsManage.reloadGoodsNumber();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>

	<body>
	刷新成功..
	</body>
</html>
