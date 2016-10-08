<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"  %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	User user = (User)request.getSession().getAttribute("user");
	if(user!=null){
		request.getRequestDispatcher("index.jsp").forward(request,response);
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<title>进销存管理系统</title>
	<link rel="stylesheet" href="<%=basePath%>resources/ht/css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="<%=basePath%>resources/ht/css/invalid.css" type="text/css" media="screen" />
	
	<script type="text/javascript">
		function refresh(){
			//重新获取验证码图片的src属性
			document.getElementById("authImg").src='<%= basePath %>authImg.do?now=' + new Date();
		}
		// 解决frameset跳转不正常问题
		if(top.location != this.location){
    		top.location.replace('<%= basePath %>login.jsp');
    	}
    	function check(){
    		var username = document.getElementById("username").value;
    		var password = document.getElementById("password").value;
    		var vercode = document.getElementById("vercode").value;
    		if(username==null || username==""){
    			alert("请正确输入账号");
    			return false;
    		}
    		if(password==null || password==""){
    			alert("请正确输入密码");
    			return false;
    		}
    		if(vercode==null || vercode==""){
    			alert("请正确输入验证码");
    			return false;
    		}
    		return true;
    	}
	</script>

</head>
<body id="login">
	<div id="login-wrapper" class="png_bg">
		<div id="login-top">
	    	<span style="width: 200px; line-height: 80px; color:black; font-family: '微软雅黑'; font-weight: 600; font-size: 20px;">进销存管理系统</span>
	    </div>
		<div id="login-content">
			<div id="login_bg">
				<form action="login.do" method="post" onsubmit="return check();">
				<div id="login_top_title">管理员登录</div>
		        <div id="login_name">用户名：</div>
		        <div id="name_input"><input type="text" class="a" id="username" name="username" /></div>
				<div class="name_txt">密 码：</div>
				<div class="input_box"><input type="password" class="a" id="password" name="password" /></div>
		        <div class="name_txt">验证码：</div>
		        <div class="code_txt"><input type="text" class="b" id="vercode" name="vercode" /></div>
		        <div class="code_display"><img src="<%= basePath %>authImg.do" id="authImg" style="width: 80px;height: 28px;margin-top: -4px;cursor: pointer"  onclick="refresh()"/></div>
		        <div class="code_display"><a href="javascript:void(0)" onclick="refresh()">换一张</a></div>
		        <div id="box_button">
					<div class="btns"><input type="submit" value="登 录" style="cursor:pointer" class="sbt" /></div>
		            <div class="btns"><input type="reset" value="重 置" style="cursor:pointer" class="sbt" /></div>
		        </div>
		        </form>
		    </div> 
		</div>
	</div>
</body>
</html>