//打印API接口地址
var print_API="http://127.0.0.1:5335/";

//创建打印框架
function hurry_createHtml(){
	var body = document.getElementsByTagName("body")[0];
	var _iframe=document.createElement("iframe");
	var _a=document.createElement("a");
	_iframe.style.display="none";
	_iframe.setAttribute("id", "hurry_print_frame");
	_iframe.setAttribute("name", "hurry_print_frame");
	_a.setAttribute("id", "hurry_print_action");
	_a.setAttribute("target", "hurry_print_frame");
	body.appendChild(_iframe);
	body.appendChild(_a);
}
//打印数据
function printHttp(base64JsonData){
	hurry_createHtml();
	var url = print_API+"hurry_print?data="+base64JsonData
	var _a= document.getElementById("hurry_print_action");
	_a.href=url;
	_a.click();
}