<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
<%@page import="cn.hurry.po.inventory.Inventory"%>
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
	            	 <a class="mini-button" iconCls="icon-add" plain="true" onclick=" win.show()">新建盘点</a>
	            	 <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑盘点</a>
	                <a class="mini-button" iconCls="icon-find" plain="true" onclick="info()">详情</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	                <span class="separator"></span>
					条件过滤:日期:<input id="createDateStart" style="width: 80px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:createDateEndObj.value,onpicked:function(dp){search()}})"  />
	           		-<input id="createDateEnd" style="width: 80px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:createDateStartObj.value,onpicked:function(dp){search()}})"  />
	            	状态:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="status" data="statusType" value="all" valueField="id" textFiled="text" />
	            	仓库:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="store" value="-1" showNullItem="true" url="<%=basePath %>store_list_json.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	盘点人:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="user" value="-1" showNullItem="true" url="<%=basePath %>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	<input class="mini-textbox" id="key" onenter="search()"  />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	            </td>
	            </tr>
	        </table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid" showFooter="true"  onrowdblclick="info()"  style="width:100%;height:100%;"  url="<%=basePath%>inventory_list_json.do"  idField="id" sizeList="[5,10,20,50]" pageSize="10" >
	            <div property="columns">
	            	<div field="code" width="50" headerAlign="center" allowSort="true">编号</div> 
	                <div field="createDate" width="80" headerAlign="center" allowSort="true">盘点日期</div>
	                <div field="userId" width="80" headerAlign="center" allowSort="true">盘点人</div>
	                <div field="storeId" width="80" headerAlign="center" allowSort="true">仓库</div>
	                <div field="status" width="80" headerAlign="center" allowSort="true">状态</div>
	            </div>
	        </div> 
	    </div>
	     <div id="win1" class="mini-window" title="盘点" style="text-align: center;width:150px;height:130px;"  showMaxButton="false" showToolbar="false" showFooter="true" showModal="true" allowResize="false" allowDrag="true" >
		    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		        <a class="mini-button" id="submitButton" onclick="create()">提交</a>
		    </div>
		    	<table style="width: 100%" id="form">
		    		<tr>
		    			<td>仓&nbsp;&nbsp;&nbsp;库:<input class="mini-combobox" style="width: 80px" id="storeId" name="storeId"   url="<%=basePath %>store_list_json.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" required="true"/>
	            		</td>
		    		</tr>
		    		<tr>
		    			<td>
		    			盘点人:<input class="mini-combobox" style="width: 80px" id="userId" name="userId" url="<%=basePath %>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" required="true"/>
		    			</td>
		    		</tr>
		    	</table>
		</div>
</body>

</html>
    <script type="text/javascript">
    	var type = '<%=Order.TYPE_BUY_ORDER%>'
    	var statusType=[{'id':'all','text':'-所有-'},{'id':'<%=Inventory.STATUS_NOT_CHECKED%>','text':'未结转'},{'id':'<%=Inventory.STATUS_CHECKED%>','text':'已结转'}];
    	var createDateStartObj = document.getElementById("createDateStart");
    	var createDateEndObj = document.getElementById("createDateEnd");
        mini.parse();
        var grid = mini.get("datagrid");
        var store = mini.get("store");
        var user = mini.get("user");
		var win = mini.get("win1");
		search();
		window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}
		function search(){
			var key="type="+type;
			var keyobj = mini.get("key");
			var createDateStart = createDateStartObj.value;
			var createDateEnd = createDateEndObj.value;
			var status = mini.get("status");
			if(status.value!="all"){
				key+=",status="+status.value;
			}
			if(store.value!=-1&&store.value!=""){
				key+=",storeId="+store.value;
			}
			if(user.value!=-1&&user.value!=""){
				key+=",userId="+user.value;
			}
			if(createDateStart!=""){
				key+=",createDateStart="+createDateStart;
			}
			if(createDateEnd!=""){
				key+=",createDateEnd="+createDateEnd;
			}
			if(keyobj.value!=""){
				key+=",code="+keyobj.value
			}
				grid.load({key:key});
		}
	    
	    confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}
		
		function info(){
			var d = grid.getSelected();
			if(!d){mini.alert("请选择"); return;}
			openWindow("<%=basePath%>inventory_info.do?id="+d.id,"盘点明细",650,500)
		}
		
		function edit(){
			var d = grid.getSelected();
			if(!d){mini.alert("请选择"); return;}
			if(d.status=="已结转"){mini.alert(d.code+"已结转,无法编辑"); return;}
			openWindow("<%=basePath%>inventory_edit_page.do?id="+d.id,"盘点明细",650,500)
		}
		
		function create(){
			var form = new mini.Form("#form");
            form.validate();
            if (!form.isValid()){return};
             var store = mini.get("storeId");
       		 var user = mini.get("userId");
       		 if(store.value==""||user.value==""){
       		 	mini.alert("请选择仓库和盘点人");
       		 	return;
       		 }
            var data = form.getData();
			var json = mini.encode(data);
			confirm("<lable style='color:red'>确认盘点?</lable>","确认?",function(e){
				if(e!="ok")return;
				var messageid = mini.loading("正在生成盘点,请稍候...", "请稍候");
				doAjax("<%=basePath%>inventory_add.do",{json:json,t:new Date().getTime()},"POST",function(text,status){
					mini.alert(text);
					grid.reload();
					 mini.hideMessageBox(messageid);
				});
			});
		}
    </script>

