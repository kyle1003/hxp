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
	<div style="width: 500px;margin: auto;" id="dataDiv">
	<fieldset>
		<legend>单据详情</legend>
	
		<table style="width: 100%;">
			<tr>
				<td colspan="3" style="text-align: center;font-size: 20px;font-weight: 800;">
					 <%=order.getType()==Order.TYPE_BUY_ORDER?"采购单":order.getType()==Order.TYPE_SELL_ORDER?"销售单":order.getType()==Order.TYPE_BUY_RETURN_ORDER?"采购退货单":order.getType()==Order.TYPE_SELL_RETURN_ORDER?"销售退货单":"未知"%>
				<hr/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px">
				仓库:<%=order.getStore().getName()%>
				</td>
				<td style="text-align: center;">
				经办人:<%=order.getUser().getUsername() %>
				</td>
				<td style="width: 200px">
					编号:
					<a href="javascript:void(0)" style="color: red;" onclick="orderInfo('<%=order.getId()%>',<%=Order.TYPE_SELL_ORDER%>)"><%=order.getId()%></a>
					<br />日期:<%=DateTimeUtils.format(order.getCreateDate(),"yyyy年MM月dd日")%>
				</td>
			</tr>
		</table>
		<table style="border: 1px black solid;width: 100%;text-align: center;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width: 20%;border-right: 1px black solid; "> 
					品名
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
					规格
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
					单位
				</td>
				<td style="width: 20%;border-right: 1px black solid; "> 
					数量
				</td>
				<td style="width: 20%;;border-right: 1px black solid;"> 
					单价
				</td>
				<td style="width: 20%;"> 
					总价
				</td>
			</tr>
			<c:set var="countPrice" value="0"></c:set>
			<c:set var="countnumber" value="0"></c:set>
			<c:set var="maxCountNumber" value="2"></c:set>
			<c:forEach var="orderGoods" items="${order.orderGoods}">
				<tr  ${countnumber>maxCountNumber?"name='display' style='display: none;'":""}>
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
					<td style="border-right: 1px black solid;border-top:1px black solid;"> 
						${orderGoods.price}
					</td>
					<td style="border-top:1px black solid;"> 
						<fmt:formatNumber value="${orderGoods.price*orderGoods.number}" pattern="##.##"></fmt:formatNumber> 
						<fmt:formatNumber var="countPrice" value="${countPrice+(orderGoods.price*orderGoods.number)}" pattern="##.##"></fmt:formatNumber> 
					</td>
				</tr>
					<c:set var="countnumber" value="${countnumber+1}"></c:set>
			</c:forEach>
		</table>
		<c:if test="${countnumber>maxCountNumber}">
			<a href="javascript:void(0)" onclick="showAll(this);">..展开</a>
		</c:if>
		<div style="text-align: right;">应付金额:${countPrice}&nbsp;&nbsp;实付金额:${order.pay}</div>
		</fieldset>
	</div>
	<fieldset>
			<legend style="color: red;">采购单消耗明细</legend>
			<table style="border: 1px gray solid;width: 100%;text-align: center;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width: 20%;border-right: 1px gray solid; "> 
					采购单编号
				</td>
				<td style="width: 35%;border-right: 1px gray solid; "> 
					销售商品
				</td>
				<td style="width: 15%;border-right: 1px gray solid; "> 
					本次消耗数量
				</td>
				<td style="width: 15%;border-right: 1px gray solid; "> 
					本次剩余数量
				</td>
				<td style="width: 15%;"> 
					备注
				</td>
			</tr>
			<c:forEach var="goodsInfo" items="${infos}">
				<tr>
					<td style="border-right: 1px gray solid;border-top:1px gray solid; ">
						<a href="javascript:void(0)" style="color: red;" onclick="orderInfo('${goodsInfo.buyOrderId}','<%=Order.TYPE_BUY_ORDER%>')">${goodsInfo.buyOrderId}</a>
					</td>
					<td style="border-right: 1px gray solid;border-top:1px gray solid; "> 
						${goodsInfo.sellOrderGoods.goods.name}
					</td>
					<td style="border-right: 1px gray solid;border-top:1px gray solid; "> 
						${goodsInfo.number}
					</td>
					<td style="border-right: 1px gray solid;border-top:1px gray solid; "> 
						${goodsInfo.surplusNumber}
					</td>
					<td style="border-top:1px gray solid;"> 
						${goodsInfo.remark}
					</td>
				</tr>
			</c:forEach>
			</table>
		</fieldset>
</body>

</html>
<script type="text/javascript">
    mini.parse();
    
    function showAll(e){
    	var d = document.getElementsByName("display");
    	var temp="";
    	for(var i=0;i<d.length;i++){
    		var old = d[i].style.display;
    		temp = old;
    		d[i].style.display=old=="none"?"":"none";
    	}
    	e.innerHTML= temp!=""?"..隐藏":"..展开";
    }
    
    function orderInfo(id,type){
		openWindow("<%=basePath%>order_info.do?id="+id+"&type="+type,"单据明细",550,500)
    }
</script>

