<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'batchAdd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	  '
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="/resources/css/basecontrol.css" />
  </head>
  
  <body>
  	<div align="center">
   		<form action="authorize.do" method="post" onsubmit="return batchCheck()">
			<table border="" style="border-color: black;border-style: double;" cellspacing="1" cellpadding="3px;" width="50%">
				<tr>
					<td colspan="2" align="center" style="background: silver;">授权<span class="spanMes">${controllerMessage}</span>&nbsp;<span class="spanErr">${controllerError}</span></td></tr>
				<tr>
					<td width="10%">
						MAC地址(请复制给授权商)：
					</td>
					<td width="20%">
						${macCode}
					</td>
				</tr>
				<tr>
					<td width="10%">
						输入授权码：
					</td>
					<td width="20%">
						<input name="authorizeCode" id="authorizeCode" type="text">
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<input type="submit" value="提交" />&nbsp;&nbsp;
						<input type="button" value="返回登陆页" onclick="window.location='<%= basePath %>login.jsp'" />
					</td>
				</tr>
			</table>
		</form>
		</div>
  </body>
</html>
