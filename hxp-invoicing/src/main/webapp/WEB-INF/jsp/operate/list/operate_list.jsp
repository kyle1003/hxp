<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>后台管理系统</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script src="<%=basePath%>plugins/miniui/boot.js" type="text/javascript"></script>
		<style type="text/css">
		html,body
		{
		    width:100%;
		    height:100%;
		    border:0;
		    margin:0;
		    padding:0;
		    overflow:visible;
		}
		</style>
  </head>
  
  <body>
	   <ul id="tree2"  class="mini-tree" url="<%=basePath %>operate_data_tree.do" style="width:95%;padding:5px; text-align: left;"  showTreeIcon="true" textField="text" idField="id" parentField="pid" resultAsTree="false"   onbeforenodecheck="onBeforeNodeCheck" allowSelect="false" enableHotTrack="false">
	   </ul>
  </body>
</html>
<script type="text/javascript"> 
		mini.parse();
 		var grid = mini.get("datagrid1");
        grid.load();
        
		function onBeforeNodeCheck(e) {
		    var tree = e.sender;
		    var node = e.node;
		    if (tree.hasChildren(node)) {
		        //e.cancel = true;
		    }
		}
</script>