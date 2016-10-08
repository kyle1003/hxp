<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.hurry.demo.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<base href="<%=basePath%>">
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2">
		<meta http-equiv="description" content="this is my page">
		<script type="text/javascript" src="<%=basePath%>plugins/miniui/boot.js"></script>
		<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	        font-size: 12px;
	        font-family:'Trebuchet MS',Arial,sans-serif;
	    }    
	     fieldset{
			border:solid 1px #aaa;
		}        
	    .hideFieldset{
	        border-left:0;
	        border-right:0;
	        border-bottom:0;
	    }
	    .hideFieldset .fieldset-body{
	        display:none;
	    }
	    .red{
	    	color: red;
	    }
	    </style>
</head>
<body>
 	<fieldset id="form" style="width:90%;margin: auto;margin-top: 50px;">
 		<input class="mini-hidden" name="id" />
 		<input class="mini-hidden" name="status" value="1" />
        <legend><span>添加客户信息</span></legend>
        <div class="fieldset-body">
            <table class="form-table" border="0" style="width: 100%" cellpadding="1" cellspacing="2">
                <tr>
                    <td class="form-label" style="width:100px;" align="right">姓名：</td>
                    <td>
                        <input name="name" class="mini-textbox" required="true" requiredErrorText="姓名不能为空"/>
                    </td>
                    <td class="form-label"   align="right">联系电话：</td>
                    <td>
                        <input name="phone" class="mini-textbox" required="true" requiredErrorText="联系电话不能为空"/>
                    </td>
                </tr>
                <tr>
                    <td class="form-label" style="width:100px;" align="right">地址：</td>
                    <td>
                        <input name="address" class="mini-textbox"/>
                    </td>
                    <td class="form-label"   align="right">备注</td>
                    <td>
                        <input name="remark" class="mini-textarea"/>
                    </td>
                </tr>
                <tr >
                    <td colspan="2" align="right">
                    	<br />
						<a class="mini-button" iconCls="icon-ok" onclick="doSave()">提交</a>&nbsp;&nbsp;
					</td>
                    <td colspan="2" align="left">
                    <br />
                       &nbsp;&nbsp;<a class="mini-button" iconCls="icon-no" onclick="new mini.Form('#form').reset()">重置</a>
                    </td>        
                </tr>
            </table>
        </div>
    </fieldset>
</body>

</html>
<script type="text/javascript">
        mini.parse(); 
		
		function doSave(){
			var form = new mini.Form('#form');
			form.validate();
	        if (form.isValid() == false) return;
	        //提交数据
			var data = form.getData();
			var json = mini.encode(data);
			var box = mini.loading("提交中...");
			doAjax("<%=basePath%>supplier_info_add.do",{json:json,t:new Date().getTime()},"POST",function(r,s){
				mini.hideMessageBox(box);
				 mini.alert(r);
			});
		}



</script>