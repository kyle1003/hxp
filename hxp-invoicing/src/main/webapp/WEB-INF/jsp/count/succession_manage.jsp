<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
<%@page import="cn.hurry.po.succession.SuccessionInfo"%>
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
	                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	                <span class="separator"></span>
					条件过滤:日期:<input id="createDateStart" style="width: 100px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:createDateEndObj.value,onpicked:function(dp){search();}})"  />
	           		-<input id="createDateEnd" style="width: 100px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:createDateStartObj.value,onpicked:function(dp){search();}})"  />
	            	状态:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="status" data="statusType" value="all" valueField="id" textFiled="text" />
	            	收银员:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="user" value="-1" showNullItem="true" url="<%=basePath %>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	单号:<input class="mini-textbox" id="key" style="width: 150px" onenter="search()"  />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	            </td>
	            </tr>
	        </table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid" onrowdblclick="successionInfoInfo" onshowrowdetail="onShowRowDetail" showFooter="true" style="width:100%;height:100%;"  url="<%=basePath%>successionInfo_list_json.do"  idField="id" sizeList="[20,50,200]" pageSize="50" >
	            <div property="columns">
	            	<div type="expandcolumn" headerAlign="center">明细</div>
	            	<div field="id" width="50" headerAlign="center" allowSort="true">编号</div> 
	                <div field="date" width="80" headerAlign="center" allowSort="true">日期</div>
	                <div field="status_cn" width="80" headerAlign="center" allowSort="false">状态</div>
	                
	            </div>
	        </div> 
	    </div>
	    
	     <div class="mini-fit" id="detailGrid_Form" style="display:none;" align="center">
	        <div id="datagrid2" class="mini-datagrid" onrowdblclick="successionInfo" showFooter="true" style="margin: auto;width:80%;height:150px;"  url="<%=basePath%>succession_list_json.do"  idField="id" sizeList="[20,50,200]" pageSize="20" >
	            <div property="columns">
	            	<div field="id" width="50" headerAlign="center" allowSort="true">编号</div> 
	                <div field="username" width="80" headerAlign="center" allowSort="false">收银员</div>
	                <div field="sellOrderIdsLength" width="80" headerAlign="center" allowSort="false">开单数量</div>
	                <div field="takeOverTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="80" headerAlign="center" allowSort="true">开班日期</div>
	                <div field="handOverTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="80" headerAlign="center" allowSort="true">结班日期</div>
	                <div field="status_cn" width="50" headerAlign="center" allowSort="false">状态</div>
	                <div field="remark" width="80" headerAlign="center" allowSort="false">备注</div>
	            </div>
	        </div> 
	    </div>
</body>

</html>
    <script type="text/javascript">
    	var type = '<%=Order.TYPE_SELL_ORDER%>'
    	var statusType=[{'id':'all','text':'-所有-'},{'id':'<%=SuccessionInfo.status_isSettle%>','text':'已日结'},{'id':'<%=SuccessionInfo.status_notStart%>','text':'未开班'},{'id':'<%=SuccessionInfo.status_settleing%>','text':'轮班中'},{'id':'<%=SuccessionInfo.status_working%>','text':'收银中'}];
    	var createDateStartObj = document.getElementById("createDateStart");
    	var createDateEndObj = document.getElementById("createDateEnd");
        mini.parse();
        var store = mini.get("store");
        var grid = mini.get("datagrid");
        var grid2 = mini.get("datagrid2");
        var detailGrid_Form = document.getElementById("detailGrid_Form");
        var user = mini.get("user");
        search();
        window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}

        function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            var key="type="+type;
			var keyobj = mini.get("key");
			var status = mini.get("status");
			if(user.value!=-1&&user.value!=""){
				key+=",takeOverUserId="+user.value;
			}
			if(keyobj.value!=""){
				key+=",sellOrderIds="+keyobj.value
			}
            grid2.load({ key:key+",successionInfoId="+ row.id });
        }
    
		function successionInfo(e){
			 var grid = e.sender;
	         var row = e.record;
	         var printUrl = "<%=basePath%>sell_order_info_succession.do?type=all&showAll=true&id="+row.id;
	         openWindow(printUrl,"",500,600);
			// window.open(printUrl,"newwindow2","height=600,width=800,top=0,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
		}
		function successionInfoInfo(e){
			 var grid = e.sender;
	         var row = e.record;
	         var printUrl = "<%=basePath%>sell_order_info_workOver.do?type=all&showAll=true&scId="+row.id;
	         openWindow(printUrl,"",500,600);
			// window.open(printUrl,"newwindow1","height=600,width=800,top=0,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
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
			if(createDateEnd!=""){
				key+=",createDateEnd="+createDateEnd;
			}
			if(user.value!=-1&&user.value!=""){
				key+=",userId="+user.value;
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
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			openWindow("<%=basePath%>order_info.do?id="+d.id+"&type="+type,"单据明细",550,500)
		}
		
		function sellinfo(){
			var d = grid.getSelected();
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			openWindow("<%=basePath%>sell_info.do?id="+d.id+"&type="+type,"单据明细",650,600)
		}
    </script>

