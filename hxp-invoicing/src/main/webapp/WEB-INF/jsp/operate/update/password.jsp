<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>后台管理系统</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script src="<%=basePath%>plugins/miniui/boot.js" type="text/javascript"></script>
  </head>
  
  <body>
  <div id="form">
    <fieldset style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;">
        <legend>修改密码</legend>
        <div id="editForm1" style="padding:5px;">
            <input class="mini-hidden" name="id"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:80px;">新密码：</td>
                    <td style="width:150px;" colspan="2"><input  id="password" name="password" class="mini-password" required="true" /></td>
                </tr>
                <tr>
                    <td style="width:80px;">再次输入：</td>
                    <td style="width:150px;"><input name="confirmPassword"  id="confirmPassword" class="mini-password" required="true"/></td>
                </tr>
            </table>
        </div>
    </fieldset>
    </div>
   <div style="text-align: center;">
	  	<br/><br/>
	  	<input type="hidden" name="id" id="userid" value="<%=request.getParameter("id")%>"/>
	  	<a class="mini-button" onclick="submitForm()">提交</a>
	  </div>
  </body>
</html>
<script type="text/javascript" >
	         mini.parse();
 			 function submitForm() {
			  		    var form = new mini.Form("#form");
			            form.validate();
			            var password=mini.get("password").value;
			            var confirmPassword=mini.get("confirmPassword").value;
			            if(password!=confirmPassword){
			            	mini.alert("两次输入密码不相同!");
			            	return;
			            }
			            if (form.isValid() == false){return};
			            var id = document.getElementById("userid").value;
			            $.ajax({
									type:"post", //请求方式
									url:"<%=basePath%>doUpdatePassword.do",
									data : { //发送给后台的数据
										id:id,
										password:password,
										confirmPassword:confirmPassword,
										t : Math.random()
									},
									//请求成功后的回调函数有两个参数
									success : function(data, textStatus) {
									  mini.alert(data);
									},
									error : function(msg) {
									  mini.alert("服务器忙!");
									}
								});
		            }
		  
</script>