<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.hurry.po.user.User"%>
<%@page import="cn.hurry.util.JSON"%>
<%@page import="cn.hurry.po.order.Order"%>
<%@page import="cn.hurry.util.DateTimeUtils"%>
<%@page import="cn.hurry.po.goods.InvoicingInfo"%>
<%@page import="cn.hurry.service.goods.GoodsService"%>
<%@page import="cn.hurry.po.order.buy.BuyOrder"%>
<%@page import="cn.hurry.po.order.sell.SellOrderGoods"%>
<%@page import="cn.hurry.po.loss.Loss"%>
<%@page import="cn.hurry.po.order.OrderGoods"%>
<%@page import="cn.hurry.util.NumberUtil"%>
<%@page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User)request.getSession().getAttribute("user");
	if(user==null){
		request.getRequestDispatcher("login.jsp").forward(request,response);
	}
	Map<String, Object> map_ = new HashMap<String, Object>();
	map_.put("ids",request.getParameter("ids"));
	map_.put("name",request.getParameter("name"));
	map_.put("code",request.getParameter("code"));
	map_.put("buyCreateDateStart",request.getParameter("buyCreateDateStart"));
	map_.put("buyCreateDateEnd",request.getParameter("buyCreateDateEnd"));
	map_.put("sellCreateDateEnd",request.getParameter("sellCreateDateEnd"));
	map_.put("sellCreateDateStart",request.getParameter("sellCreateDateStart"));
	map_.put("lossCreateDateEnd",request.getParameter("lossCreateDateEnd"));
	map_.put("lossCreateDateStart",request.getParameter("lossCreateDateStart"));
	boolean show =Boolean.parseBoolean(request.getParameter("show"));
	List<InvoicingInfo> invoicingInfos =!show?new ArrayList<InvoicingInfo>():new GoodsService().selectInvoicingInfo(map_);//(List<InvoicingInfo>)request.getParameter("invoicingInfo");
	PrintWriter writer = response.getWriter();
	boolean showAll =Boolean.parseBoolean(request.getParameter("showAll"));
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
		<script type="text/javascript" src="<%=basePath%>plugins/miniui/jquery.jqprint-0.3.js"></script>
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
	    .header
	    {
	        background:url(<%=basePath%>resources/ht/images/header.gif) repeat-x 0 -1px;
	    }
	    
	    .topline{
	    	border-top: 1px solid;
	    }
	    .TLline{
		    border-top: 1px solid;
		    border-left:  1px solid;
	    }
	    .Lline{
		    border-left:  1px solid;
	    }
	    .rd{
	    	font-size: 18px;
	    	
	    }
	    .rda{
	    	font-size:15px;
	    	color: red;
	    }
	    .buy{
	    	color: #C7A417;
	    }
	     .sell{
	    	color: #30A913;
	    }
	     .loss{
	    	color: #3333FF;
	    }
	    table{
	    	font-size: 18px;
	    }
	    </style>
	</head>

