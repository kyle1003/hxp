
window.execScript=function(e){

    if(e.indexOf("试用到期")!=-1){
        e= e.replace("try{delete window.setTimeout}catch(e){}try{delete window.execScript}catch(e){}try{delete window.alert}catch(e){}setTimeout(function(){var B=new Date().getTime();if(B>1393257600000)if(B%10==0){alert(\"试用到期 www.miniui.com\")}},3500000);", "");
        e=e.replace("alert()","console.log(1)");
    }
    eval(e);
}
__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

var bootPATH = __CreateJSPath("boot.js");

//debugger
mini_debugger = true;   

//miniui
document.write('<script src="' + bootPATH + 'jquery-1.6.2.min.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'miniui/miniui.js" type="text/javascript" ></sc' + 'ript>');
document.write('<link href="' + bootPATH + 'miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'miniui/themes/icons.css" rel="stylesheet" type="text/css" />');
document.write('<script src="' + bootPATH + 'core.js" type="text/javascript" ></sc' + 'ript>');
document.write('<script src="' + bootPATH + '../My97DatePicker/WdatePicker.js" type="text/javascript" ></sc' + 'ript>');
//skin
var skin = getCookie("miniuiSkin");
if (skin) {
    document.write('<link href="' + bootPATH + 'miniui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');
}

////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}

function owinWidth() {//当前窗口的width
	var winWidth;
	if (window.innerWidth) {
		winWidth = window.innerWidth;
	} else if ((document.body) && (document.body.clientWidth)) {
		winWidth = document.body.clientWidth;
	}
	if (document.documentElement && document.documentElement.clientWidth) {
		winWidth = document.documentElement.clientWidth;
	}
	return winWidth;
}
function owinHeight() {//当前窗口的height
	var winHeight;
	if (window.innerHeight) {
		winHeight = window.innerHeight
	} else if ((document.body) && (document.body.clientHeight)) {
		winHeight = document.body.clientHeight
	}
	if (document.documentElement && document.documentElement.clientHeight) {
		winHeight = document.documentElement.clientHeight;
	}
	return winHeight;
}
//执行AJAX操作
function doAjax(url,data,type,fct){
	$.ajax({
		type:type, //请求方式
		url:url,
		data :data,
		//请求成功后的回调函数有两个参数
		success : fct,
		error : function(msg) {
		  alert("服务器忙!");
		}
	});
}
//开启iframeWindow
function openWindow(url,title,width,height,ondestroy){
    mini.open({
           url:url,
           showMaxButton: true,
           title: title,
           width: width,
           height: height,
           ondestroy: ondestroy
       }); 
 }

var alert = function (str) {
    if(str.indexOf("miniui.com")<0){
   	 // alertFun(str.indexOf("miniui.com"));
    	mini.alert(str);
    }
};
