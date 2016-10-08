<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
<%@page import="cn.hurry.util.BaseString"%>
<%@page import="cn.hurry.po.order.OrderGoods"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute("user");
	if(user==null){
		request.getRequestDispatcher("/login.jsp").forward(request,response);
	}
	session.removeAttribute("order_goods_list_"+Order.TYPE_BUY_ORDER);
	session.removeAttribute("order_goods_id");
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

	<body>
<!--Layout-->
       <div class="mini-toolbar" style="padding:2px;border:0;">
       <h1 align="center">入库单</h1>
       <hr />
	       <div style="width:800px; height: 50px;text-align: center;margin: auto;">
	       	    <a class="mini-button" iconCls="icon-add" plain="true" onclick="showCenter('add')">添加商品</a>
	         <!--   <a class="mini-button" iconCls="icon-edit" plain="true" onclick="showCenter('edit')">编辑</a> --> 
	            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeGoods()">删除商品</a>
	           
	             <span class="separator"></span>
	            <a class="mini-button" iconCls="icon-reload" plain="true" onclick="grid.reload();">刷新</a>
	       	</div>
	    </div>
	    <!--撑满页面-->
	    <div class="mini-fit" style="height:100px;" align="center">
	        <div id="datagrid" class="mini-datagrid" showPager="false"  style="width:100%;height:100%;"  url="<%=basePath%>order_goods_list_session.do"  idField="oid" sizeList="[5,10,20,50]" pageSize="10" contextMenu="#gridMenu" headerContextMenu="#headerMenu" >
	            <div property="columns">
	                <div field="code" width="80" headerAlign="center" allowSort="false">条码</div> 
	                <div field="name" width="100" headerAlign="center" allowSort="false">名称</div>
	                <div field="goodsType" width="80" headerAlign="center" allowSort="false">类别</div> 
	                <div field="unit" width="100" headerAlign="center" allowSort="false">单位</div>
	                <div field="norms" width="100" headerAlign="center" allowSort="false">规格型号</div>                              
	          	 	<div field="buyPrice" width="100"  headerAlign="center" allowSort="false">预计采购价</div>
	          	 	<div field="price" width="100"  headerAlign="center" allowSort="false">采购价</div>
	          	 	<div field="odNumber" width="100"  headerAlign="center" allowSort="false">采购数量</div>
	          	 	<div field="countPrice" width="100"  headerAlign="center" allowSort="false">总价</div>
	          	 	<div field="remark" width="100"  headerAlign="center" allowSort="false">备注</div>
	            </div>
	        </div> 
	    </div>
	    <div id="form2" style="height: 60px;text-align: center;margin: auto;margin-top: 20px">
	    		<input class="mini-hidden" name="type" value="<%=Order.TYPE_BUY_ORDER%>"/>
				<label style="font-size: 20px;font-weight: 800;">商品总数:<label id="countNumber" style="color: red">0</label></label>
     			<label style="font-size: 20px;font-weight: 800;">应付金额:<label id="countPrice" style="color: red">0</label></label>
        		<label style="font-size: 20px;font-weight: 800;">实付金额:<input class="mini-textbox" name="pay" id="pay" style="width: 80px;" vtype="float" required="true" /></label>
        		&nbsp;&nbsp;&nbsp;仓库:<input class="mini-combobox" name="storeId"  url="<%=basePath%>store_list_json.do?type=<%=JSON.TYPE_COMBOBOX%>" style="width: 80px" required="true" />
        		&nbsp;&nbsp;&nbsp;经办人:<input class="mini-combobox" value="${sessionScope.user.id }"  name="userId"  url="<%=basePath%>user_combobox.do?type=<%=JSON.TYPE_COMBOBOX%>" style="width: 80px" required="true" />
		       	<span class="separator"></span>
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="addOrder">提交入库单</a>
		       	<br/> 备注:<input class="mini-textbox" name="remark" id="remark" style="width: 180px;"/>
	   </div>
	    
	    <div id="win1" class="mini-window" title="Window" style="width:400px;height:420px;"  showMaxButton="false" showToolbar="false" showFooter="true" showModal="true" allowResize="false" allowDrag="true" >
		    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		        <a class="mini-button" id="submitButton" onclick="sbmitForm()">提交</a>
		    </div>
		    <fieldset style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;">
		    	 <legend>商品信息</legend>
		    	<table>
		    		<tr>
			    		<td colspan="1">
			    			条码:<input class="mini-textbox" selectOnFocus="true" id="code"  onenter="addGoodsByCode" style="width: 180px;height: 25px;" />
			    		</td>
			    	</tr>
		    		<tr>
		    			<td>
		    			  	商品:<input id="goods"  style="width: 180px;" class="mini-buttonedit" onbuttonclick="chooseGoods(this)" required="true" />
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
		    			  	剂量:<label id="goods_dose" style="color: red"></label>
		    			</td>
		    			<td>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>
		    			  	预计进货价:<label id="goods_buyPrice" style="color: red"></label>
		    			  </td>
		    			<td>
		    			  	库存量:<label id="goods_number" style="color: red"></label>
		    			</td>
		    		</tr>
		    	</table>
		    </fieldset>
		    	 <div id="form">
		    	 	<input class="mini-hidden" value="0" id="goodsId" name="goodsId"  />
		    	 	<input class="mini-hidden" name="type" value="<%=Order.TYPE_BUY_ORDER%>"/>
				    <fieldset style="width:95%;border:solid 1px #aaa;margin-top:8px;position:relative;">
				    	<legend>进货信息</legend>
				    	<table>
				    		<tr>
				    			<td>
				    				本次进货价:<input class="mini-textbox" id="price" name="price" style="width: 80px;" required="true" />
				    			</td>
				    			<td>
				    			  	本次进货量:<input class="mini-textbox" onenter="addGoods()"  selectOnFocus="true" id="number" name="number"  vtype="range:1,10000" style="width: 80px;"   required="true" />
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    				生产日期:<input class="mini-datepicker" id="productionDate" name="productionDate" style="width: 100px;" required="true" />
				    			</td>
				    			
				    			<td >
				    			有效期:<input class="mini-datepicker" id="shelfLife" name="shelfLife" style="width: 100px;" required="true" />
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    				生产批号:<input class="mini-textbox" id="productionBatchNumber" name="productionBatchNumber" style="width: 100px;" />
				    			</td>
				    			<td >
				    				批准文号:<input class="mini-textbox" id="approvalDocument" name="approvalDocument" style="width: 100px;"  />
				    			</td>
				    		</tr>
				    	</table>
				    </fieldset>
				</div>
				<ul id="gridMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
					<li name="edit" iconCls="icon-add" onclick="showCenter('add')">
						新增商品
					</li>
					<li name="remove" iconCls="icon-find" onclick="removeGoods">
						删除商品
					</li>
					<li name="edit" iconCls="icon-reload" onclick="grid.reload();">
						刷新
					</li>
				</ul>
			<label style="color: red;">*注意：进货单必须审核通过后才能生效。审核通过后将不能再修改！</label>
		</div>
