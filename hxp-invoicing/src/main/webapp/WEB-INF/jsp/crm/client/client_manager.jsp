<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute("user");
	if(user==null){
		request.getRequestDispatcher("/login.jsp").forward(request,response);
	}
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
	        font-size: 15px;
	        font-family:'Trebuchet MS',Arial,sans-serif;
	    }    
	    </style>
</head>
<body>
<!--工具栏-->
	<div class="mini-toolbar" style="padding:2px;border:0;" id="searchForm" >
			<a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增客户</a>
			<a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑客户</a>
			<span class="separator"></span>
			<a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
			<a class="mini-button" iconCls="icon-print" plain="true" onclick="exportExcel(grid,'客户列表')">导出EXCEL</a>
			<input class="mini-textbox" name="key" onenter="search()"/>
			<a class="mini-button" onclick="search()" iconCls="icon-search" plain="true" onclick="search()">关键字查询</a>
	</div>
	<div class="mini-fit" style="height:100px;" align="center">
	 	<div id="datagrid1" class="mini-datagrid" onrowdblclick="edit" pageSize="50"  url="<%=basePath %>client_list_json.do" style="width: 100%; height: 100%" showColumnsMenu="true" allowAlternating="true" allowCellSelect="true" multiSelect="true"   editNextOnEnterKey="true"  editNextRowCell="true" allowCellEdit="true"  showfooter="false" contextMenu="#gridMenu" >
	        <div property="columns">
	            <div field="id" name="rowid" width="50" align="center" headeralign="center" allowSort="true">序号</div>
	            <div field="name" align="center" headeralign="center" width="160" allowSort="true">客户名</div>
	            <div field="phone" align="center" headeralign="center" width="50" allowSort="false">联系电话</div>
	        	<div field="address" align="center" headeralign="center" width="50" allowSort="false">地址 </div>
	        	<div field="remark" align="center" headeralign="center" width="50" allowSort="false">备注</div>
	        </div>
	    </div>
	</div>
	
	 <ul id="gridMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">              
        <li iconCls="icon-add" onclick="add">新增客户</li>
	    <li iconCls="icon-edit" onclick="edit">编辑客户</li>
   	 </ul>
</body>

</html>
<script type="text/javascript">
        mini.parse(); 
        var grid = mini.get("datagrid1"); 
        search();

		function onBeforeOpen(e) {
		    var menu = e.sender;
		    var row = grid.getSelected();
		    var rowIndex = grid.indexOf(row);    
		      
		    if (!row) {
		        e.cancel = true;
		        //阻止浏览器默认右键菜单
		        e.htmlEvent.preventDefault();
		        return;
		    }else{
			   }
		}

		function search(){
			 var form = new mini.Form("#searchForm");
			form.validate();
			if (form.isValid() == false) return;
	      	//提交数据
			var data = form.getData();      
			var json = mini.encode(data);
			grid.load({key:json});
		}

		function add(){
			openWindow("<%=basePath%>jsp/crm/client/client_add.jsp","添加客户",650,500,function(){
				grid.reload();
			});
		}

		function edit(){
			var row = grid.getSelected();
			if(!row){
				showTips("请选中一条记录","danger");
				return;
			}
			openWindow("<%=basePath%>client_info_edit_page.do?id="+row.id,"修改客户",650,500,function(){
				grid.reload();
			});
		}


		 grid.on("drawcell", function (e) {
	            var record = e.record,
		        column = e.column,
		        field = e.field,
		        value = e.value;

	            //格式化日期
	            if (field == "birthday") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd");
	            }

	            if (field == "status") {
	            	 if (e.value == "0") {
		                    e.cellHtml = "已禁用"
		                    e.cellStyle = "background:#FAD1FA";
		                } else if (e.value == "1"){
		                    e.cellHtml = "正常"
		                }else{
		                	 e.cellHtml = "未知"
			            }
	            }
	            if (field == "isLogin") {
					if (e.value) {
	                    e.cellHtml = "已登陆"
	                    e.cellStyle = "background:#D6FFCC";
	                } else{
	                	 e.cellHtml = "未登陆"
		            }
	            }
	            

	            //给帐号列，增加背景色
	            if (field == "loginname") {
	                e.cellStyle = "background:#ecedef";
	            }

	            //action列，超连接操作按钮
	            if (column.name == "action") {
	                e.cellStyle = "text-align:center";
	                e.cellHtml = '<a href="javascript:edit(\'' + record.id + '\')">Edit</a>&nbsp; '
	                    + '<a href="javascript:del(\'' + record.id + '\')">Delete</a>'
	            }

	            //将性别文本替换成图片
	            if (column.field == "userInfo.sex") {
	                if (e.value == "1") {
	                    e.cellHtml = "男"
	                } else {
	                    e.cellHtml = "女"
	                }
	            }
	        });

</script>