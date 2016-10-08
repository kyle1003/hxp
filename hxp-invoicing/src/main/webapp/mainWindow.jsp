<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    	<title>管理中心 V1.0</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%
			if(session.getAttribute("user")==null){
	  			request.getRequestDispatcher("login.jsp").forward(request,response);
	  		}
	  		String str = new Date().getTime()+"";
		 %>
		 
		  <script src="<%=basePath%>plugins/miniui/boot.js" type="text/javascript"></script>
		 <script type="text/javascript">
		  	initWindow();
	       	//解决页面显示不正常 方法1
			 function initWindow(){
				 	 setTimeout(function(){reloadTabs()},100);
				 	 return;
				 	 var os = getOs();
				 	if(os!="MSIE"&&os!="Safari"){
				 	 reloadTabs();
				 	 return;
				 	}
			        if(owinWidth()){
			        	 tabs.setStyle("width:"+owinWidth()+"px;height:"+owinHeight()+"px;");
			        	 tabs.setWidth("100%");
			        	 tabs.setHeight("100%");
			        }else{
			        	reloadTabs();
			        }
	        }
	        //解决页面显示不正常  方法2（重载页面） 
	        function reloadTabs(){ 
	        	if(!<%=request.getParameter("first")%>){
	        		 window.location= window.location+"&first=true";
	        	}
	        }
		 </script> 
		 <style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;
		        
		    }    
		    .header
		    {
		        background:url(<%=basePath%>css/header.gif) repeat-x 0 -1px;
		    }
	    </style>
</head>
<body onload="mini.layout()"  onselectstart="return false" oncopy="return false"  oncut="return false"  onpaste="return false" >
	<!--tab页-->
	<div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;"  plain="false">
	  	<div title="首页" >
		  	<c:forEach var="opera" items="${operaMenuDataList}">
		  		 <a style="margin-left: 8%;margin-top: 2%"  plain="true" class="mini-button" onclick="showTab(${opera.index},'<%=basePath%>${opera.url}','${opera.name}')" ><img style="border: 0px" src="<%=basePath%>css/${opera.iconImg}" alt="${opera.name}" title="${opera.title}" width="128" height="128"/><br /><font size="4" style="font-family:Microsoft YaHei;font-weight: 800;color: #5E5E5E;">${opera.name}</font></a>
			</c:forEach>
		</div>
	</div>
</body>
</html>
 <script type="text/javascript">
        var times=0;
        mini.parse();
        var tabTmp = null;
        var tabs = mini.get("mainTabs");
       
        
        function showTab(id,url,title) {
            id="tab$"+id;
            var tab = tabs.getTab(id);
            if (!tab) {
                tab = {};
                tab.name=id;
                tab.showCloseButton = true;
                tab.url = url;
				tab.title=title;
                tabs.addTab(tab);
            }
            tabs.activeTab(tab);
        }
		
         function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
		 } 
		 
		 //获取当前浏览器
		 function getOs() {  
			    var OsObject = "";  
			   if(navigator.userAgent.indexOf("MSIE")>0) {  
			        return "MSIE";  
			   }  
			   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
			        return "Firefox";  
			   }  
			   if(isSafari=navigator.userAgent.indexOf("Safari")>0) {  
			        return "Safari";  
			   }   
			   if(isCamino=navigator.userAgent.indexOf("Camino")>0){  
			        return "Camino";  
			   }  
			   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){  
			        return "Gecko";  
			   }  
			    
			}  
    </script>
