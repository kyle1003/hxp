<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
<%@page import="cn.hurry.util.DateTimeUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute("user");
	if(user==null){
		request.getRequestDispatcher("login.jsp").forward(request,response);
	}
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
	<div style="width: 400px;margin: auto;text-align: center;" id="dataDiv">
		<table style="width: 100%;">
			<tr>
				<td colspan="3" style="text-align: center;font-size: 20px;font-weight: 800;">
				损耗情况
				<hr/>
				</td>
			</tr>
		</table>
		<table style="width: 100%"  style="border: 1px black solid;width: 100%;" cellpadding="0" cellspacing="0">
				<tr>
					<td style="width: 20%;">单号</td>
					<td style="width: 20%;">商品</td>
					<td style="width: 10%;">损耗量</td>
					<td style="width: 10%;">操作人</td>
					<td style="width: 20%;">日期</td>
					<td style="width: 30%;">备注</td>
				</tr>
			<c:forEach var="ls" items="${loss}">
				<tr>
					<td style="cursor: pointer" title="点击查看明细" onclick="orderInfo('${ls.orderGoods.order.id}')">${ls.orderGoods.order.id}</td>
					<td>${ls.orderGoods.goods.name}</td>
					<td>${ls.number}</td>
					<td>${ls.user.username}</td>
					<td><fmt:formatDate value="${ls.createDate}" pattern="yyyy-MM-dd"/></td>
					<td>${ls.remark}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
		<div style="text-align: center;"><a class="mini-button" align="right" onclick="$('#dataDiv').jqprint(); ">打印</a></div>
</body>

</html>
<script type="text/javascript">
    mini.parse();
    var createDateStartObj = document.getElementById("createDateStart");
    	var createDateEndObj = document.getElementById("createDateEnd");
       
    function showAll(e){
    	var d = document.getElementsByName("display");
    	for(var i=0;i<d.length;i++){
    		var old = d[i].style.display;
    		d[i].style.display=old=="none"?"":"none";
    	}
    	e.innerHTML="..隐藏";
    }
    
    function orderInfo(id){
		openWindow("<%=basePath%>order_info.do?id="+id+"&type=<%=Order.TYPE_BUY_ORDER%>","单据明细",550,500)
    }
</script>

