<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
<%@page import="cn.hurry.util.DateTimeUtils"%>
<%@page import="cn.hurry.po.inventory.Inventory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute("user");
	if(user==null){
		request.getRequestDispatcher("/login.jsp").forward(request,response);
	}
	Inventory inventory = (Inventory)request.getAttribute("inventory");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>进销存管理系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<%=basePath%>plugins/miniui/boot.js"></script>
		<script type="text/javascript" src="<%=basePath%>plugins/miniui/jquery.jqprint-0.3.js"></script>
		<style type="text/css">
	    html,body
	    {
	        width:100%;
	        height:100%;
	        border:0;
	        margin:0;
	        padding:0;
	        overflow:visible;
	    } 
	    .header
	    {
	        background:url(<%=basePath%>resources/ht/images/header.gif) repeat-x 0 -1px;
	    }
	    </style>
	</head>

<body>
	<div style="width: 620px;margin: auto;" id="dataDiv">
		<table style="width: 100%;">
			<tr>
				<td colspan="3" style="text-align: center;font-size: 20px;font-weight: 800;">
					库存盘点单
				<hr/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px">
				仓库:<%=inventory.getStore().getName()%>
				</td>
				<td style="text-align: center;">
				盘点人:<%=inventory.getUser().getUsername()%>
				<td style="width: 200px">
					编号:<%=inventory.getCode()%><br />
					日期:<%=DateTimeUtils.format(inventory.getCreateDate(),"yyyy年MM月dd日")%>
				</td>
			</tr>
		</table>
		<table  style="border: 1px black solid;width: 100%;text-align: center;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width: 20%;border-right: 1px black solid; "> 
					品名
				</td>
				<td style="width: 10%;border-right: 1px black solid; "> 
					单位
				</td>
				<td style="width: 10%;border-right: 1px black solid; "> 
					规格
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
					总成本
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
					库存成本
				</td>
				<td style="width: 20%;border-right: 1px black solid; "> 
					库存数量
				</td>
				<td style="width: 20%;"> 
					盘点数量
				</td>
			</tr>
			<c:set var="countNumber" value="0"></c:set>
			<c:set var="countMoney" value="0"></c:set>
			<c:set var="countSumMoney" value="0"></c:set>
			<c:set var="countRealNumber" value="0"></c:set>
			<c:forEach var="inventoryGoods" items="${inventory.inventoryGoods}">
				<tr>
					<td style="border-right: 1px black solid;;border-top:1px black solid; "> 
						${inventoryGoods.goods.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${inventoryGoods.goods.unit.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${inventoryGoods.goods.norms.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${inventoryGoods.sumMoney}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${inventoryGoods.money}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${inventoryGoods.storeNumber}
					</td>
					<td style="border-top:1px black solid;"> 
						${inventoryGoods.realNumber}
					</td>
				</tr>
			<c:set var="countNumber" value="${inventoryGoods.storeNumber+countNumber}"></c:set>
			<c:set var="countMoney" value="${inventoryGoods.money+countMoney}"></c:set>
			<c:set var="countSumMoney" value="${inventoryGoods.sumMoney+countSumMoney}"></c:set>
			<c:set var="countRealNumber" value="${inventoryGoods.realNumber+countRealNumber}"></c:set>
			</c:forEach>
				<tr>
					<td style="border-right: 1px black solid;;border-top:1px black solid; "> 
						总计
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${countSumMoney}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${countMoney}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${countNumber}
					</td>
					<td style="border-top:1px black solid;"> 
						${countRealNumber}
					</td>
				</tr>
		</table>
		<div style="text-align: right;">
		<label style="color: red;font-size: 12px;"><%=inventory.getStatus()==Inventory.STATUS_NOT_CHECKED?"未结转":""%></label>
		</div>
		</div>
		<div style="text-align: center;">
		<a class="mini-button" align="right" onclick="$('#dataDiv').jqprint();">打印</a>
		</div>
</body>

</html>
<script type="text/javascript">
    mini.parse();
</script>

