<%@ page language="java"  pageEncoding="UTF-8"
%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute("user");
if(user==null){
	request.getRequestDispatcher("/login.jsp").forward(request,response);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>后台管理系统</title>
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">    
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<script src="<%=basePath%>plugins/miniui/boot.js" type="text/javascript"></script>
	<style type="text/css">
	 html,body
    {
        width:100%;
        height:100%;
        border:0;
        margin:0;
        padding:0;
        overflow:auto;
    }
	</style>
  </head>
  
  <body>
	<div class="mini-splitter" style="width:100%;height:80%;" borderStyle="border:0;">
	<div id="trees" size="120" maxSize="180" minSize="100" showCollapseButton="true" style="border-width:1px;">
	      <!--Tree-->
	      <ul id="leftTree" url="<%=basePath%>goodsType_list_json.do?type=<%=JSON.TYPE_TREE%>" class="mini-tree" style="width:100%;height:100%;"  showTreeIcon="true" textField="text" idField="id" resultAsTree="false"  onnodeselect="onNodeSelect">        
	      </ul>
	 </div>
	 <div showCollapseButton="false" style="border:0px;" >
	  <div class="mini-toolbar" style="padding:2px;border:0;">
	        <table style="width:100%;">
	            <tr>
	            <td style="white-space:nowrap;">
	                <input class="mini-textbox" onenter="search()" value="${param.code}" id="key" />
	                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	                </td>
	            </tr>
	        </table>
	    </div>
		<div id="datagrid1" onenter="onOk()"  virtualScroll="true" onrowdblclick="onOk()" showPager="false"  showFooter="false" multiSelect="true" idField="id" allowCellSelect="true" class="mini-datagrid" style="width:100%;height:88%;"  url="<%= basePath %>goods_list_json.do"   idField="id" >
            <div property="columns">
				<div field="id" width="50" headerAlign="center" allowSort="false">编号</div> 
                <div field="code" width="80" headerAlign="center" allowSort="false">条码</div> 
                <div field="medicineName" width="100" headerAlign="center" allowSort="true">通用名</div>
			    <div field="name" width="100" headerAlign="center" allowSort="true">药名</div>
                <div field="goodsType" width="80" headerAlign="center" allowSort="false">类别</div> 
                <div field="unit" width="100" headerAlign="center" allowSort="false">单位</div>
                <div field="dose" width="100" headerAlign="center" allowSort="true">剂量</div>
                <div field="norms" width="100" headerAlign="center" allowSort="false">规格型号</div>                              
          	 	<div field="sellPrice" width="100"  headerAlign="center" allowSort="true">预计销售价</div>
          	 	<div field="buyPrice" width="100"  headerAlign="center" allowSort="true">预计采购价</div>
          	 	<div field="number" width="100"  headerAlign="center" allowSort="true">库存</div>
          	 	<div field="remark" width="100"  headerAlign="center" allowSort="false">备注</div>
            </div>
        </div>
        </div>
        </div> 
		<div style="width: 100%;text-align: center;">
		<br/>
             <a class="mini-button" style="width: 100px;height: 30px" onclick="onOk()">选择</a>
			&nbsp;&nbsp; <a class="mini-button" style="width: 100px;height: 30px" onclick="onCancel()">取消</a>
			<input type="hidden" id="setOfBooksTypeId" value="<%=request.getParameter("setOfBooksTypeId")%>"/>
		</div>
   </body>
</html>
<script type="text/javascript">
 		mini.parse();
  		var grid = mini.get("datagrid1");
  		var keyobj=mini.get("key");
  		search();
        var goodsTypeIdTemp = -1;
        var tree = mini.get("leftTree");
        function onNodeSelect(e) {
            var node = e.node;
            var isLeaf = e.isLeaf;
            if (node) {
            	goodsTypeIdTemp=node.id;
                 search();
            }
        }
        mini.get("key").focus();
       	function onKeyEnter(e) {
        	search();
	    }
	    function onRowDblClick(e) {
	        onOk();
	    }
        function search(){
			var key=keyobj.value;
			var keys = "haveBuyOrder=<%=request.getParameter("haveBuyOrder")%>,key="+key;
			if(goodsTypeIdTemp!=-1){
				keys+=",goodsTypeId="+goodsTypeIdTemp;
			} 
			grid.load({key:keys});
		}
         function GetData() {
			var row = grid.getSelected();
       	 	return row;
   		 }
       
    	function getSelecteds() {
			var rows = grid.getSelecteds();
			var s = "";
			for ( var i = 0, l = rows.length; i < l; i++) {
				var row = rows[i];
				s += row.id;
				if (i != l - 1)
					s += ",";
			}
			return s;
		}
		
		//关闭窗体
    	 function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	
	    function onOk() {
	        CloseWindow("ok");
	    }
	    function onCancel() {
	        CloseWindow("cancel");
	    }
    	function changeJob(){
				var departmentId=mini.get("departmentId").value;
				if(departmentId!=0){
					var url="<%=basePath%>ht/job_manage_select.do?id="+departmentId;
						mini.get("jobId").setUrl(url);
						mini.get("jobId").setValue(0);
					}
				serch();
			}

    	$(function () {
            $("#datagrid1").bind("keydown", "ctrl", function (ev) {
                if(ev.keyCode==13){
                    onOk();
            		return true; 
                }
                if(ev.keyCode==116){
                   grid.reload();
             	   return false;
          		}
            });
            $(document).bind("keydown", "ctrl", function (ev) {
                if(ev.keyCode==27){
                    onOk();
            		return true; 
                }
            });
            
        });
	</script>