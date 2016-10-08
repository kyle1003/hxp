<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
%>
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
  </head>
  
  <body onload="setSelecteds">
   <div id="form2">
  <fieldset style="width:480px;border:solid 1px #aaa;margin-top:8px;position:relative;">&nbsp; 
        <legend>管理组信息</legend>
        <div id="editForm1" style="padding:5px;">
            <input type="hidden" id="id" name="id" value="${managerGroup.id}"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:80px;" align="right">名称：</td>
                    <td style="width:150px;" ><input value="${managerGroup.name}"  id="name" name="name" class="mini-textbox" required="true" /></td>
                 	<td style="width:80px;" align="right">备注：</td>
                    <td style="width:150px;" colspan="2"><input name="discription" value="${managerGroup.discription}"  id="discription" class="mini-textbox" /></td>
                </tr>
            </table>
        </div>
    </fieldset>
  </div>
  <br/>
  
   <ul id="tree2" class="mini-tree" url="<%=basePath %>operate_data_tree.do" style="width:95%px;padding:5px;" 
        showTreeIcon="true" textField="text" idField="id" parentField="pid" resultAsTree="false"  
        showCheckBox="true" checkRecursive="true" 
        onbeforenodecheck="onBeforeNodeCheck" allowSelect="false" enableHotTrack="false"
        >
    </ul>
    <br/>
    <div align="center">
    	<a class="mini-button" onclick="doUpdate()">提交</a>
    </div>
  </body>
  
</html>


<script type="text/javascript">
  		mini.parse();
        var operateids = ${operateids}+"";
        var op = operateids.split(",");
        var tree = mini.get("tree2");
        tree.setValue(op);
		function doUpdate(){
			var operates = tree.getValue();
			var name=mini.get("name").getValue();
			var discription=mini.get("discription").getValue();
			var id=document.getElementById("id").value;
			 $.ajax({
					type:"post", //请求方式
					url:"<%=basePath%>managerGroup_doUpdate.do",
					data : { //发送给后台的数据
						id : id,
						discription:discription,
						operateIds:operates,
						name:name,
						t : Math.random()
					},
					//请求成功后的回调函数有两个参数
					success : function(data, textStatus) {
						mini.alert(data);
					},
					error : function(msg) {
					 mini.alert("删除失败,服务器忙!");
					}
				});
			
		}
		
		 function setCheckedNodes() {
            var tree = mini.get("tree2");
            //tree.setValue("ajax,json,date,control,forms");
            tree.setValue("forms");
        }
        function getCheckedNodes() {
            var tree = mini.get("tree2");
            var value = tree.getValue();
            alert(value);
        }
        function checkAll() {
            var tree = mini.get("tree2");
            var nodes = tree.getAllChildNodes(tree.getRootNode());
            tree.checkNodes(nodes);
        }
        function uncheckAll() {
            var tree = mini.get("tree2");
            var nodes = tree.getAllChildNodes(tree.getRootNode());
            tree.uncheckNodes(nodes);
        }
         function onBeforeNodeCheck(e) {
            var tree = e.sender;
            var node = e.node;
            if (tree.hasChildren(node)) {
                //e.cancel = true;
            }
        }
		
</script>