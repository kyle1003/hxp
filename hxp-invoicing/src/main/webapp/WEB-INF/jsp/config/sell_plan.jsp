<%@ page language="java"  pageEncoding="UTF-8"
%>
<%@page import="cn.hurry.po.plan.SellPlan"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  <fieldset style="width:500px;border:solid 1px #aaa;margin-top:8px;position:relative;"> 
        <legend>成本核算方案</legend>
        <div id="editForm1" style="padding:5px;">
            <input class="mini-hidden" name="id"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:100px;" align="right">当前方案：</td>
                    <td style="width:100px;" ><input class="mini-combobox" style="width: 150px" id="type" name="type" value="${plan}"  data="types"/></td>
              		<td><a class="mini-button" onclick="submitForm()">保存</a></td>
                </tr>
            </table>
        </div>
    </fieldset>
  </div>
  </body>
</html>
<script type="text/javascript">
		var types=[{id:'<%=SellPlan.TYPE_FIRSTIN_FRISTOUT%>',text:'先进先出'},{id:'<%=SellPlan.TYPE_LASTTIN_FRISTOUT%>',text:'后进先出'}];
        mini.parse();
      	 //提交表单
		function submitForm() {
            var type=mini.get("type").value;
          	$.ajax({
				type:"post", //请求方式
				url:"<%=basePath%>sellPlan_update.do",
				data : { //发送给后台的数据
					type:type,
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