</body>

</html>
    <script type="text/javascript">
    	var type='<%=Order.TYPE_BUY_ORDER%>'
        mini.parse();
        var grid = mini.get("datagrid");
		var priceObj = mini.get("price");
		var goodsIdobj = mini.get("goodsId")
		var operateType="add";
		var countPriceObj = document.getElementById("countPrice");
		var countNumberObj = document.getElementById("countNumber");
		grid.load({type:type});
		
		window.confirm=function (text,title,callback){
			mini.confirm(text, title,callback);
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
                      $("#goods_buyPrice").html(data[0].buyPrice);
                      $("#goods_dose").html(data[0].dose);
                      $("#goods_number").html(data[0].number?data[0].number:0);
                      mini.get("code").setValue(code);
                      priceObj.setValue(data[0].buyPrice);
                      goodsIdobj.setValue(data[0].id);
                      addGoods();
                      //mini.get("price").focus();
				}else{
					chooseGoods(mini.get("goods"),code);
				}
			});
		}
		
		 function showCenter(type) {
	        var win = mini.get("win1");
	        if(type=="edit"){
	           operateType="edit";
	      	   win.setTitle("编辑进货单商品");
	        }
	        if(type=="add"){
	        	operateType="add";
	        	win.setTitle("添加进货单商品");
	        }
	        win.show();
	    }

		 
	    
	    function addOrder(e){
	   		var form = new mini.Form("#form2");
            form.validate();
            if (!form.isValid()){return};
            var oldnumber=countNumberObj.innerHTML;
            var data = form.getData();
			var json = mini.encode(data);
			if(grid.totalCount==0){
				mini.alert("请添加商品");
				return;
			}
			 var messageid = mini.loading("正在提交订单,请稍候...", "保存中");
			 doAjax("<%=basePath%>order_add.do",{json:json,type:type,t:new Date().getTime()},"POST",function(d, textStatus) {
				 mini.hideMessageBox(messageid);
				 mini.alert(d);
				 grid.reload();
				 resetText();
				});
			form.reset();
	    }

	    grid.on("load", function (e) {
	    	   initNumber();
	       });
	    function sbmitForm(){
	    	if(operateType=="add"){
	    		addGoods();
	    	}
	    	if(operateType=="edit"){
	    		 editGoods();
	    	}
	    }
	    
		function editGoods(){
		 	var form = new mini.Form("#form");
            form.validate();
            if (!form.isValid()){return};
            var data = form.getData();
			var json = mini.encode(data);
			doAjax("<%=basePath%>order_goods_edit_session.do",{json:json,type:type,t:new Date().getTime()},"POST",function(data, textStatus) {
			 mini.alert(data);
			 grid.reload();
			});
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
            if (!form.isValid()){mini.get("number").focus(); return};
            if(goodsIdobj.value==0){mini.alert("请选择商品");return;}
            var data = form.getData();
			var json = mini.encode(data);
			doAjax("<%=basePath%>order_goods_add_session.do",{json:json,type:type,t:new Date().getTime()},"POST",function(d, textStatus) {
			 mini.get("code").focus();
			 grid.reload();
			});
			resetForm();
		}

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
	       	 mini.get("pay").setValue(countPrice.toFixed(2));
		}
		
		function resetForm(){
			var goodsObj = mini.get("goods");
			 goodsObj.setValue(0);
			 mini.get("number").setValue(0);
             goodsObj.setText("");
             document.getElementById("goods_id").innerHTML="";
             document.getElementById("goods_norms").innerHTML="";
             document.getElementById("goods_unit").innerHTML="";
             document.getElementById("goods_buyPrice").innerHTML="";
             document.getElementById("goods_number").innerHTML="";
             priceObj.setValue(0);
             goodsIdobj.setValue(0);
		}
		
		function resetText(){
			 countPriceObj.innerHTML=0;
			 countNumberObj.innerHTML=0;
		}
		
		function chooseGoods(e,code) {
            var btnEdit = e;
            mini.open({
                url: "<%=basePath%>jsp/goods/choose_goods.jsp?code="+(code?code:""),
                title: "选择列表",
                width: '70%',
                height: '70%',
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
                            document.getElementById("goods_buyPrice").innerHTML=data.buyPrice;
                            $("#goods_dose").html(data.dose);
                            document.getElementById("goods_number").innerHTML=(data.number?data.number:0);
                            mini.get("code").setValue(data.code);
                            priceObj.setValue(data.buyPrice);
                            goodsIdobj.setValue(data.id);
                        }
                    }
                    mini.get("number").focus();
                }
            });            
		}
		grid.on("drawcell", function (e) {
			var record = e.record,
	        column = e.column,
	        field = e.field,
	        value = e.value;
	        if (field == "countPrice") {
	        	//mini.alert(value);
	        }
       });
       
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
	setTimeout(function(){showCenter("add");},200);
    </script>

