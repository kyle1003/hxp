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
	Order order = (Order)request.getAttribute("order");
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
		<style type="text/css">
	     html,body
	    {
	        width:100%;
	        height:100%;
	        border:0;
	        margin:0;
	        padding:0;
	        overflow:visible;
	        font-size: 22px;
	        font-weight: 800;
	    }  
	    .header
	    {
	        background:url(<%=basePath%>resources/ht/images/header.gif) repeat-x 0 -1px;
	    }
	    .topTd{
	    	border-top:1px dashed;
	    }
	    td{
	    	margin-top: 13px;
	    }
	    </style>
	</head>
	
<body onload="printvb();window.close()">
	<div style="width: 400px;margin: auto;" id='printtt' >
		<table style="width: 100%;">
			<tr >
				<td colspan="3" style="text-align: center;">
					门诊软件收费系统
				</td>
			</tr>
			<tr >
				<td colspan="3" style="text-align: center;">
					《***欢迎光临***》
				</td>
			</tr>
			<tr >
				<td colspan="3" style="text-align: center; font-size: 18px;">
					就诊时间:<%=DateTimeUtils.format(new Date(),DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE) %>
					操作员:${sessionScope.user.username}
				</td>
			</tr>
			<tr >
				<td colspan="3" style="text-align: center;font-size: 18px;">
					流水号:${order.id}
				</td>
			</tr>
			<tr ><td colspan="3" class="topTd"></td></tr>
		</table>
		<table style="width: 100%;text-align: center;" cellpadding="0" cellspacing="5">
			<tr>
				<td style="width: 40%" align="center"> 
					品名
				</td>
				<td style="width: 20%" align="center"> 
					数量
				</td>
				<td style="width: 20%" align="center"> 
					售价
				</td> 
				<td style="width: 20%" align="center"> 
					金额
				</td>
			</tr>
			<c:set var="countPrice" value="0"></c:set>
			<c:set var="countnumber" value="0"></c:set>
			<c:set var="maxCountNumber" value="2"></c:set>
			<c:forEach var="orderGoods" items="${order.orderGoods}">
				<tr>
					<td style="font-weight: 0;font-size: 19px;"> 
						${orderGoods.goods.name}
					</td>
					<td style="font-weight: 0;font-size: 19px"> 
						${orderGoods.number}
					</td>
					<td style="font-weight: 0;font-size: 19px"> 
						${orderGoods.price}
					</td>
					<td style="font-weight: 0;font-size: 19px"> 
						<fmt:formatNumber value="${orderGoods.price*orderGoods.number}" pattern="##.##"></fmt:formatNumber> 
						<fmt:formatNumber var="countPrice" value="${countPrice+(orderGoods.price*orderGoods.number)}" pattern="##.##"></fmt:formatNumber> 
					</td>
				</tr>
					<c:set var="countnumber" value="${countnumber+1}"></c:set>
			</c:forEach>
				<tr ><td colspan="4" class="topTd"></td></tr>
				<tr>
					<td  colspan="4" style="width: 50%; "> 
					应付:<fmt:formatNumber value="${sdp}" pattern="##.##"></fmt:formatNumber> 
					</td>
				</tr>
				<tr>
					<td  colspan="4" style="width: 50%; " align="center" > 
					实付:<fmt:formatNumber value="${sf}" pattern="##.##"></fmt:formatNumber> 
						&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;找零:<fmt:formatNumber value="${zl}" pattern="##.##"></fmt:formatNumber> 
					</td>
				</tr>
				<tr ><td colspan="4" class="topTd"></td></tr>
				<tr ><td colspan="4">欢迎下次光临</td></tr>
				<tr ><td colspan="4">联系电话:85615120</td></tr>
		</table>
	</div>
</body>

</html>
	<object id="WB" width="0" height="0" classid="clsid:8856F961-340A-11D0-A96B-00C04FD705A2"></object> 
	<script language="VBScript"> 
		Sub window_onunload 
		On Error Resume Next 
		Set WB = nothing 
		End Sub 
		Sub vbPrintPage ( x , y , z ) 
		OLECMDID_PRINT = 6 
		OLECMDEXECOPT_DODEFAULT = 0 
		OLECMDEXECOPT_PROMPTUSER = 1 
		OLECMDEXECOPT_DONTPROMPTUSER = 2
		On Error Resume Next 
		WB.ExecWB x, y, z, 0 
		End Sub 
	</script> 
	<script language="VBScript">
	    sub printvb()
	    document.all.printtt.style.display="block"
	    wb.execwb 6,2,3 
 		//wb.execwb 7,1,0
	    End Sub
	</script>