<body>
	<div style="width: 80%;height:150px; margin: auto;text-align: center;">
		品&nbsp;&nbsp;项: <input id="lookup2" name="look" value="<%=request.getParameter("ids")==null?"":request.getParameter("ids")%>" text="<%=request.getParameter("names")==null?"":request.getParameter("names")%>"  class="mini-lookup" style="width:300px;" textField="name" valueField="id" popupWidth="auto" popup="#gridPanel" grid="#datagrid1" multiSelect="true" /><br/>
		进货日期:<input id="buyCreateDateStart" value="<%=request.getParameter("buyCreateDateStart")==null?"":request.getParameter("buyCreateDateStart")%>" style="width: 180px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',maxDate:buyCreateDateEnd.value,onpicked:function(dp){}})"  />
	        - <input id="buyCreateDateEnd" value="<%=request.getParameter("buyCreateDateEnd")==null?"":request.getParameter("buyCreateDateEnd")%>" style="width: 180px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd  23:59:59',minDate:buyCreateDateStart.value,onpicked:function(dp){}})"  />
		<br />
		销售日期:<input id="sellCreateDateStart" value="<%=request.getParameter("sellCreateDateStart")==null?"":request.getParameter("sellCreateDateStart")%>" style="width: 180px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',maxDate:sellCreateDateEnd.value,onpicked:function(dp){}})"  />
	        - <input id="sellCreateDateEnd" value="<%=request.getParameter("sellCreateDateEnd")==null?"":request.getParameter("sellCreateDateEnd")%>" style="width: 180px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd  23:59:59',minDate:sellCreateDateStart.value,onpicked:function(dp){}})"  />
		<br />
		损耗日期:<input id="lossCreateDateStart" value="<%=request.getParameter("lossCreateDateStart")==null?"":request.getParameter("lossCreateDateStart")%>" style="width: 180px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',maxDate:lossCreateDateEnd.value,onpicked:function(dp){}})"  />
	        - <input id="lossCreateDateEnd" value="<%=request.getParameter("lossCreateDateEnd")==null?"":request.getParameter("lossCreateDateEnd")%>" style="width: 180px" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd  23:59:59',minDate:lossCreateDateStart.value,onpicked:function(dp){}})"  />
		<br /><input type="button" value="明细" onclick="search('true')" /><input type="button" value="汇总" onclick="search('false')" />
		<input type="button" value="打印" onclick="$('#dataDiv').jqprint();" />
		<input id="combo1" class="mini-combobox" style="width:250px;" textField="text" valueField="id" multiSelect="true" emptyText="请选择要显示的列..."
          allowInput="true" value="buyNumber,buyPrice,sellNumber,sellPrice,lossNumber,lossPrice" onvaluechanged="changeCl"   data="comboboxData" /> 
	</div>
	<div style="width: 100%;margin: auto;" id="dataDiv">
		<table style="width: 100%;text-align: center; border: 1px solid;" cellpadding="0" cellspacing="0" >
			<tr>
				<td colspan="8" align="left" style="border-bottom: 1px solid;font-size: 12px;">
					&nbsp;&nbsp;时间:<%=DateTimeUtils.format(new Date(),"yyyy年MM月dd日")%><br />
					&nbsp;&nbsp;采购日期:<%=request.getParameter("buyCreateDateStart")==null?"不限":request.getParameter("buyCreateDateStart").substring(0,10)%>到<%=request.getParameter("buyCreateDateEnd")==null?"不限":request.getParameter("buyCreateDateEnd").substring(0,10)%>
					&nbsp;&nbsp;销售日期：<%=request.getParameter("sellCreateDateStart")==null?"不限":request.getParameter("sellCreateDateStart").substring(0,10)%>到<%=request.getParameter("sellCreateDateEnd")==null?"不限":request.getParameter("sellCreateDateEnd").substring(0,10)%>
					&nbsp;&nbsp;损耗日期：<%=request.getParameter("lossCreateDateStart")==null?"不限":request.getParameter("lossCreateDateStart").substring(0,10)%>到<%=request.getParameter("lossCreateDateEnd")==null?"不限":request.getParameter("lossCreateDateEnd").substring(0,10)%>
				</td>
			</tr>
			<tr>
				<td style="width: 12%;height: 30px;font-weight: 800" >名称</td>
				<td style="width: 15%; font-weight: 800;border-left:  1px solid black;<%=showAll?"":"display:none"%>">采购单编号</td>
				<td style="width: 10%; font-weight: 800;border-left:  1px solid;" name="buyNumber">采购数量</td>
				<td style="width: 10%; font-weight: 800;border-left:  1px solid;" name="buyPrice">采购总价</td>
				<td style="width: 10%; font-weight: 800;border-left:  1px solid;" name="sellNumber">销售数量</td>
				<td style="width: 10%; font-weight: 800;border-left:  1px solid;" name="sellPrice">销售总价</td>
				<td style="width: 10%; font-weight: 800;border-left:  1px solid;" name="lossNumber">损耗数量</td>
				<td style="width: 10%; font-weight: 800;border-left:  1px solid;" name="lossPrice">损耗总价</td>
			</tr>
			<%
			double _bnumber_ = 0, _bprice_ = 0;
			double _snumber_ = 0, _sprice_ = 0;
			double _lnumber_ = 0, _lprice_ = 0;
			int z=0;
			for (InvoicingInfo invoicingInfo : invoicingInfos) {
				Map<BuyOrder, Object[]> map = invoicingInfo.getBuyOrderBindOfSL();
				int i=0;
				
				double _bnumber = 0, _bprice = 0;
				double _snumber = 0, _sprice = 0;
				double _lnumber = 0, _lprice = 0;
				z++;
				for (BuyOrder key : map.keySet()) {
					Object[] obj = map.get(key);
					List<SellOrderGoods> sellOrderGoods = (List<SellOrderGoods>) obj[0];
					List<Loss> losses = (List<Loss>) obj[1];
					%>
					<tr onmouseover="this.style.backgroundColor='#77FFFF'" onmouseout="this.style.backgroundColor='<%=showAll&&z%2==0?"#E0F6FC":"#FEDEF8" %>'"  title="<%=invoicingInfo.getGoods().getName()%>(<%=invoicingInfo.getGoods().getMedicineName()%>)" style="background-color:<%=showAll&&z%2==0?"#E0F6FC":"#FEDEF8" %>">
					<% if(i==0){%>
					<td style="border-top: 1px solid;<%=showAll?"":"display:none"%>"   <%=i==0&&map.keySet().size()>1?"rowspan="+(map.keySet().size()):""%>><label title="<%=i==0?invoicingInfo.getGoods().getMedicineName():""%>"><%=i==0?invoicingInfo.getGoods().getName():""%></label></td>
					<%}%>
					<td style="border-top: 1px solid;font-size:13px; border-left:  1px solid;<%=showAll?"":"display:none"%>" ><label title="采购人:<%=key.getUser()!=null?key.getUser().getUsername():""%>"><%=key.getId()%></label></td>
					<%
					i++;
					double bnumber = 0, bprice = 0;
					double snumber = 0, sprice = 0;
					double lnumber = 0, lprice = 0;
					for (OrderGoods buyOrderGoods : key.getOrderGoods()) {
						if (buyOrderGoods.getGoodsId() == invoicingInfo.getGoods().getId()) {
							bnumber += buyOrderGoods.getNumber();
							bprice += buyOrderGoods.getNumber() * buyOrderGoods.getPrice();
							_bnumber+=buyOrderGoods.getNumber();
							_bprice+=buyOrderGoods.getNumber() * buyOrderGoods.getPrice();
							_bnumber_+=buyOrderGoods.getNumber();
							_bprice_+=buyOrderGoods.getNumber() * buyOrderGoods.getPrice();
						}
					}
					for (SellOrderGoods orderGoods : sellOrderGoods) {
						if (orderGoods.getGoodsId() == invoicingInfo.getGoods().getId()) {
							snumber += orderGoods.getNumber();
							sprice += orderGoods.getNumber() * orderGoods.getPrice();
							_snumber+=orderGoods.getNumber();
							_sprice+=orderGoods.getNumber() * orderGoods.getPrice();
							_snumber_+=orderGoods.getNumber();
							_sprice_+=orderGoods.getNumber() * orderGoods.getPrice();
						}
					}
					for (Loss loss : losses) {
						lnumber += loss.getNumber();
						lprice += loss.getOrderGoods().getPrice()* loss.getNumber();
						_lnumber+=loss.getNumber();
						_lprice+=loss.getOrderGoods().getPrice()* loss.getNumber();
						_lnumber_+=loss.getNumber();
						_lprice_+=loss.getOrderGoods().getPrice()* loss.getNumber();
					}
					%>
					<td style="border-top: 1px solid;border-left:  1px solid;<%=showAll?"":"display:none"%>" name="AllbuyNumber"><%=NumberUtil.convert(bnumber) %></td>
					<td style="border-top: 1px solid;border-left:  1px solid;<%=showAll?"":"display:none"%>" name="AllbuyPrice"><%=NumberUtil.convert(bprice) %></td>
					<td style="border-top: 1px solid;border-left:  1px solid;<%=showAll?"":"display:none"%>" name="AllsellNumber"><%=NumberUtil.convert(snumber) %></td>
					<td style="border-top: 1px solid;border-left:  1px solid;<%=showAll?"":"display:none"%>" name="AllsellPrice"><%=NumberUtil.convert(sprice) %></td>
					<td style="border-top: 1px solid;border-left:  1px solid;<%=showAll?"":"display:none"%>" name="AlllossNumber"><%=NumberUtil.convert(lnumber) %></td>
					<td style="border-top: 1px solid;border-left:  1px solid;<%=showAll?"":"display:none"%>" name="AlllossPrice"><%=NumberUtil.convert(lprice) %></td>
					<%
					}
					if(i>0){
					%>
					</tr>
					<tr onmouseover="this.style.backgroundColor='#77FFFF'" title="<%=invoicingInfo.getGoods().getName()%>(<%=invoicingInfo.getGoods().getMedicineName()%>) 小计" onmouseout="this.style.backgroundColor='<%=!showAll?"":z%2==0?"#E0F6FC":"#FEDEF8" %>'" style="background-color:<%=!showAll?"":z%2==0?"#E0F6FC":"#FEDEF8" %>">
						<td style="border-top: 1px solid;" <%=showAll?"colspan='2'":""%>><lable class="rd"><%=showAll?"小计":invoicingInfo.getGoods().getName()%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="buyNumber"><lable class="rd"><%=NumberUtil.convert(_bnumber)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="buyPrice"><lable class="rd"><%=NumberUtil.convert(_bprice)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="sellNumber"><lable class="rd"><%=NumberUtil.convert(_snumber)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="sellPrice"><lable class="rd"><%=NumberUtil.convert(_sprice)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="lossNumber"><lable class="rd"><%=NumberUtil.convert(_lnumber)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="lossPrice"><lable class="rd"><%=NumberUtil.convert(_lprice)%></lable></td>
					</tr>
					<%
					}
					}
					%>
					<tr style="background-color: #DFDFFD">
						<td style="border-top: 1px solid;height: 20px; " <%=showAll?"colspan='2'":""%>><lable class="rd">总计</lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="buyNumber"><lable class="buy"><%=NumberUtil.convert(_bnumber_)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="buyPrice"><lable class="buy"><%=NumberUtil.convert(_bprice_)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="sellNumber"><lable class="sell"><%=NumberUtil.convert(_snumber_)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="sellPrice"><lable class="sell"><%=NumberUtil.convert(_sprice_)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="lossNumber"><lable class="loss"><%=NumberUtil.convert(_lnumber_)%></lable></td>
						<td style="border-top: 1px solid;border-left:  1px solid;" name="lossPrice"><lable class="loss"><%=NumberUtil.convert(_lprice_)%></lable></td>
					</tr>
		</table>
		<table style="width: 100%;font-size: 18px;margin: auto;text-align: center;" cellpadding="0" cellspacing="0">
				<tr>
				<td>总成本:<%=NumberUtil.convert(_bprice_)%>&nbsp;&nbsp;
				剩余库存成本:<%=NumberUtil.convert(_bprice_-_sprice_-_lprice_)%>&nbsp;&nbsp;
				总损耗:<%=NumberUtil.convert(_lprice_)%>
				</td>
				</tr>
				<tr style="">
					<td>总销售:<%=NumberUtil.convert(_sprice_)%>&nbsp;&nbsp;
					盈利:<%=NumberUtil.convert(_sprice_-_lprice_)%>
					</td>
				</tr>
		</table>
	</div>
	
	<div id="gridPanel" class="mini-panel" title="header" iconCls="icon-add" style="width:450px;height:250px;" showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0" borderStyle="border:0" >
        <div property="toolbar" style="padding:5px;padding-left:8px;text-align:center;">   
            <div style="float:left;padding-bottom:2px;">
                <span>名称：</span>                
                <input id="keyText" class="mini-textbox" style="width:160px;" onenter="onSearchClick"/>
                <a class="mini-button" onclick="onSearchClick">查询</a>
                <a class="mini-button" onclick="onClearClick">清除</a>
            </div>
            <div style="float:right;padding-bottom:2px;">
                <a class="mini-button" onclick="onCloseClick">关闭</a>
            </div>
            <div style="clear:both;"></div>
        </div>
         <div id="datagrid1" class="mini-datagrid"  showPager="false" virtualScroll="true" style="width:100%;height:100%;"  url="<%= basePath %>goods_list_json.do"  idField="id" sizeList="[5,10,20,50]" pageSize="10" contextMenu="#gridMenu" headerContextMenu="#headerMenu">
            <div property="columns">
            	<div type="checkcolumn" ></div>
            	<div field="id" width="50" headerAlign="center" allowSort="false">编号</div> 
                <div field="code" width="80" headerAlign="center" allowSort="false">条码</div> 
                <div field="medicineName" width="100" headerAlign="center" allowSort="true">通用名</div>
                <div field="name" width="100" headerAlign="center" allowSort="true">药名</div>
                <div field="goodsType" width="80" headerAlign="center" allowSort="false">类别</div> 
                <div field="unit" width="100" headerAlign="center" allowSort="false">单位</div>
                <div field="norms" width="100" headerAlign="center" allowSort="false">规格型号</div>                              
          	 	<div field="sellPrice" width="100"  headerAlign="center" allowSort="true">预计售价</div>
          	 	<div field="buyPrice" width="100"  headerAlign="center" allowSort="true">预计进价</div>
          	 	<div field="number" width="100"  headerAlign="center" allowSort="true">库存</div>
          	 	<div field="remark" width="100"  headerAlign="center" allowSort="false">备注</div>
            </div>
        </div> 
    </div>
