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
	                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	            	条件过滤:
	            	单号:<input class="mini-textbox" id="key" onenter="search()"  />
	            	日期:<input id="createDateStart" style="width: 80px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:createDateEndObj.value,onpicked:function(dp){search()}})"  />
	           		-<input id="createDateEnd" style="width: 80px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:createDateStartObj.value,onpicked:function(dp){search()}})"  />
	            	仓库:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="store" value="-1" showNullItem="true" url="<%=basePath %>store_list_json.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	经办人:<input class="mini-combobox" onvaluechanged="search()" style="width: 80px" id="user" value="-1" showNullItem="true" url="<%=basePath %>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX %>" valueField="id" textFiled="text" />
	            	<a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	                 <span class="separator"></span>
	                 <a class="mini-button" iconCls="icon-save" plain="true" onclick="startGiveBack()">退货</a>
	            </td>
	        </table>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid"  onrowdblclick="startGiveBack()" allowCellEdit="true" allowCellSelect="true" multiSelect="true"  editNextOnEnterKey="true" showFooter="true" style="width:100%;height:100%;"  url="<%=basePath%>sell_orderGoods_list_json.do"  idField="oid" sizeList="[5,10,20,50]" pageSize="20" >
	            <div property="columns">
	            	<div field="orderid" width="130" headerAlign="center" allowSort="false">销售单编号</div> 
	                <div field="code" width="80" headerAlign="center" allowSort="false">条码</div> 
	                <div field="name" width="100" headerAlign="center" allowSort="false">名称</div>
	                <div field="unit" width="50" headerAlign="center" allowSort="false">单位</div>
	                <div field="norms" width="50" headerAlign="center" allowSort="false">规格型号</div>
	                <div field="price" width="50" headerAlign="center" allowSort="false">单价</div>
	                <div field="odNumber" width="50" headerAlign="center" allowSort="false">数量</div>
	                <div field="countNumber" width="50" headerAlign="center" allowSort="false">库存量</div>
	            </div>
	        </div> 
	    </div>
	    
	    <div id="win2" class="mini-window" title="退货" showCloseButton="false" style="width:300px;height:250px;"  showMaxButton="false" showToolbar="false" showFooter="true" showModal="true" allowResize="false" allowDrag="true" >
		    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		         <a class="mini-button" id="submitButton" onclick="win.hide();">关闭(esc)</a> <a class="mini-button" id="submitButton" onclick="submitGiveBack()">提交</a>
		    </div>
		    <div style="margin: auto;text-align: center;" id="form1" >
		     	<table>
		     		<tr>
		     			<td colspan="2">
		     				单号:<input style="width: 200px" class="mini-textbox" Enabled='false' name="orderid" id="orderid"/>
		     			</td>
		     		</tr>
		     		<tr>
		     			<td>名称:<input class="mini-textbox" Enabled='false' name="name" id="name"/></td>
		     			<td>数量:<input class="mini-spinner" value="0" minValue="0.0" maxValue="0.0" id="number" name="number"  decimalPlaces="1"/></td>
		     			<input class="mini-hidden" name="sellOrderGoodsId" id="sellOrderGoodsId"/>
		     		</tr>
		     		<tr>
		     			<td colspan="2">
		     				原因:<input style="width: 200px;height: 50px" class="mini-textarea" name="remark" id="remark"/>
		     			</td>
		     		</tr>
		     	</table>
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
        var win = mini.get("win2");
      //绑定表单
        var db = new mini.DataBinding();
        db.bindForm("form1", grid);
		search();
		window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}

		function startGiveBack(){
			var data = grid.getSelected();
			if(data.odNumber<=0){
				mini.alert("销售数量为0，无法退货!");
				return;
			}
			if(!data){
				mini.alert("请选择");
				return;
			}
			mini.get("number").setMaxValue(data.odNumber);
			mini.get("number").setValue(data.odNumber);
			mini.get("sellOrderGoodsId").setValue(data.oid);;
			win.show();
		}

		function submitGiveBack(){
			var form = new mini.Form("#form1");
            form.validate();
            if (form.isValid() == false){return};
            var data = form.getData();
            if(data.number<=0){
				mini.alert("销售数量为0，无法退货!");
				return;
			}
    		var json = mini.encode(data);
    		confirm("确认退货，退货后将恢复库存？","确认?",function(e){
				if(e!="ok")return;
				grid.loading("保存中，请稍后......");
	            var json = mini.encode(data);
	             doAjax("<%=basePath%>giveBack_add.do",{json:json,t:new Date().getTime()},"POST",function(text,status){
	             	mini.alert(text);
	             	grid.reload();
	             	win.hide();
	             	form.reset();
	             });
			});
		}
		
		function search(){
			var key="type=<%=Order.TYPE_SELL_ORDER%>";
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
				key+=",orderId="+keyobj.value
			}
				grid.load({key:key});
		}
		
		function saveData(){
			var data = grid.getChanges();
			if(data.length==""||data.length<1){
				return;
			}
			confirm("提交后对应库存将减少,且无法恢复。确认提交损耗？","确认?",function(e){
				if(e!="ok")return;
				 grid.loading("保存中，请稍后......");
	            var json = mini.encode(data);
	             doAjax("<%=basePath%>loss_add.do",{json:json,t:new Date().getTime()},"POST",function(text,status){
	             	mini.alert(text);
	             	grid.reload();
	             });
			});
			
		}
		
    </script>

