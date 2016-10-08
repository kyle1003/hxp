<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
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
	                <a class="mini-button" iconCls="icon-find" plain="true" onclick="info()">单据详情</a>
	                <a class="mini-button" iconCls="icon-find" plain="true" onclick="sellinfo()">销售明细</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	                <span class="separator"></span>
					条件过滤:日期:<input id="createDateStart" style="width: 150px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00',maxDate:createDateEndObj.value,onpicked:function(dp){search();}})"  />
	           		-<input id="createDateEnd" style="width: 150px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd  HH:mm:00',minDate:createDateStartObj.value,onpicked:function(dp){search();}})"  />
	            	<br />状态:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="status" data="statusType" value="<%=Order.STATUS_THROUGHCHECK%>" valueField="id" textFiled="text" />
	            	仓库:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="store" value="-1" showNullItem="true" url="<%=basePath %>store_list_json.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	经办人:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="user" value="-1" showNullItem="true" url="<%=basePath %>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	单号:<input class="mini-textbox" id="key" onenter="search()"  />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	            </td>
	            </tr>
	        </table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid"  onrowdblclick="info()" showFooter="true" style="width:100%;height:100%;"  url="<%=basePath%>order_list_json.do"  idField="id" sizeList="[20,50,200]" pageSize="20" >
	            <div property="columns">
	            	<div field="id" width="50" headerAlign="center" allowSort="true">编号</div> 
	                <div field="createDate" width="80" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:dd" allowSort="true">开单日期</div>
	                <div field="type" width="80" headerAlign="center" allowSort="true">单据类型</div>
	                <div field="pay" width="80" headerAlign="center" allowSort="true">收款金额</div>
	                <div field="payType" width="80" headerAlign="center" allowSort="true">支付方式</div>
	                <div field="storeId" width="80" headerAlign="center" allowSort="true">仓库</div>
	                <div field="userId" width="80" headerAlign="center" allowSort="true">经办人</div>
	                <div field="status" width="80" headerAlign="center" allowSort="true">状态</div>
	            </div>
	        </div> 
	    </div>
	    
</body>

</html>
    <script type="text/javascript">
    	var type = '<%=Order.TYPE_SELL_ORDER%>'
    	var statusType=[{'id':'all','text':'-所有-'},{'id':'<%=Order.STATUS_DELETE%>','text':'已删除'},{'id':'<%=Order.STATUS_NOTCHECKED%>','text':'未审核'},{'id':'<%=Order.STATUS_THROUGHCHECK%>','text':'已生效'}];
    	var createDateStartObj = document.getElementById("createDateStart");
    	var createDateEndObj = document.getElementById("createDateEnd");
        mini.parse();
        var store = mini.get("store");
        var grid = mini.get("datagrid");
         var user = mini.get("user");
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
			if(createDateStart!=""){
				key+=",createDateStart="+createDateStart;
			}
			if(store&&store.value!=-1&&store.value!=""){
				key+=",storeId="+store.value;
			}
			if(createDateEnd!=""){
				key+=",createDateEnd="+createDateEnd;
			}
			if(user.value!=-1&&user.value!=""){
				key+=",userId="+user.value;
			}
			if(keyobj.value!=""){
				key+=",id="+keyobj.value
			}
				grid.load({key:key});
		}
	    
	    confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}
		
		function info(){
			var d = grid.getSelected();
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			openWindow("<%=basePath%>order_info.do?id="+d.id+"&type="+type,"单据明细",550,500)
		}
		
		function sellinfo(){
			var d = grid.getSelected();
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			openWindow("<%=basePath%>sell_info.do?id="+d.id+"&type="+type,"单据明细",650,600)
		}
    </script>