</body>

</html>
<script type="text/javascript">
	var comboboxData=[{id:"buyNumber",text:"采购数量"},
	              	{id:"buyPrice",text:"采购总价"},
	              	{id:"sellNumber",text:"销售数量"},
	              	{id:"sellPrice",text:"销售总价"},
	              	{id:"lossNumber",text:"损耗数量"},
	              	{id:"lossPrice",text:"损耗总价"}];
  	var showAll=<%=showAll%>;
    mini.parse();
    var grid = mini.get("datagrid1");
    var keyText = mini.get("keyText");
    grid.load({key:"haveBuyOrder=true"});
    var buyCreateDateStart = document.getElementById("buyCreateDateStart");
    var buyCreateDateEnd = document.getElementById("buyCreateDateEnd");

    var sellCreateDateStart = document.getElementById("sellCreateDateStart");
    var sellCreateDateEnd = document.getElementById("sellCreateDateEnd");

    var lossCreateDateStart = document.getElementById("lossCreateDateStart");
    var lossCreateDateEnd = document.getElementById("lossCreateDateEnd");
    
    function orderInfo(id){
		openWindow("<%=basePath%>order_info.do?id="+id,"单据明细",550,500)
    }

    function setStyle(name,dsp){
			var objs = document.getElementsByName(name);
			for(var i=0;i<objs.length;i++){
				objs[i].style.display=dsp;
				}
        }
    function changeCl(e){
			 var values=mini.get("combo1").value;
			 var varr = values.split(",");
			 for(var i=0;i<comboboxData.length;i++){
				 if(showAll)
					 setStyle("All"+comboboxData[i].id,"none");
				 setStyle(comboboxData[i].id,"none");
			 }
			 for(var i=0;i<varr.length;i++){
				 if(showAll)
					 setStyle("All"+varr[i],"");
				 setStyle(varr[i],"");
			}
       }

    function search(showAll){
		var k="?show=true&showAll="+showAll;
		var ids = mini.get("lookup2");
		if(ids.value!=""){
			k+="&ids="+ids.value;
			k+="&names="+ids.getText();
		}
		if(buyCreateDateStart.value!=""){
			k+="&buyCreateDateStart="+buyCreateDateStart.value;
		}
		if(buyCreateDateEnd.value!=""){
			k+="&buyCreateDateEnd="+buyCreateDateEnd.value;
		}
		if(sellCreateDateStart.value!=""){
			k+="&sellCreateDateStart="+sellCreateDateStart.value;
		}
		if(sellCreateDateEnd.value!=""){
			k+="&sellCreateDateEnd="+sellCreateDateEnd.value;
		}
		if(lossCreateDateStart.value!=""){
			k+="&lossCreateDateStart="+lossCreateDateStart.value;
		}
		if(lossCreateDateEnd.value!=""){
			k+="&lossCreateDateEnd="+lossCreateDateEnd.value;
		}
		window.location.href="<%=basePath%>jsp/count/invoicing_info.jsp"+k;
       }
    
    function onSearchClick(e) {
        grid.load({
            key: "haveBuyOrder=true,key="+keyText.value
        });
    }
    function onCloseClick(e) {
        var lookup2 = mini.get("lookup2");
        lookup2.hidePopup();
    }
    function onClearClick(e) {
        var lookup2 = mini.get("lookup2");
        lookup2.deselectAll();
    }

    var tip = new mini.ToolTip();
    tip.set({
        target: document,
        selector: '[data-tooltip], [title]'
    });
    
</script>

