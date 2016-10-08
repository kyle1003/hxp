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
    	<title>进销存管理</title>
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
		 <link href="<%=basePath%>plugins/miniui/desktop/js/desktop.css" rel="stylesheet" type="text/css" />
		 <style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }    
		    .header
		    {
		        background:url(<%=basePath%>css/icon/header.gif) repeat-x 0 -1px;
		    }
		    #msg_win{position:absolute;right:0px;display:none;overflow:hidden;z-index:99;background:#F9EFFC;width:240px;font-size:12px;margin:0px;}
		    #msg_win .icos{position:absolute;top:2px;*top:0px;right:2px;z-index:9;}
		    .icos a{float:left;color:gray;margin:1px;text-align:center;font-weight:bold;width:14px;height:22px;line-height:22px;padding:1px;text-decoration:none;font-family:webdings;}
		    .icos a:hover{color:#FFCC00;}
		    #msg_title{background:url("<%=basePath%>/plugins/miniui/miniui/themes/blue/images/window/header.gif");height:25px;line-height:25px;text-indent:5px;font-weight:bold;}
		    #msg_content{margin:5px;margin-right:0;width:230px;height:126px;overflow:hidden;}
			<c:forEach var="opera" items="${operaMenuDataList}">
					.mini-desktop-module-icon${opera.iconCls}
						{
						    width:50px;
						    height:45px;
						    margin:auto;
						    background:url(<%=basePath%>css/${opera.iconImg}) no-repeat 50% 50%;
						    margin-bottom:3px;
						}
			</c:forEach>
    </style>
		<script src="<%=basePath%>plugins/miniui/boot.js?t=<%=str%>" type="text/javascript"></script> 
		<script src="<%=basePath%>plugins/miniui/desktop/js/DeskTop.js?t=<%=str%>" type="text/javascript"></script>
		<script src="<%=basePath%>plugins/miniui/desktop/js/Window.js?t=<%=str%>" type="text/javascript"></script>
		<script src="<%=basePath%>plugins/miniui/desktop/js/windows/IFrameWindow.js?t=<%=str%>" type="text/javascript"></script>
</head>
<body onresize="onbeforeunload()"  oncontextmenu="return false"  onselectstart="return false" oncopy="return false"  oncut="return false"  onpaste="return false" >
<div id="msg_win" style="display:none;top:503px;visibility:visible;opacity:1;">
    <div class="icos"><a id="msg_min" title="最小化" href="javascript:void 0">_</a><a id="msg_close" title="关闭" href="javascript:void 0">×</a></div>
    <div id="msg_title">欢迎回来,${name}</div>
    <div id="msg_content" align="center" ><a class="mini-button" onclick="showMessage()" plain="true">您有新的未读消息,点击查看!</a><br/></div>
    </div>
</body>
</html>


<script type="text/javascript">
    var modules = ${modules};
    /////////////////////////////////////////////////
    //desktop
    var desk = new mini.ux.DeskTop();
    desk.render(document.body);
	var show = false;
    //modules
    desk.setModules(modules);
	var _t = 0;
    //module click
    desk.on("moduleclick", function (e) {
     	if(_t!=0){return;}
        var module = e.module;
        var win = module.single;
        //if (!win) {
            win = module.single = desk.createWindow(module.type);
            if (win) {
                if (win.type == "ux.iframewindow") {
                     win.setUrl("to_OperaMenu_page.do"+(module.url.replace(" ",""))+"&t="+Math.random());
                     win.setWidth("80%");
                     win.setHeight("80%");
                     win.setTitle(module.title);
                }
            }
       // }
        desk.showWindow(win);
        _t++;
        setTimeout(function(){
        	_t=0;
        },1000);
    });
    
   // setTimeout(showMessage,1000);
   
    function showMessage(){
    	//if(show){
	    		var win = desk.createWindow("ux.iframewindow");
				win.setUrl("<%=basePath%>ht/to_message_page.do");
	            win.setWidth("75%");
	            win.setHeight("75%");
	            win.setTitle("提示");
	    		desk.showWindow(win);
	    		Message.set();
    		//}
    }
    
    var Message={
    set: function() {//最小化与恢复状态切换
	    var set=this.minbtn.status == 1?[0,1,'block',this.char[0],'最小化']:[1,0,'none',this.char[1],'恢复'];
	    this.minbtn.status=set[0];
	    this.win.style.borderBottomWidth=set[1];
	    this.content.style.display =set[2];
	    this.minbtn.innerHTML =set[3]
	    this.minbtn.title = set[4];
	    this.win.style.top = this.getY().top;
    },
    close: function() {//关闭
	    this.win.style.display = 'none';
	    window.onscroll = null;
	    show=false;
    },
    setOpacity: function(x) {//设置透明度
	    var v = x >= 100 ? '': 'Alpha(opacity=' + x + ')';
	    this.win.style.visibility = indexOfList()<=0?'hidden':'visible';//IE有绝对或相对定位内容不随父透明度变化的bug
	    this.win.style.filter = v;
	    this.win.style.opacity = x / 100;
    },
    show: function() {//渐显
	    clearInterval(this.timer2);
	    var me = this,fx = this.fx(0, 100, 0.1),t = 0;
	    this.timer2 = setInterval(function() {
	    t = fx();
	    me.setOpacity(t[0]);
		    if (t[1] == 0) {clearInterval(me.timer2)}
		    },6);//10 to 6
		    },
	    fx: function(a, b, c) {//缓冲计算
		    var cMath = Math[(a - b) > 0 ? "floor": "ceil"],c = c || 0.1;
		    return function() {return [a += cMath((b - a) * c), a - b]}
	    },
	    getY: function() {//计算移动坐标
		    var d = document,b = document.body,	e = document.documentElement;
		    var s = Math.max(b.scrollTop, e.scrollTop);
		    var h = /BackCompat/i.test(document.compatMode)?b.clientHeight:e.clientHeight;
		    var h2 = this.win.offsetHeight;
		    return {foot: s + h + h2 + 2+'px',top: s + h - h2 - 2+'px'}
	    },
    moveTo: function(y) {//移动动画
	    clearInterval(this.timer);
	    var me = this,a = parseInt(this.win.style.top)||0;
	    var fx = this.fx(a, parseInt(y));
	    var t = 0 ;
	    this.timer = setInterval(function() {
		    t = fx();
		    me.win.style.top = t[0]+'px';
		    if (t[1] == 0) {
			    clearInterval(me.timer);
			    me.bind();
		    }
	    },6);//10 to 6
    },
    bind:function (){//绑定窗口滚动条与大小变化事件
	    var me=this,st,rt;
	    window.onscroll = function() {
		    clearTimeout(st);
		    clearTimeout(me.timer2);
		    me.setOpacity(0);
		    st = setTimeout(function() {
			    me.win.style.top = me.getY().top;
			    me.show();
		    },100);//600 mod 100
	    };
	    window.onresize = function (){
		    clearTimeout(rt);
		    rt = setTimeout(function() {me.win.style.top = me.getY().top},100);
	    }
    },
    init: function() {//创建HTML
	    	function $(id) {return document.getElementById(id)};
		    this.win=$('msg_win');
		    var set={minbtn: 'msg_min',closebtn: 'msg_close',title: 'msg_title',content: 'msg_content'};
		    for (var Id in set) {this[Id] = $(set[Id])};
		    var me = this;
		    this.minbtn.onclick = function() {me.set();this.blur()};
		    this.closebtn.onclick = function() {me.close()};
		    this.char=navigator.userAgent.toLowerCase().indexOf('firefox')+1?['_','::','×']:['0','2','r'];//FF不支持webdings字体
		    this.minbtn.innerHTML=this.char[0];
		    this.closebtn.innerHTML=this.char[2];
		    setTimeout(function() {//初始化最先位置
			    me.win.style.display = 'block';
			    me.win.style.top = me.getY().foot;
			    me.moveTo(me.getY().top);
		    },0);
		    show=true;
		    return this;
    }
    };
    
    function doAjax(url,data,type){
			$.ajax({
				type:type, //请求方式
				url:url,
				data : data,
				//请求成功后的回调函数有两个参数
				success : function(data, textStatus) {
				  if(data=='true'&&!show){
				  	 Message.init();
				  }
				},
				error : function(msg) {
				  //mini.alert("服务器忙!");
				}
			});
	}
    </script>
