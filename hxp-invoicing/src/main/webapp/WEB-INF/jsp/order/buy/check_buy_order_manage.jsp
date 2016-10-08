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
	                <a class="mini-button" iconCls="icon-add" plain="true" onclick="check()">通过审核</a>
	                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
	                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeOrder()">删除</a>
	                <a class="mini-button" iconCls="icon-find" plain="true" onclick="info()">详情</a>
	                <span class="separator"></span>
	                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	            </td>
	            <td style="white-space:nowrap;"><label style="font-family:Verdana;">搜索: </label>
	                <input class="mini-textbox" id="key" onenter="search()"  />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	                </td>
	            </tr>
	        </table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid" showFooter="false" style="width:100%;height:100%;"  url="<%=basePath%>order_list_json.do"  idField="id" sizeList="[5,10,20,50]" pageSize="10" contextMenu="#gridMenu" headerContextMenu="#headerMenu" >
	            <div property="columns">
	            	<div field="id" width="50" headerAlign="center" allowSort="true">编号</div> 
	                <div field="createDate" width="80" headerAlign="center" dateformat="yyyy-MM-dd HH:mm:dd" allowSort="true">开单日期</div>
	                <div field="type" width="80" headerAlign="center" allowSort="false">单据类型</div>
	                <div field="pay" width="80" headerAlign="center" allowSort="true">付款金额</div>
	                <div field="userId" width="80" headerAlign="center" allowSort="true">经办人</div>
	                <div field="status" width="80" headerAlign="center" allowSort="true">状态</div>
	            </div>
	        </div> 
	    </div>
	    <ul id="gridMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
			<li name="edit" iconCls="icon-edit" onclick="edit()">
				编辑
			</li>
			<li name="edit" iconCls="icon-find" onclick="check()">
				通过审核
			</li>
			<li name="edit" iconCls="icon-remove" onclick="removeOrder()">
				删除
			</li>
			<li name="edit" iconCls="icon-reload" onclick="grid.reload();">
				刷新
			</li>
		</ul>
</body>

</html>
    <script type="text/javascript">
    	var type = '<%=Order.TYPE_BUY_ORDER%>'
        mini.parse();
        var grid = mini.get("datagrid");
		grid.load({key:"type=<%=Order.TYPE_BUY_ORDER%>,status=<%=Order.STATUS_NOTCHECKED%>"});
		window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}
		
		function onBeforeOpen(e) {
		    var menu = e.sender;
		    var row = grid.getSelected();
		    var rowIndex = grid.indexOf(row);            
		    //if (!row||rowIndex== -1) {
		    //    e.cancel = true;
		        //阻止浏览器默认右键菜单
		     //   e.htmlEvent.preventDefault();
		     //  return;
	   		// }
		}
		
		function search(){
			var keyobj = mini.get("key");
			if(keyobj.value!=""){
				grid.load({key:"type=<%=Order.TYPE_BUY_ORDER%>,status=<%=Order.STATUS_NOTCHECKED%>,id="+keyobj.value});
			}
		}
	    
	    confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}
	    
	    function removeOrder(){
	    	var d = grid.getSelected();
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			 mini.prompt("<lable style='color:red;'>请输入删除原因!</lable>", "请输入",
		            function (action, value) {
		                if(action!="ok"){
							return;
						}
						doAjax("<%=basePath%>order_check.do",{id:d.id,remark:value,type:type,status:'<%=Order.STATUS_DELETE%>',t:new Date().getTime()},"POST",function(data, textStatus) {
							 mini.alert(data);
							 grid.reload();
						});
		            },true);
	    }
	    
		function check(){
			var d = grid.getSelected();
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			confirm("<lable style='color:red;'>审核后将不能再进行修改!</lable>确认?","确认",function(e){
				if(e!="ok"){
					return;
				}
				doAjax("<%=basePath%>order_check.do",{id:d.id,type:type,status:'<%=Order.STATUS_THROUGHCHECK%>',t:new Date().getTime()},"POST",function(data, textStatus) {
					 mini.alert(data);
					 grid.reload();
				});
			});
		}
		
		function info(){
			var d = grid.getSelected();
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			openWindow("<%=basePath%>order_info.do?id="+d.id+"&type="+type,"单据明细",800,500)
		}
		
		function edit(){
			var d = grid.getSelected();
			if(!d||d.id=="合计"){mini.alert("请选择"); return;}
			openWindow("<%=basePath%>order_info.do?edit=edit&id="+d.id+"&type="+type,"单据编辑",550,500)
		}
    </script>

