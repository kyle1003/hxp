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
		<script type="text/javascript" src="<%=basePath%>plugins/hurryPrint/hurryPrint.js"></script>
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
	    .xhx{
	    	border-bottom: 2px black solid;
	    }
	    </style>
	</head>
	
<body oncontextmenu="return false">
	<div style="width: 400px;margin: auto;" id='printtt' >
		<table style="width: 100%;">
			<tr>
				<td colspan="3" style="text-align: center;">
					<c:if test="${all}">
						日结单据
					</c:if>
					<c:if test="${!all}">
						结班单据
					</c:if>
				</td>
			</tr>
			<tr >
				<td colspan="3" style="text-align: center; font-size: 18px;">
					时间:<%=DateTimeUtils.format(new Date(),DateTimeUtils.YEAR_MONTH_DAY_HOUR_MINUTE) %>
					操作员:${sessionScope.user.username}
				</td>
			</tr>
		</table>
		<c:set var="show" value="${showAll?'':'display:none'}"></c:set>
		<table style="width: 100%;text-align: center;" cellpadding="0" cellspacing="5">
			<tr style="${show}" ><td colspan="4" class="topTd"></td></tr>
			<tr>
				<td colspan="2" style="width: 40%" align="center"> 
					流水号
				</td>
				<td colspan="2" style="width: 20%" align="center"> 
					小计(元)
				</td>
			</tr>
			<tr style="${show}" ><td colspan="4" class="topTd"></td></tr>
			<tr style="${show}">
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
			<tr ><td colspan="4" class="topTd"></td></tr>
			<c:set var="allcountPrice" value="0"></c:set>
			<c:set var="allcountnumber" value="0"></c:set>
			<c:set var="allcountPay" value="0"></c:set>
			<c:forEach var="succession" items="${successions}">
				<c:forEach var="sellOrder" items="${succession.sellOrderList}">
						<c:set var="countPrice" value="0"></c:set>
						<c:set var="countnumber" value="0"></c:set>
						<c:set var="maxCountNumber" value="2"></c:set>
						<c:set var="allcountPay" value="${sellOrder.pay+allcountPay}"></c:set>
						<c:forEach var="ogoods" items="${sellOrder.orderGoods}">
							<tr style="${show}" title="${succession.remark}">
								<td style="font-weight: 0;font-size: 17px;"> 
									${ogoods.goods.name}
								</td>
								<td style="font-weight: 0;font-size: 17px"> 
									${ogoods.number}
								</td>
								<td style="font-weight: 0;font-size: 17px"> 
									${ogoods.price}
								</td>
								<td style="font-weight: 0;font-size: 17px"> 
									<fmt:formatNumber value="${ogoods.price*ogoods.number}" pattern="##.##"></fmt:formatNumber> 
									<fmt:formatNumber var="countPrice" value="${countPrice+(ogoods.price*ogoods.number)}" pattern="##.##"></fmt:formatNumber> 
								</td>
							</tr>
							<c:set var="countnumber" value="${countnumber+1}"></c:set>
						</c:forEach>
						<c:if test="${countnumber>0}">
						<c:set var="allcountnumber" value="${countnumber+allcountnumber}"></c:set>
						<c:set var="allcountPrice" value="${countPrice+allcountPrice}"></c:set>
						<tr title="${succession.remark }">
							<td colspan="2" align="center" style="font-weight: 0;font-size: 18px;">
								${sellOrder.id}
							</td>
							<td colspan="2" align="center" style="font-weight: 0;font-size: 18px;">
								${countPrice}
							</td>
						</tr>
						<tr style="${show}" ><td colspan="4" class="topTd"></td></tr>
						</c:if>
				</c:forEach>
			</c:forEach>
			<tr style="${showAll?'display:none':''}"><td colspan="4" class="topTd"></td></tr>
			
		</table>
		<table style="width: 100%;text-align: center;">
			<tr>
				<td align="center">
					<span style="font-size: 19px;">
						合计金额:
					</span>
				</td>
				<td align="center" class="xhx">
					<span style="font-size: 19px;">
					${allcountPrice}
					</span>
				</td>
				<td  align="center">
					<span style="font-size: 19px;">
						实收金额:
					</span>
				</td>
				<td align="center"  class="xhx">
					<span style="font-size: 19px;">
					<fmt:formatNumber value="${allcountPay}" pattern="##.##"></fmt:formatNumber> 
					</span>
				</td>
			</tr>
			<tr >
				<td align="center">
					<span style="font-size: 19px;">
						挂 &nbsp;&nbsp;&nbsp;账:
					</span>
				</td>
				<td align="center">
					 ______
				</td>
				<td colspan="2" align="center">
					<c:if test="${showAll}">
						<a href="javascript:void(0);" onclick="goPrint(this)">打印</a>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
</body>

</html>
	<script type="text/javascript">
		function goPrint(e){
			//var url = window.location.href;
			//window.location.href=url.replace("showAll=true","showAll=false");
			var printUrl = "<%=basePath%>get_workover_printData.do";
			doAjax(printUrl,{all:"${printAll}",id:"${id}",t:new Date().getTime()},"POST",function(d, textStatus) {
				var json = JSON.parse(d); 
				if(!json.success){
					alert("打印失败!"+json.msg);
					return;
				}else{
					printHttp(json.data);
				}
			});
		}

		//function printHttp(data){
			//var from = document.getElementById("printFrom");
			//from.href="http://127.0.0.1:5335/?"+data;
			//from.click();
			//window.open("http://127.0.0.1:5335/?"+data,"newwindow2","height=100,width=500,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
			//doAjax("http://127.0.0.1:5335/?"+data,{},"GET",function(d, textStatus) {
				 //alert(d+"");
			//});
		//}
	</script>
	 
