<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
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
	                <a class="mini-button" iconCls="icon-add" plain="true" onclick="add">添加损耗</a>
	                <span class="separator"></span>
	                 <a class="mini-button" iconCls="icon-find" plain="true" onclick="info()">明细</a>
	                 <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	            	条件过滤:日期:<input id="createDateStart" style="width: 80px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:createDateEndObj.value,onpicked:function(dp){search()}})"  />
	           		-<input id="createDateEnd" style="width: 80px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:createDateStartObj.value,onpicked:function(dp){search()}})"  />
	            	仓库:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="store" value="-1" showNullItem="true" url="<%=basePath %>store_list_json.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	操作员:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="user" value="-1" showNullItem="true" url="<%=basePath %>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	<input class="mini-textbox" id="key" onenter="search()"  />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	            </td>
	            </tr>
	        </table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid" allowCellSelect="true" multiSelect="true" onrowdblclick="info()" showFooter="true" style="width:100%;height:100%;"  url="<%=basePath%>loss_list_json.do"  idField="lid" sizeList="[5,10,20,50]" pageSize="20" >
	            <div property="columns">
	            	<div type="checkcolumn"></div>
	            	<div field="orderid" width="100" headerAlign="center" allowSort="false">采购单编号</div> 
	                <div field="code" width="80" headerAlign="center" allowSort="false">条码</div> 
	                <div field="name" width="100" headerAlign="center" allowSort="false">名称</div>
	                <div field="goodsType" width="50" headerAlign="center" allowSort="false">类别</div> 
	                <div field="unit" width="50" headerAlign="center" allowSort="false">单位</div>
	                <div field="norms" width="50" headerAlign="center" allowSort="false">规格型号</div>
	                <div field="createDate" width="100" headerAlign="center" allowSort="false">日期</div> 
	                <div field="luser" width="100" headerAlign="center" allowSort="false">操作员</div> 
	                <div field="lossnumber" width="50" headerAlign="center" allowSort="false">损耗量</div> 
	                <div field="remark" width="100" headerAlign="center" allowSort="false">原因</div> 
	            </div>
	        </div> 
	    </div>
</body>

</html>
    <script type="text/javascript">
        var createDateStartObj = document.getElementById("createDateStart");
    	var createDateEndObj = document.getElementById("createDateEnd");
        mini.parse();
        var grid = mini.get("datagrid");
        var store = mini.get("store");
        var user = mini.get("user");
		search();
        var grid = mini.get("datagrid");
		grid.load();
		window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}
		
		function search(){
			var key="type=0";
			var keyobj = mini.get("key");
			var createDateStart = createDateStartObj.value;
			var createDateEnd = createDateEndObj.value;
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
		
		function info(){
			var rows = grid.getSelecteds();
			if(!rows){mini.alert("请选择"); return;}
			if(rows.length>0){
				var ids = "";
				for(var i=0;i<rows.length;i++){
					ids+=rows[i].lid;
					if(i<rows.length-1)
						ids+=","; 
				}
			}
			openWindow("<%=basePath%>loss_info.do?ids="+ids+"&type=<%=Order.TYPE_BUY_ORDER%>","单据明细",550,500)
		}
		
		function add(){
			openWindow("<%=basePath%>jsp/loss/loss_add.jsp","添加损耗","80%",500,function(){
				grid.reload();
			});
		}
		
    </script>

