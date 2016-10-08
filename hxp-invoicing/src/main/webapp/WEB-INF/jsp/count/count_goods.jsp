<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
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
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div title="center" region="center" bodyStyle="overflow:hidden;" style="border:0;">
        <!--Splitter-->
        <div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
            <div id="trees" size="180" maxSize="250" minSize="100" showCollapseButton="true" style="border-width:1px;">
                <!--Tree-->
                <ul id="leftTree" url="<%=basePath%>goodsType_list_json.do?type=<%=JSON.TYPE_TREE%>" class="mini-tree" style="width:100%;height:100%;"  showTreeIcon="true" textField="text" idField="id" resultAsTree="false"  onnodeselect="onNodeSelect">        
                </ul>
            </div>
            <div showCollapseButton="false" style="border:0px;" >
                <div class="mini-toolbar" style="padding:2px;border:0;">
			        <table style="width:100%;">
			            <tr>
			            <td style="width:100%;">
			                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
			            </td>
			            <td style="white-space:nowrap;"><label style="font-family:Verdana;">搜索: </label>
			                <input class="mini-textbox" id="key" onvaluechanged="search()" />
			                <input class="mini-combobox" value="1" id="not0" data=[{id:1,text:'所有商品'},{id:2,text:'有库存的商品'},{id:3,text:'无库存的商品'}] onvaluechanged="search()" />
			                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
			                </td>
			            </tr>
			        </table>
			    </div>
			    <!--撑满页面-->
			    <div class="mini-fit" style="height:100px;" align="center">
			        <div id="datagrid" class="mini-datagrid"  virtualScroll="true" onrowdblclick="editGoods()" showPager="false" style="width:100%;height:100%;"  url="<%= basePath %>goods_list_json.do"  idField="id" sizeList="[5,10,20,50]" pageSize="10" contextMenu="#gridMenu" headerContextMenu="#headerMenu">
			            <div property="columns">
			            	<div field="id" width="50" headerAlign="center" allowSort="false">编号</div> 
			                <div field="code" width="80" headerAlign="center" allowSort="false">条码</div> 
			                <div field="medicineName" width="100" headerAlign="center" allowSort="true">通用名</div>
			                <div field="name" width="100" headerAlign="center" allowSort="true">药名</div>
			                <div field="goodsType" width="80" headerAlign="center" allowSort="false">类别</div> 
			                <div field="unit" width="100" headerAlign="center" allowSort="false">单位</div>
			                <div field="norms" width="100" headerAlign="center" allowSort="false">规格型号</div>                              
			          	 	<div field="sellPrice" width="100"  headerAlign="center" allowSort="true">预计售价</div>
			          	 	<div field="buyPrice" width="100"  headerAlign="center" allowSort="true">预计进价</div>
			          	 	<div field="number" width="100"  headerAlign="center" allowSort="true">库存</div>
			          	 	<div field="remark" width="100"  headerAlign="center" allowSort="true">备注</div>
			            </div>
			        </div> 
			    </div> 
            </div>        
        </div>
    </div>
</div>

</body>

</html>
    <script type="text/javascript">
        mini.parse();
        var tree = mini.get("leftTree");
        var keyobj=mini.get("key");
		var goodsTypeIdTemp = -1;
		var grid = mini.get("datagrid");
		var not0Obj = mini.get("not0");
		search();
        function onNodeSelect(e) {
            var node = e.node;
            var isLeaf = e.isLeaf;
            if (node) {
            	goodsTypeIdTemp=node.id;
            	search();
            }
        }

		function search(){
			var key=keyobj.value;
			var not0 = not0Obj.value;
			var keys ="haveBuyOrder=true,key="+key;
			if(goodsTypeIdTemp!=-1){
				keys+=",goodsTypeId="+goodsTypeIdTemp;
			} 
			if(not0==2){
				keys+=",not0=true";
			}
			if(not0==3){
				keys+=",is0=true";
			}
			grid.load({key:keys});
		}
		
		window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
		}

        function reloadTree() {
           goodsTypeIdTemp=1;
           tree.setUrl("<%=basePath%>goodsType_list_json.do?type=<%=JSON.TYPE_TREE%>");
        }
        
         function collapseNode() {
            var node = tree.getSelectedNode();
            if (node) {
                tree.collapseNode(node);
            }else{
            	tree.collapseAll();
            }
        }
        
        function expandNode() {
            var node = tree.getSelectedNode();
            if (node) {
                tree.expandNode(node);
            }else{
            	 tree.expandAll();
            }
        }
        
    </script>

