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
		<style type="text/css">
	    .header
	    {
	        background:url(<%=basePath%>resources/ht/images/header.gif) repeat-x 0 -1px;
	    }
		 html,body
	    {
	        width:100%;
	        height:100%;
	        border:0;
	        margin:0;
	        padding:0;
	        overflow:visible;
	    }
	    </style>
	</head>

<body>
<div id="form">
	<div style="width: 500px;margin: auto;" id="dataDiv">
		<table style="width: 100%;">
			<tr>
				<td colspan="3" style="text-align: center;font-size: 20px;font-weight: 800;">
					 <%=order.getType()==Order.TYPE_BUY_ORDER?"入库单":order.getType()==Order.TYPE_SELL_ORDER?"销售单":order.getType()==Order.TYPE_BUY_RETURN_ORDER?"采购退货单":order.getType()==Order.TYPE_SELL_RETURN_ORDER?"销售退货单":"未知"%>
				<hr/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px">
				仓库:<input class="mini-combobox" name="storeId" value="<%=order.getStore().getId()%>" url="<%=basePath%>store_list_json.do?type=<%=JSON.TYPE_COMBOBOX%>" style="width: 50px" required="true" />
				</td>
				<td style="text-align: center;">
				经办人:<input class="mini-combobox"  name="userId" value="<%=order.getUser().getId()%>"  url="<%=basePath%>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX%>" style="width: 50px" required="true" /></td>
				<td style="width: 200px">
					编号:<%=order.getId()%><br />
					日期:<%=DateTimeUtils.format(order.getCreateDate(),"yyyy年MM月dd日")%>
				</td>
			</tr>
		</table>
		<input class="mini-hidden" name="id" value="${order.id}"/>
		<input class="mini-hidden" name="type" value="<%=order.getType()%>"/>
		<table  style="border: 1px black solid;width: 100%;text-align: center;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width: 20%;border-right: 1px black solid; "> 
					品名
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
					单位
				</td>
				<td style="width: 15%;border-right: 1px black solid; "> 
					规格
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
			<c:forEach var="orderGoods" items="${order.orderGoods}">
				<tr>
					<td style="border-right: 1px black solid;;border-top:1px black solid; "> 
						${orderGoods.goods.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${orderGoods.goods.unit.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						${orderGoods.goods.norms.name}
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid; "> 
						<input class="mini-textbox" required="true" vtype="float" name="number${orderGoods.id}" value="${orderGoods.number}" />
					</td>
					<td style="border-right: 1px black solid;border-top:1px black solid;"> 
						<input class="mini-textbox" required="true" vtype="float" name="price${orderGoods.id}" value="${orderGoods.price}" />
					</td>
					<td style="border-top:1px black solid;"> 
						<fmt:formatNumber value="${orderGoods.price*orderGoods.number}" pattern="##.##"></fmt:formatNumber> 
						<fmt:formatNumber var="countPrice" value="${countPrice+(orderGoods.price*orderGoods.number)}" pattern="##.##"></fmt:formatNumber> 
					</td>
				</tr>
			</c:forEach>
		</table>
		<div style="text-align: right;">应付金额:${countPrice}&nbsp;&nbsp;实付金额:<input class="mini-textbox" required="true" vtype="float" name="pay" value="${order.pay}" /></div>
		</div>
	</div>
	<div style="text-align: center;"><a class="mini-button" align="right" onclick="submitForm">保存</a></div>
</body>

</html>
<script type="text/javascript">
    mini.parse();
    function submitForm(){
    		var form = new mini.Form("#form");
            form.validate();
            if (!form.isValid()){return};
            var data = form.getData();
			var json = mini.encode(data);
			doAjax("<%=basePath%>order_edit.do",{type:'<%=order.getType()%>',json:json,t:new Date().getTime()},"POST",function(text,status){
				mini.alert(text);
			});
    }
</script>

