<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute("user");
	if(user==null){
		request.getRequestDispatcher("login.jsp").forward(request,response);
	}
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
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	    .header
	    {
	        background:url(<%=basePath%>resources/ht/images/header.gif) repeat-x 0 -1px;
	    }
	    </style>
	</head>

	<body>
<!--Layout-->
       <div class="mini-toolbar" style="padding:2px;border:0;">
	        <table style="width:100%;">
	            <tr>
	            <td style="width:100%;">
	                <a class="mini-button" iconCls="icon-add" plain="true" onclick="showCenter('add')">添加</a>
	                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="showCenter('edit')">编辑</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	            </td>
	            <td style="white-space:nowrap;"><label style="font-family:Verdana;">搜索: </label>
	                <input class="mini-textbox" id="key"  />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="serch()">查询</a>
	                </td>
	            </tr>
	        </table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid" showFooter="false" style="width:100%;height:100%;"  url="<%=basePath%>store_list_json.do"  idField="id" sizeList="[5,10,20,50]" pageSize="10" >
	            <div property="columns">
	            	<div field="id" width="50" headerAlign="center" allowSort="false">编号</div> 
	                <div field="name" width="80" headerAlign="center" allowSort="false">名称</div> 
	                <div field="user" width="80" headerAlign="center" allowSort="false">负责人</div>
	                <div field="userId"  width="80" headerAlign="center" allowSort="false">负责人编号</div>
	            </div>
	        </div> 
	    </div>
	    
	    <div id="win1" class="mini-window" title="Window" style="width:300px;height:100px;"  showMaxButton="false" showToolbar="false" showFooter="true" showModal="true" allowResize="false" allowDrag="true" >
		    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		        <a class="mini-button" id="submitButton" onclick="sbmitForm">提交</a>
		    </div>
		    <div style="text-align: center;" id="form">
		    	<input class="mini-hidden" name="id" id="id" />
		    	仓库名:<input class="mini-textbox" style="width: 80px" name="name" id="name" required="true"/>
		    	负责人:<input class="mini-combobox" url="<%=basePath%>user_combobox.do" style="width: 80px" name="userId" id="userId" valueField="id" textField="text" required="true"/>
		    </div>
		</div>
</body>

</html>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
		var goodsTypeIdTemp = -1;
		var operateType="add";
		var nameObj = mini.get("name");
		var idObj = mini.get("id");
		var userObj = mini.get("userId");
		grid.load();
		window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}
		
		function showCenter(type) {
	        var win = mini.get("win1");
	        if(type=="edit"){
	        	win.setTitle("编辑仓库");
	        	var row = grid.getSelected();
				if(!row){mini.alert("请选择仓库");return;}
				operateType="edit";
				idObj.setValue(row.id);
				nameObj.setValue(row.name);
				userObj.setValue(row.userId);
	        }
	        if(type=="add"){
	        	win.setTitle("新增仓库");
	        	operateType="add";
	        	idObj.setValue(0);
				nameObj.setValue("");
				userObj.setValue("");
	        }
	        win.show();
	    }
	    
	    function sbmitForm(){
	    	if(operateType=="add"){
	    		addStore();
	    	}
	    	if(operateType=="edit"){
	    		editStore();
	    	}
	    }
	    
		function editStore(){
		 	var form = new mini.Form("#form");
            form.validate();
            if (!form.isValid()){return};
            var data = form.getData();
			var json = mini.encode(data);
			doAjax("<%=basePath%>store_edit.do",{json:json,t:new Date().getTime()},"POST",function(data, textStatus) {
				mini.alert(data);
				rid.reload();
			});
		}
		
		function addStore(){
			var form = new mini.Form("#form");
            form.validate();
            if (!form.isValid()){return};
            var data = form.getData();
			var json = mini.encode(data);
			doAjax("<%=basePath%>store_add.do",{json:json,t:new Date().getTime()},"POST",function(data, textStatus) {
				 mini.alert(data);
				 grid.reload();
			});
		}
    </script>

