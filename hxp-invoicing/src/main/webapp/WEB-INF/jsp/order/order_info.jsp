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
	<div style="width: <%=order.getType()==Order.TYPE_BUY_ORDER?"700":"500"%>px;margin: auto;" id="dataDiv">
		<table style="width: 100%;">
			<tr>
				<td colspan="3" style="text-align: center;font-size: 20px;font-weight: 800;">
					 <%=order.getType()==Order.TYPE_BUY_ORDER?"入库单":order.getType()==Order.TYPE_SELL_ORDER?"销售单":order.getType()==Order.TYPE_BUY_RETURN_ORDER?"入库退货单":order.getType()==Order.TYPE_SELL_RETURN_ORDER?"销售退货单":"未知"%>
				<hr/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px">
				仓库:<%=order.getStore().getName()%>
				</td>
				<td style="text-align: center;">
				经办人:<%=order.getUser()==null?"-":order.getUser().getUsername() %>
				</td>
				<td style="width: 200px">
					编号:<%=order.getId()%><br />
					日期:<%=DateTimeUtils.format(order.getCreateDate(),"yyyy年MM月dd日")%>
				</td>
			</tr>
		</table>
		<table style="border: 1px black solid;width: 100%;text-align: center;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width: 18%;border-right: 1px black solid; "> 
					品名
				</td>
				<td style="width: 8%;border-right: 1px black solid; "> 
					规格
				</td>
				<td style="width: 8%;border-right: 1px black solid; "> 
					单位
				</td>
				<td style="width: 8%;border-right: 1px black solid; "> 
					数量
				</td>
				<% if(order.getType()==Order.TYPE_BUY_ORDER){%>
				<td style="width: 13%;border-right: 1px black solid; "> 
				 	剩余数量
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
				 	生产日期
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
				 	有效期
				</td>
				<td style="width: 5%;border-right: 1px black solid; "> 
				 	生产批号
				</td>
				<td style="width: 5%;border-right: 1px black solid; "> 
				 	批准文号
				</td>
				<%}%>
				<td style="width: 8%;;border-right: 1px black solid;"> 
					单价
				</td>
				<td style="width: 8%;"> 
					总价
				</td>
			</tr>
			<c:set var="countPrice" value="0"></c:set>
			<c:forEach var="orderGoods" items="${order.orderGoods}">
				<tr>
					<td style="border-right: 1px black solid;;border-top:1px black solid; "> 
						${orderGoods.goods.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${orderGoods.goods.norms.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${orderGoods.goods.unit.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${orderGoods.number}
					</td>
					<% if(order.getType()==Order.TYPE_BUY_ORDER){%>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
					 	${orderGoods.surplusNumber}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
					 	 <fmt:formatDate value="${orderGoods.productionDate}" pattern="yyyy-MM-dd" /> 
					</td> 
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
					 	<fmt:formatDate value="${orderGoods.shelfLife}" pattern="yyyy-MM-dd" /> 
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${orderGoods.productionBatchNumber}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${orderGoods.approvalDocument}
					</td>
					<%}%>
					<td style="border-right: 1px black solid;border-top:1px black solid;"> 
						${orderGoods.price}
					</td>
					<td style="border-top:1px black solid;"> 
						<fmt:formatNumber value="${orderGoods.price*orderGoods.number}" pattern="##.##"></fmt:formatNumber> 
						<fmt:formatNumber var="countPrice" value="${countPrice+(orderGoods.price*orderGoods.number)}" pattern="##.##"></fmt:formatNumber> 
					</td>
				</tr>
			</c:forEach>
		</table>
		<div style="text-align: right;">应付金额:${countPrice}&nbsp;&nbsp;实付金额:${order.pay}</div>
	</div>
	<div style="text-align: center;"><a class="mini-button" align="right" onclick="$('#dataDiv').jqprint(); ">打印</a></div>
</body>

</html>
<script type="text/javascript">
    mini.parse();
</script>

