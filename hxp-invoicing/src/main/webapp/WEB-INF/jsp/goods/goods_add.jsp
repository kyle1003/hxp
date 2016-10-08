<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.manage.goods.GoodsManage"%>
<%@page import="cn.hurry.util.BaseString"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
User user = (User)request.getSession().getAttribute("user");
if(user==null){
	request.getRequestDispatcher("/login.jsp").forward(request,response);
}
String typeId = request.getParameter("typeId");
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
  
  <body>
   <div id="form">
  <fieldset style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;">
        <legend>商品添加</legend>
        <div id="editForm1" style="padding:5px;">
            <input class="mini-hidden" name="id"/>
            <table style="width:100%;">
             	<tr>
	              <td>条&nbsp;&nbsp;&nbsp;码:<input id="code" name="code"  style="width: 200px" class="mini-textbox"  /></td>
				  <td>监管码:<input id="jgCode" name="jgCode"  style="width: 200px" class="mini-textbox"  /></td>
                </tr>
                <tr>
	               <td>厂&nbsp;&nbsp;&nbsp;家:<input style="width: 200px;" id="manufacturers" name="manufacturers" class="mini-combobox" data="manufacturersData" allowInput="true" textField="text" valueField="text" showNullItem="true" required="true" pinyinField="pinyin" /></td>
	               <td>类&nbsp;&nbsp;&nbsp;别:<input id="goodsTypeId" name="goodsTypeId"  class="mini-treeselect" value="<%=typeId.equals("-1")?"":typeId%>" url="<%=basePath%>goodsType_list_json.do?type=<%=JSON.TYPE_TREE %>" multiSelect="false"  valueFromSelect="false" textField="text" valueField="id" parentField="pid" value="-请选择-"  /></td>
                </tr>
                <tr>
                	<td>通用名:<input style="width: 150px"  id="medicineName" name="medicineName" class="mini-textbox"  /></td>
	                <td>药&nbsp;&nbsp;&nbsp;名:<input id="name" name="name"  style="width: 200px"  class="mini-textbox"  required="true" /></td>
                </tr>
                <tr>
	                <td>规&nbsp;&nbsp;&nbsp;格:<input class="mini-combobox" url="<%=basePath%>norms_list_json.do?type=<%=JSON.TYPE_COMBOBOX%>" allowInput="true" textField="text" valueField="id" showNullItem="true" id="normsId" name="normsId" required="true"/><a class="mini-button" plain="true" iconcls="icon-add" onclick="addnorms"></a></td>
	                <td>单&nbsp;&nbsp;&nbsp;位:<input class="mini-combobox" url="<%=basePath%>unit_list_json.do?type=<%=JSON.TYPE_COMBOBOX%>" allowInput="true" textField="text" valueField="id" showNullItem="true" id="unitId" name="unitId"  required="true"/><a class="mini-button" plain="true" iconcls="icon-add" onclick="addunit"></td>
                </tr>
                <tr>
	               <td>剂&nbsp;&nbsp;&nbsp;型:<input class="mini-combobox" data="doseTypeData" allowInput="true" textField="text" valueField="text" showNullItem="true" id="doseType" name="doseType" required="true"/> </td>
	               <td>剂&nbsp;&nbsp;&nbsp;量:<input class="mini-combobox" data="doseData" allowInput="true" textField="text" valueField="text" showNullItem="true" id="dose" name="dose" required="true"/></td>
                </tr>
                <tr>
	                <td>预设进价:<input class="mini-textbox" vtype="float" id="buyPrice" name="buyPrice"  required="true" /></td>
	                <td>预设售价:<input class="mini-textbox" vtype="float" id="sellPrice" name="sellPrice"  required="true"/></td>
                </tr>
                <tr>
	                <td colspan="2">备&nbsp;&nbsp;&nbsp;注:<input class="mini-textarea" id="remark" name="remark" /></td>
                </tr>
            </table>
        </div>
    </fieldset>
  </div>
	 <div style="text-align: center;">
	  	<br/><br/>
	  	<a class="mini-button" onclick="submitForm()">提交</a>
	  </div>
  </body>
</html>
<script type="text/javascript">
	var manufacturersData = <%=GoodsManage.getManufacturersJson()%>;
	var doseData = <%=GoodsManage.getDoseJson() %>;
	var doseTypeData = <%=GoodsManage.getDoseTypeJson()%>;
	mini.parse();
	function submitForm() {
		var form = new mini.Form("#form");
		var nromsObj = mini.get("normsId");
		var unitObj = mini.get("unitId");
		if(nromsObj.value==nromsObj.text){
			mini.alert("请选择规格"); 
			return;
		}
		if(unitObj.value==unitObj.text){
			mini.alert("请选择单位"); 
			return;
		}
		form.validate();
		if (form.isValid() == false){return};
		var data = form.getData();
		var json = mini.encode(data);
		doAjax("<%=basePath%>goods_add.do",{json:json,t:new Date().getTime()},"POST",function(r,s){
			alert(r);
			if(r=="<%=BaseString.INFO_ADD_SUCCESS%>"){
				window.location.reload();
			}
		});
	}
	window.confirm=function (text,title,callback){
		mini.confirm(text, title,callback);
	}
	
	function addnorms(){
		var nromsObj = mini.get("normsId");
		var text = nromsObj.text;
		var value=nromsObj.value;
		if(value>0&&text!=value){
			return;
		}if(value==""){
			return;
		}
		confirm("确定新增规格:"+text+" ?","确认?",function(e){
			if(e=="ok"){
				text=text.replace(",","，");
				text=text.replace("\"","''");
				 var json="{\"name\":\""+text+"\"}";
				 doAjax("<%=basePath%>norms_add.do",{get:'id',json:json,t:new Date().getTime()},"POST",function(r,s){
				 	nromsObj.setUrl("<%=basePath%>norms_list_json.do?type=<%=JSON.TYPE_COMBOBOX%>");
				 	nromsObj.setValue(r);
				 });
			}
		});
	}
			
    function addunit(){
		var unitObj = mini.get("unitId");
		var text = unitObj.text;
		var value=unitObj.value;
		if(value>0&&text!=value){
			return;
		}if(value==""){
			return;
		}
		confirm("确定新增单位:"+text+" ?","确认?",function(e){
			if(e=="ok"){
				text=text.replace(",","，");
				text=text.replace("\"","''");
				var json="{\"name\":\""+text+"\"}";
				 doAjax("<%=basePath%>unit_add.do",{get:'id',json:json,t:new Date().getTime()},"POST",function(r,s){
				 	unitObj.setUrl("<%=basePath%>unit_list_json.do?type=<%=JSON.TYPE_COMBOBOX%>");
				 	unitObj.setValue(r);
				 });
			}
		});
	}
    	
	</script>