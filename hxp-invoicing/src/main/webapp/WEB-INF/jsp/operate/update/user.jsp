<%@ page language="java"  pageEncoding="UTF-8"
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
  
  <body>
  <div id="form1">
   <div id="form2">
  <fieldset style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;">&nbsp; 
        <legend>基本信息</legend>
        <div id="editForm1" style="padding:5px;">
            <input class="mini-hidden" name="id" value="${user.id}"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:80px;" align="right">登录帐号：</td>
                    <td style="width:150px;" ><input value="${user.username}"  id="username" name="username" class="mini-textbox" required="true" /></td>
               		<td style="width:80px;" align="right">管理组：</td>
                    <td style="width:150px;"><input name="managerGroupId" value="${user.managerGroupId}" id="managerGroup" url="<%=basePath%>getManagerGroup.do" id="managerGroup" class="mini-combobox" /></td>
                </tr>
                <tr>
                    <td style="width:80px;" align="right">备注：</td>
                    <td style="width:150px;" colspan="2"><input name="remark" value="${user.remark}"  id="remark" class="mini-textbox" /></td>
               	</tr>
                <tr>
                	<td style="width:80px;" align="right">是否生效：</td>
                    <td style="width:150px;"><input name="valid" ${user.valid?"checked='checked'":""} id="valid" class="mini-checkbox" /></td>
                   	<td style="width:80px;" align="right">详细信息：</td>
                    <td style="width:150px;" colspan="2"><input name="userinfo" onclick="checkUserInfo();" id="userinfo" class="mini-checkbox"  /></td>
               </tr>
            </table>
        </div>
    </fieldset>
  </div>
  <div id="form3">
  <fieldset id="userinfoset" style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;display: none " >
        <legend>详细信息</legend>
        <div id="editForm1" style="padding:5px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:80px;" class="t1">姓名：</td>
                    <td style="width:100px;"><input id="name" name="name" value="${user.userData.name}" class="mini-textbox" required="true" /></td>
               	    <td style="width:80px;" class="t2">性别</td>
                    <td style="width:150px;"><input id="gender" name="sex" value="${user.userData.sex==1?'男':'女'}"  required="true" class="mini-combobox" data="Genders" /></td>
                </tr>
                <tr>
                	<td style="width:80px;"  class="t1">籍贯：</td>
                    <td style="width:100px;"><input id="hometown" name="hometown"  value="${user.userData.hometown}" required="true" class="mini-textbox" /></td>
                    <td style="width:150px;" class="t2">政治面貌：</td>
                    <td style="width:150px;"><input id="zzmm" name="zzmm" required="true" value="${user.userData.zzmm}" class="mini-combobox"  data="zzmm"/></td>
                </tr>
                <tr>
                	<td style="width:80px;" class="t1">学历：</td>
                    <td style="width:100px;"><input id="degree" name="degree"  value="${user.userData.degree}" required="true" class="mini-textbox" /></td>
                    <td style="width:150px;" class="t2">联系电话：</td>
                    <td style="width:150px;"><input name="tel" id="tel"  required="true" value="${user.userData.tel}" class="mini-textbox" /></td>
                </tr>
            </table>
        </div>
    </fieldset>
	  <div style="text-align: center;">
	  	<br/><br/>
	  	<a class="mini-button" onclick="submitForm()">提交</a>
	  	<input type="hidden" id="userId" value="${user.id}"/>
	  	<input type="hidden" id="hasUserData" value="${user.userData!=null}"/>
	   </div>
	    </div>
  </div>
  </body>
</html>
<script type="text/javascript">
				
			  function submitForm() {
			  		var form = new mini.Form("#form2");
			  		var hasinfo=mini.get("userinfo").checked;
			  		if(hasinfo){
			  			form = new mini.Form("#form1");
			  		}
		            form.validate();
		            if (form.isValid() == false){return};
		            var data = form.getData();
            		var json = mini.encode(data);
		          	$.ajax({
									type:"post", //请求方式
									url:"<%=basePath%>user_doUpdate.do",
									data : { //发送给后台的数据
										json:json,
										hasData:hasinfo,
										t : Math.random()
									},
									//请求成功后的回调函数有两个参数
									success : function(data, textStatus) {
									  mini.alert(data);
									},
									error : function(msg) {
									  mini.alert("服务器忙!");
									}
								});
        		}
			
			
		function checkUserInfo(){
		var dsp = document.getElementById("userinfoset").style;
		    if(dsp.display=="none"){
		       dsp.display="";
		    }else{
		    	dsp.display="none";
		    }
			
		}
    	 var Genders = [{ id: '男', text: '男' }, { id: '女', text: '女'}];
		 var zzmm = [{ id: '群众', text: '群众' }, { id: '团员', text: '团员'},{ id: '党员', text: '党员'}];
		 
        mini.parse();
    	
	</script>