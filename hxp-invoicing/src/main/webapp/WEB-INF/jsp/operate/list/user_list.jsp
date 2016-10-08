<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
  <div class="mini-toolbar" style="padding:2px;border:0;">
        <table style="width:100%;">
            <tr>
            <td style="width:100%;">
                <a class="mini-button" iconCls="icon-add" plain="true" onclick="openWindow('jsp/operate/add/adduser.jsp','添加用户',530,400);">添加</a>
                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                <span class="separator"></span>
                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
            </td>
            <td style="white-space:nowrap;"><label style="font-family:Verdana;">名称: </label>
                <input class="mini-textbox" id="key" />
                <a class="mini-button" iconCls="icon-search" plain="true" onclick="serch()">查询</a>
                </td>
            </tr>
        </table>
    </div>
    <!--撑满页面-->
    <div class="mini-fit" style="height:100px;" align="center">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"  url="<%= basePath %>user_doSelect.do"  idField="id" sizeList="[5,10,20,50]" pageSize="10" contextMenu="#gridMenu" headerContextMenu="#headerMenu">
            <div property="columns">
                <div type="indexcolumn" ></div>
                <div field="username" width="80" headerAlign="center" allowSort="true">用户帐号</div> 
                <!--   
                <div field="user_data#name" width="50" headerAlign="center" allowSort="true">姓名</div>
                <div field="user_data#sex" width="50" headerAlign="center" allowSort="true" >性别</div>                            
                <div field="user_data#hometown" width="50" headerAlign="center" allowSort="true">籍贯</div>
                <div field="user_data#zzmm" width="50" headerAlign="center"  allowSort="true">政治面貌</div>
                <div field="user_data#degree" width="50" headerAlign="center" allowSort="true">学籍</div>
                -->  
                <div field="remark" width="100" headerAlign="center" allowSort="true">备注</div>                                    
                <div field="createDatetime" width="50" headerAlign="center" dateFormat="yyyy-MM-dd" allowSort="true">创建日期</div>                
          	 <div field="isValid" width="100"  headerAlign="center" allowSort="true">是否有效</div>
            </div>
        </div> 
    </div>
    
    <ul id="gridMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">              
	    <li name="edit" iconCls="icon-edit" onclick="edit">编辑资料</li>
	    <li name="edit" iconCls="icon-edit" onclick="goUpdatePassowrd">修改密码</li>
	    <li name="remove" iconCls="icon-remove" onclick="remove">删除</li>        
    </ul>	
  </body>
</html>
<script type="text/javascript">
 		var Genders = [{ id: 1, text: '男' }, { id: 2, text: '女'}];
		var db = new mini.DataBinding();
        db.bindForm("editForm1", grid);
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
 		function onGenderRenderer(e) {
            for (var i = 0, l = Genders.length; i < l; i++) {
                var g = Genders[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
       
        function goUpdatePassowrd(){
           var row = grid.getSelected();
           openWindow("jsp/operate/update/password.jsp?id="+row.id,"修改密码",350,240);
        }
        
		 function openWindow(url,title,width,height){
		         mini.open({
		                url: "<%=basePath%>"+url,
		                showMaxButton: false,
		                title: title,
		                width: width,
		                height: height,
		                ondestroy: function (action) { 
		                      grid.reload();
		                }
		            }); 
		      }
        function edit() {
            var row = grid.getSelected();
            if (row) {
            	openWindow("user_toUpdate.do?id="+row.id,"编辑用户",530,400);
            } else {
                mini.alert("请选中一条记录");
            }
        }
        
        function remove(){
        	var row = grid.getSelected();
            if (row) {
            mini.confirm("确定删除此用户？", "确定？",
			            function (action) {
			                if (action == "ok") {
                			var id = row.id;
                			grid.loading("删除中...");
						        $.ajax({
									type:"get", //请求方式
									url:"<%=basePath%>user_doDelete.do",
									data : { //发送给后台的数据
										id : id,
										t : Math.random()
									},
									//请求成功后的回调函数有两个参数
									success : function(data, textStatus) {
										mini.alert(data);
										grid.reload();
									},
									error : function(msg) {
									 mini.alert("删除失败,服务器忙!");
									}
								});
			                	 
			                } 
			            }
	       		 );
            } else {
                mini.alert("请选中一条记录");
            }
           
        }
        
        function serch(){
        	var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
      function onBeforeOpen(e) {
		    var grid = mini.get("datagrid1");
		    var menu = e.sender;
		            
		    var row = grid.getSelected();
		    var rowIndex = grid.indexOf(row);            
		    if (!row||rowIndex== -1) {
		        e.cancel = true;
		        //阻止浏览器默认右键菜单
		        e.htmlEvent.preventDefault();
		        return;
   	 }
}
    </script>