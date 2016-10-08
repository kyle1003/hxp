<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
<%@page import="cn.hurry.util.BaseString"%>
<%@page import="cn.hurry.po.succession.Succession"%>
<%@page import="cn.hurry.manage.succession.SuccessionManage"%>
<%@page import="cn.hurry.po.succession.SuccessionInfo"%>
<%@page import="cn.hurry.manage.succession.SuccessionInfoManage"%>
<%@page import="cn.hurry.util.DateTimeUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute("user");
	if(user==null){
		request.getRequestDispatcher("/login.jsp").forward(request,response);
	}
	//是否有操作权限
	boolean hasOperate = false;
	//显示开班
	boolean showStartWork = false;
	//显示结班
	boolean showHandsOver = false;
	//显示接班
	boolean showTakeOver = false;
	boolean showOver= false;
	//交接班控制
	Succession working = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKING);
	Succession notHandover = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKOVER_BUT_NOT_HANDOVER);
	SuccessionInfo info = SuccessionInfoManage.getToDaySuccessionInfo();
	if(info!=null){
		switch(info.getStatus()){
			case SuccessionInfo.status_notStart:
				showStartWork = true;
				hasOperate = true;
			break;
			case SuccessionInfo.status_working:
				showHandsOver=true;
				hasOperate = true;
				if(working!=null&&working.getTakeOverUserId()!=user.getId()){
					hasOperate = false;
				}
			break;
			case SuccessionInfo.status_settleing:
				hasOperate = true;
				showOver = true;
				showTakeOver=true;
			break;
			default:
				hasOperate=false;
				break;
		}
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
		<script type="text/javascript" src="<%=basePath%>plugins/hurryPrint/hurryPrint.js"></script>
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

	<body  >
<!--Layout oncontextmenu=self.event.returnValue=false onselectstart="return false"-->
       <div class="mini-toolbar" style="padding:2px;border:0;">
       <h1 align="center">收银</h1>
       <hr />
	       <div style="width:800px; text-align: center;margin: auto;">
	       		<a class="mini-button" iconCls="icon-add" plain="true" onclick="showCenter('add');">添加商品(+)</a>
	            <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新(F5)</a>
	    		<input class="mini-hidden" name="type" value="<%=Order.TYPE_SELL_ORDER%>"/>
				<label style="font-size: 20px;font-weight: 800;">商品总数:<label id="countNumber" style="color: red">0</label></label>
     			<label style="font-size: 20px;font-weight: 800;">应收金额:<label id="countPrice" style="color: red">0</label></label>
        		<label style="font-size: 20px;font-weight: 800;">实收金额:<input class="mini-textbox" selectOnFocus="true" onenter="sbOrder()" name="pay" id="sdpay" style="width: 80px;" vtype="float" required="true" /></label>
        		<br />操作员:<input value="${user.id}" enabled="false" class="mini-combobox"  name="userId"  url="<%=basePath%>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX%>" style="width: 80px" required="true" />
	       		支付方式:<input value="现金" textField="id" class="mini-combobox" id="payType" name="payType"  url="<%=basePath%>jsp/sell/payType.json" style="width: 80px" required="true" />
	       		
	       		<%if(showStartWork){%>
	       			<a class="mini-button" iconCls="icon-add" plain="true" onclick="startWork();">开班</a>
	       		<%}else if(showTakeOver){%>
	       			<a class="mini-button" iconCls="icon-add" plain="true" onclick="takeOver();">开班</a>
	       		<%}else{ %>
	       			<a class="mini-button" iconCls="icon-add" plain="true" onclick="sbOrder();">结算(enter)</a>
	       		<%} %>
	       		
	       	</div>
	       	<div style=" width: 80%;text-align: right;">
	       		<%if(showHandsOver){%>
	       				<a class="mini-button" iconCls="icon-add" plain="true" onclick="handsOver();">结班</a>
	       				<a class="mini-button" iconCls="icon-add" plain="true" onclick="workOver();">结班并日结</a>
	       		<%}%>
	       			<%=info!=null?DateTimeUtils.format(info.getDate(),DateTimeUtils.YEAR_MONTH_DAY):"" %>
	       	</div>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" id="gr" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid" showPager="false"  style="width:100%;height:100%;"  url="<%=basePath%>order_goods_list_session.do"  idField="oid" sizeList="[5,10,20,50]" pageSize="10" contextMenu="#gridMenu" headerContextMenu="#headerMenu" >
	            <div property="columns">
	                <div field="code" width="150" headerAlign="center" allowSort="false">条码</div> 
	                <div field="name" width="100" headerAlign="center" allowSort="false">名称</div>
	                <div field="goodsType" width="80" headerAlign="center" allowSort="false">类别</div> 
	                <div field="unit" width="100" headerAlign="center" allowSort="false">单位</div>
	                <div field="norms" width="100" headerAlign="center" allowSort="false">规格型号</div>                              
	          	 	<div field="price" width="80"  headerAlign="center" allowSort="false">单价</div>
	          	 	<div field="odNumber" width="80"  headerAlign="center" allowSort="false">数量</div>
	          	 	<div field="countPrice" width="80"  headerAlign="center" allowSort="false">总价</div>
	            </div>
	        </div> 
	    </div>
	    
	    <div id="win1" class="mini-window" showCloseButton="false" title="Window" style="width:400px;height:300px;"  showMaxButton="false" showToolbar="false" showFooter="true" showModal="true" allowResize="false" allowDrag="true" >
		    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		        <a class="mini-button" id="submitButton" onclick="closeCenter()">关闭(esc)</a> <a class="mini-button" id="submitButton" onclick="sbmitForm()">提交</a>
		    </div>
		    <fieldset style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;">
		    	 <legend>商品信息</legend>
		    	<table>
			    	<tr>
			    		<td colspan="1">
			    			条码(助记):<input class="mini-textbox" selectOnFocus="true" id="code"  onenter="addGoodsByCode" style="width: 180px;height: 25px;" />
			    		</td>
			    	</tr>
		    		<tr>
		    			<td>
		    			  	商品:<input id="goods"  style="width: 180px;" class="mini-buttonedit" onenter="chooseGoods(this)" onbuttonclick="chooseGoods(this)" required="true" />
		    			</td>
		    			<td>
		    			  	编号:<label id="goods_id" style="color: red"></label>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>
		    			  	规格:<label id="goods_norms" style="color: red"></label>
		    			</td>
		    			<td>
		    			  	单位:<label id="goods_unit" style="color: red"></label>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>
		    			  	预计销售价:<label id="goods_sellPrice"  style="color: red"></label>
		    			  </td>
		    			<td>
		    			  	库存量:<label id="goods_number" style="color: red"></label>
		    			</td>
		    		</tr>
		    	</table>
		    </fieldset>
		    	 <div id="form">
		    	 	<input class="mini-hidden" value="0" id="goodsId" name="goodsId"  />
		    	 	<input class="mini-hidden" name="type" value="<%=Order.TYPE_SELL_ORDER%>"/>
				    <fieldset style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;">
				    	<legend>销售信息</legend>
				    	<table>
				    		<tr>
				    			<td>
				    				本次销售价:<input class="mini-textbox" selectOnFocus="true" id="price" name="price" onenter="mini.get('number').focus();" style="width: 80px;" required="true" />
				    			</td>
				    			<td>
				    			  	本次销售量:<input class="mini-textbox" selectOnFocus="true" id="number" onenter="addGoods()" name="number" value="1" vtype="range:1,10000" style="width: 80px;"   required="true" />
				    			</td>
				    		</tr>
				    	</table>
				    </fieldset>
				</div>
				<ul id="gridMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
					<li name="edit" iconCls="icon-add" onclick="showCenter('add')">
						新增商品(+)
					</li>
					<li name="remove" iconCls="icon-remove" onclick="removeGoods">
						删除商品(delete)
					</li>
					<li name="edit" iconCls="icon-reload" onclick="grid.reload();">
						刷新(F5)
					</li>
				</ul>
		</div>
		
		<div id="win2" class="mini-window" title="结算" showCloseButton="false" style="width:300px;height:200px;"  showMaxButton="false" showToolbar="false" showFooter="true" showModal="true" allowResize="false" allowDrag="true" >
		    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		         <a class="mini-button" id="submitButton" onclick="win2.hide();startSubmit=false;">关闭(esc)</a> <a class="mini-button" id="submitButton" onclick="addOrder()">提交</a>
		    </div>
		    <div style="margin: auto;text-align: center;" >
		    <table style="font-size: 30px; width: 100%">
		    	<tr>
		    		<td align="right">
		    			数量:
		    		</td>
		    		<td align="left">
		    			<label style="color: red" id="jsNumber"></label>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">
		    			应收:
		    		</td>
		    		<td align="left">
		    			<label style="color: red" id="jsPrice"></label>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">
		    			实收:
		    		</td>
		    		<td align="left">
		    			<label style="color: red" id="jsSsPrice"></label>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">
		    			找零:
		    		</td>
		    		<td align="left">
		    			<label style="color: red" id="jsZlPrice"></label>
		    		</td>
		    	</tr>
		    </table>
		    </div>
		</div>
</body>

</html>
    <script type="text/javascript">
    	
    	var type='<%=Order.TYPE_SELL_ORDER%>';
        mini.parse();
        var win2 = mini.get("win2");
        var grid = mini.get("datagrid");
		var priceObj = mini.get("price");
		var goodsIdobj = mini.get("goodsId")
		var operateType="add";
		var countPriceObj = document.getElementById("countPrice");
		var countNumberObj = document.getElementById("countNumber");
		grid.load({type:type});
		
		window.confirm=function (text,title,callback){
			mini.confirm(text,title,callback);
		}
		
		function onBeforeOpen(e) {
		    var menu = e.sender;
		    var row = grid.getSelected();
		    var rowIndex = grid.indexOf(row);            
		    //if (!row||rowIndex== -1) {
		    //    e.cancel = true;
		        //阻止浏览器默认右键菜单
		     //   e.htmlEvent.preventDefault();
		     //  return;
	   		// }
		}

		var startSubmit = false;
		var sbmtData = {pay:0};
		 function sbOrder(){
			var countNumber = 0.0;
			var countPrice = 0.0;
			var sdPay =  parseFloat(mini.get("sdpay").value);
			var data = grid.getData();
			for(var i=0;i<data.length;i++){
				countPrice+=parseFloat(data[i].countPrice);
				countNumber+=parseFloat(data[i].odNumber);
			}
	       	$("#jsPrice").html(countPrice.toFixed(2));
	       	$("#jsSsPrice").html(sdPay.toFixed(2));
	       	$("#jsZlPrice").html((sdPay-countPrice).toFixed(2));
	       	$("#jsNumber").html(countNumber);
	       	 
	       	 sbmtData.pay= sdPay.toFixed(2);
	       	 sbmtData.payType=mini.get("payType").value;
			 win2.show()
			setTimeout(function(){
				 startSubmit = true;
			},500);
		}
		function addGoodsByCode(e){
			var code = mini.get("code").getValue();
			if(mini.get("goods").value!=""){
					addGoods();
					return;
				}
			doAjax("<%=basePath%>get_goods_by_code.do",{code:code,t:new Date().getTime()},"POST",function(d, textStatus) {
				var rs = JSON.parse(d); 
				var data={};
				if(rs.status=="1"){
					 data =JSON.parse(rs.data);
				}
				if(data.length==1){
					  mini.get("goods").setValue(data[0].id);
					  mini.get("goods").setText(data[0].name+"("+data[0].goodsType+")");
                      $("#goods_id").html(data[0].id);
                      $("#goods_norms").html(data[0].norms);
                      $("#goods_unit").html(data[0].unit);
                      $("#goods_sellPrice").html(data[0].sellPrice);
                      $("#goods_number").html(data[0].number?data[0].number:0);
                      mini.get("code").setValue(code);
                      priceObj.setValue(data[0].sellPrice);
                      goodsIdobj.setValue(data[0].id);
                      addGoods();
                      //mini.get("price").focus();
				}else{
					chooseGoods(mini.get("goods"),code);
				}
			});
		}
		var isShow = false;
		 function showCenter(type) {
			 if(<%=showStartWork%>){
				 	startWork();
					return;
			 }
			 if(<%=showTakeOver%>){
				 	takeOver();
					return;
			 }
			 if(<%=!hasOperate%>){
					alert("未结班，或已经日结");
					window.close();
		     }
	        var win = mini.get("win1");
	        if(type=="add"){
	        	operateType="add";
	        	win.setTitle("添加商品");
	        }
	        isShow = true;
	        win.show();
	        mini.get("code").focus();
	        mini.get("code").setValue("");
	    }
		 function closeCenter() {
			 isShow = false;
			 mini.get("win1").hide();
			 mini.get("sdpay").focus();
		 }

		 function startWork(){
			 window.confirm("确认开班?","提示",function(s){
					if(s=="ok"){
						doAjax("<%=basePath%>do_startwork.do",{t:new Date().getTime()},"POST",function(d, textStatus) {
							var json = JSON.parse(d); 
							if(!json.success){
								alert("开班失败，请重试"+json.msg);
								return;
							}else{
								alert(json.msg+"");
								window.location.reload();
							}
						});
					}
			});
		}

		function handsOver(){
			confirm("确认结班?","提示",function(s){
				if(s=="ok"){
					doAjax("<%=basePath%>do_handsover.do",{t:new Date().getTime()},"POST",function(d, textStatus) {
						var json = JSON.parse(d); 
						if(!json.success){
							alert("结班失败，请重试"+json.msg);
							return;
						}else{
							printInfo(false,json.data);
						}
					});
				}
			});
		}

			function takeOver(){
				confirm("确认开班?","提示",function(s){
					if(s=="ok"){
						doAjax("<%=basePath%>do_takeover.do",{t:new Date().getTime()},"POST",function(d, textStatus) {
							var json = JSON.parse(d); 
							if(!json.success){
								alert("开班失败，请重试"+json.msg);
								return;
							}else{
								alert(json.msg+"");
								window.location.reload();
							}
						});
					}
				});
			}
		
				function workOver(){
					confirm("确认日结(日结后当天不能再收银)?","提示",function(s){
						if(s=="ok"){
							doAjax("<%=basePath%>do_workover.do",{t:new Date().getTime()},"POST",function(d, textStatus) {
								var json = JSON.parse(d); 
								if(!json.success){
									alert("失败，请重试"+json.msg);
									return;
								}else{
									printInfo(true,json.data);
								}
							});
						}
				});
			}
		function printInfo(all,id){
			mini.showMessageBox({
	            title: "打印方式",
	            iconCls: "mini-messagebox-question",
	            buttons: ["打印", "不打印"],
	            message: "选择销售情况打印方式",
	            callback: function (action) {
					  if(action!="不打印"){
						var printUrl = "<%=basePath%>get_workover_printData.do";
						if(all){
							setTimeout(function(){
								doAjax(printUrl,{all:"true",id:id,t:new Date().getTime()},"POST",function(d, textStatus) {
									var json = JSON.parse(d); 
									if(!json.success){
										alert("打印失败!"+json.msg);
										return;
									}else{
										printHttp(json.data);
									}
								});
								//window.open(printUrl+"&all=true","newwindow","height=100,width=500,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
							},500);
							}
						//window.open(printUrl,"newwindow2","height=100,width=500,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
						doAjax(printUrl,{all:"false",id:id,t:new Date().getTime()},"POST",function(d, textStatus) {
							var json = JSON.parse(d); 
							if(!json.success){
								alert("打印失败!"+json.msg);
								return;
							}else{
								printHttp(json.data);
							}
						});
					}
					setTimeout(function(){
						  window.close();
					},1000);
	            }
	        });		
		}
				  
	    function addOrder(e){
			var json = mini.encode(sbmtData);
			if(grid.totalCount==0){
				mini.alert("请添加商品");
				return;
			}
			 var messageid = mini.loading("正在提交,请稍候...", "保存中");
			 doAjax("<%=basePath%>sell_add.do",{json:json,t:new Date().getTime()},"POST",function(d, textStatus) {
				 mini.hideMessageBox(messageid);
				 grid.reload();
				 startSubmit = false;
				 var id = d;
				 if(id.indexOf("XS")==-1){
					mini.alert(d);
					return;
				 }else{
			       	 var sdp = $("#jsPrice").html();
			       	 var sf = $("#jsSsPrice").html();
			       	 var zl = $("#jsZlPrice").html();
			       	 //var printurl = "<%=basePath%>sell_order_info.do?id="+id+"&sdp="+sdp+"&sf="+sf+"&zl="+zl;
			       	doAjax("<%=basePath%>get_sellInfo_printData.do",{sdp:sdp,sf:sf,zl:zl,id:id,t:new Date().getTime()},"POST",function(d, textStatus) {
						var json = JSON.parse(d); 
						if(!json.success){
							alert("打印失败!"+json.msg);
							return;
						}else{
							printHttp(json.data);
						}
					});
				    //var wp = window.open(printurl,"newwindow","height=100,width=500,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
			         window.focus();
				 }
				 setTimeout(function(){
					 win2.hide();
					 startSubmit=false;
					 showCenter("add");
					// wp.close();
				},2000);
				});
			form.reset();
	    }
	    
	    function sbmitForm(){
	    	if(operateType=="add"){
	    		addGoods();
	    	}
	    	if(operateType=="edit"){
	    		 editGoods();
	    	}
	    }
	    
		function removeGoods(){
			var d=grid.getSelected();
        	if(!d){mini.alert("请选择！"); return;}
        	doAjax("<%=basePath%>order_goods_delete_session.do",{id:d.oid,type:type,t:new Date().getTime()},"POST",function(data, textStatus) {
			 grid.reload();
			});
		}
		
		function addGoods(){
			var form = new mini.Form("#form");
            form.validate();
            if (!form.isValid()){return};
            if(goodsIdobj.value==0){mini.alert("请选择商品");return;}
            var data = form.getData();
			var json = mini.encode(data);
			doAjax("<%=basePath%>order_goods_add_session.do",{json:json,type:type,t:new Date().getTime()},"POST",function(d, textStatus) {
				if(d!="<%=BaseString.INFO_ADD_SUCCESS%>"){
					mini.alert(d);
				 }
				 grid.reload();
				 mini.get("code").focus();
			});
			resetForm();
		}
		
		function resetForm(){
			var goodsObj = mini.get("goods");
			 goodsObj.setValue(0);
			 mini.get("number").setValue(1);
             goodsObj.setText("");
             document.getElementById("goods_id").innerHTML="";
             document.getElementById("goods_norms").innerHTML="";
             document.getElementById("goods_unit").innerHTML="";
             document.getElementById("goods_sellPrice").innerHTML="";
             document.getElementById("goods_number").innerHTML="";
             priceObj.setValue(0);
             goodsIdobj.setValue(0);
             mini.get("code").setValue("");
		}
		
		function resetText(){
			 countPriceObj.innerHTML=0;
			 countNumberObj.innerHTML=0;
		}
		
		function chooseGoods(e,code) {
            var btnEdit = e;
            mini.open({
                url: "<%=basePath%>jsp/goods/choose_goods.jsp?haveBuyOrder=true&code="+(code?code:""),
                title: "选择列表",
                width: '70%',
                height:'70%',
                ondestroy: function (action) {
                    //if (action == "close") return false;
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            btnEdit.setValue(data.id);
                            btnEdit.setText(data.name+"("+data.goodsType+")");
                            document.getElementById("goods_id").innerHTML=data.id;
                            document.getElementById("goods_norms").innerHTML=data.norms;
                            document.getElementById("goods_unit").innerHTML=data.unit;
                            document.getElementById("goods_sellPrice").innerHTML=data.sellPrice;
                            document.getElementById("goods_number").innerHTML=(data.number?data.number:0);
                            mini.get("code").setValue(data.code);
                            priceObj.setValue(data.sellPrice);
                            goodsIdobj.setValue(data.id);
                           // mini.get("number").setVtype("range:1,"+data.number);
                        }
                    }
					mini.get("code").focus();
                }
            });            
		}
		grid.on("drawcell", function (e) {
			var record = e.record,
	        column = e.column,
	        field = e.field,
	        value = e.value;
       });

		function initNumber(){
			var countNumber = 0.0;
			var countPrice = 0.0;
			var data = grid.getData();
			for(var i=0;i<data.length;i++){
				countPrice+=parseFloat(data[i].countPrice);
				countNumber+=parseFloat(data[i].odNumber);
				}
			 $("#countNumber").html(countNumber.toFixed(2));
	       	 $("#countPrice").html(countPrice.toFixed(2));
	       	 mini.get("sdpay").setValue(countPrice.toFixed(2));
		}
       
       grid.on("load", function (e) {
    	   initNumber();
       });
		
       $(function () {
           $("#gr").bind("keydown", "del", function (ev) {
               if(ev.keyCode==46){
            	   var d=grid.getSelected();
	               	if(!d){return true;}
	               	doAjax("<%=basePath%>order_goods_delete_session.do",{id:d.oid,type:type,t:new Date().getTime()},"POST",function(data, textStatus) {
	       			 grid.reload();
	       			});
           			return true; 
               }
           });
           $(document).bind("keydown", "+", function (ev) {
               if((ev.keyCode==106||ev.keyCode==107)&&!isShow){
            	   showCenter("add");
           		   return false; 
               }
               if(ev.keyCode==116){
                   grid.reload();
            	   return false;
         		}
               if(ev.keyCode==13&&startSubmit){
            	   addOrder();
            	   return false; 
               }
               if(ev.keyCode==27&&startSubmit){
            	   win2.hide();
            	   startSubmit=false;
            	   return false; 
               }
           });
           
           $("#win1").bind("keydown", "esc", function (ev) {
               if((ev.keyCode==27||ev.keyCode==109)&&isShow){
            	   closeCenter();
            	   return false; 
               }
           });
       });
       setTimeout(function(){showCenter('add');},500);
       if(<%=!hasOperate%>){
			alert("未结班，或已经日结");
			window.close();
       }
    </script>
    

