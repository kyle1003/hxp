<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>种子软件项目管理系统</title>
	<style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			background-image: url(/resources/images/left.gif);
		}
		-->
	</style>
	<link href="/resources/css/css.css" rel="stylesheet" type="text/css" />
</head>
<SCRIPT language=JavaScript>
	function tupian(idt){
		var nametu="xiaotu"+idt;
		var tp = document.getElementById(nametu);
		tp.src="/resources/images/ico05.gif";//图片ico04为白色的正方形

		for(var i=1;i<30;i++)
		{

			var nametu2="xiaotu"+i;
			if(i!=idt*1)
			{
				var tp2=document.getElementById('xiaotu'+i);
				if(tp2!=undefined)
				{tp2.src="/resources/images/ico06.gif";}//图片ico06为蓝色的正方形
			}
		}
	}

	function list(idstr){
		var name1="subtree"+idstr;
		var name2="img"+idstr;
		var objectobj=document.all(name1);
		var imgobj=document.all(name2);


		//alert(imgobj);

		if(objectobj.style.display=="none"){
			for(i=1;i<10;i++){
				var name3="img"+i;
				var name="subtree"+i;
				var o=document.all(name);
				if(o!=undefined){
					o.style.display="none";
					var image=document.all(name3);
					//alert(image);
					image.src="/resources/images/ico04.gif";
				}
			}
			objectobj.style.display="";
			imgobj.src="/resources/images/ico03.gif";
		}
		else{
			objectobj.style.display="none";
			imgobj.src="/resources/images/ico04.gif";
		}
	}

</SCRIPT>

<body>
<table width="198" border="0" cellpadding="0" cellspacing="0" class="left-table01">
	<tr>
		<TD>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="207" height="55" background="/resources/images/nav01.gif">
						<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="25%" rowspan="2"><img src="/resources/images/ico02.gif" width="35" height="35" /></td>
								<td width="75%" height="22" class="left-font01">您好，<span class="left-font02">king</span></td>
							</tr>
							<tr>
								<td height="22" class="left-font01">
									[&nbsp;<a href="/resources/images/index.jsp" target="_top" class="left-font01">退出</a>&nbsp;]</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>



			<!--  任务系统开始    -->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29">
						<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="8%"><img name="img8" id="img8" src="/resources/images/ico04.gif" width="8" height="11" /></td>
								<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('8');" >任务管理</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</TABLE>
			<table id="subtree8" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0"
				   cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="20" ><img id="xiaotu20" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/addrenwu.ftl" target="mainFrame" class="left-font03" onClick="tupian('20');">创建任务</a></td>
				</tr>
				<tr>
					<td width="9%" height="21" ><img id="xiaotu21" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listrenwu.htm" target="mainFrame" class="left-font03" onClick="tupian('21');">任务信息查看</a></td>
				</tr>
			</table>
			<!--  任务系统结束    -->



			<!--  消息系统开始    -->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29">
						<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="8%"><img name="img7" id="img7" src="/resources/images/ico04.gif" width="8" height="11" /></td>
								<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('7');" >消息管理</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</TABLE>
			<table id="subtree7" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0"
				   cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="20" ><img id="xiaotu17" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%">
						<a href="/resources/sendxiaoxi.htm" target="mainFrame" class="left-font03" onClick="tupian('17');">发送信息</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu18" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%">
						<a href="/resources/listtakexiaoxi.htm" target="mainFrame" class="left-font03" onClick="tupian('18');">收件信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu19" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%">
						<a href="/resources/listsendxiaoxi.htm" target="mainFrame" class="left-font03" onClick="tupian('19');">发件信息查看
						</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu24" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%">
						<a href="/resources/listxiaoxi.htm" target="mainFrame" class="left-font03" onClick="tupian('24');">消息信息列表
						</a></td>
				</tr>
			</table>
			<!--  消息系统结束    -->



			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29">
						<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="8%"><img name="img1" id="img1" src="/resources/images/ico04.gif" width="8" height="11" /></td>
								<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('1');" >项目系统</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</TABLE>
			<table id="subtree1" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0"
				   cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="20" ><img id="xiaotu1" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listxiangmuxinxi.htm" target="mainFrame" class="left-font03" onClick="tupian('1');">项目基本信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu4" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listjihua.htm" target="mainFrame" class="left-font03" onClick="tupian('4');">项目计划信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu2" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listxuqiu.htm" target="mainFrame" class="left-font03" onClick="tupian('2');">项目需求信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu5" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listmokuai.htm" target="mainFrame" class="left-font03" onClick="tupian('5');">项目模块信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu3" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listgongneng.htm" target="mainFrame" class="left-font03" onClick="tupian('3');">项目功能信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu6" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listshangchuan.htm" target="mainFrame" class="left-font03" onClick="tupian('6');">项目上传信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu7" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listchengbenxinxi.htm" target="mainFrame" class="left-font03" onClick="tupian('7');">项目成本信息查看</a></td>
				</tr>
			</table>
			<!--  项目系统结束    -->

			<!--  客户系统开始    -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%" height="12"><img name="img2" id="img2" src="/resources/images/ico04.gif" width="8" height="11" /></td>
							<td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('2');" >客户系统</a></td>
						</tr>
					</table></td>
				</tr>
			</table>

			<table id="subtree2" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">

				<tr>
					<td width="9%" height="20" ><img id="xiaotu7" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listkehuxinxi.html" target="mainFrame" class="left-font03" onClick="tupian('7');">客户信息查看</a></td>
				</tr>
			</table>

			<!--  客户系统结束    -->

			<!--  人员系统开始    -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%" height="12"><img name="img3" id="img3" src="/resources/images/ico04.gif" width="8" height="11" /></td>
							<td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('3');" >人员系统</a></td>
						</tr>
					</table></td>
				</tr>
			</table>

			<table id="subtree3" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="20" ><img id="xiaotu8" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listyuangong.html" target="mainFrame" class="left-font03" onClick="tupian('8');">人员信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu9" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listzhiwu.htm" target="mainFrame" class="left-font03" onClick="tupian('9');">职务信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu10" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listMonthYuanGongGongZi.htm" target="mainFrame" class="left-font03" onClick="tupian('10');">员工工作情况查看</a></td>
				</tr>
			</table>

			<!--  人员系统结束    -->

			<!--  考勤系统开始    -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%" height="12"><img name="img4" id="img4" src="/resources/images/ico04.gif" width="8" height="11" /></td>
							<td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('4');" >考勤系统</a></td>
						</tr>
					</table></td>
				</tr>
			</table>

			<table id="subtree4" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="20" ><img id="xiaotu11" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listGongZiType.htm" target="mainFrame" class="left-font03" onClick="tupian('11');">考勤类型查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="20" ><img id="xiaotu12" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listMonthYuanGongGongZi.htm" target="mainFrame" class="left-font03" onClick="tupian('12');">员工考勤信息查看</a></td>
				</tr>
			</table>

			<!--  考勤系统结束    -->

			<!-- 管理系统开始 -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img5" id="img5" src="/resources/images/ico04.gif" width="8" height="11" /></td>
							<td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('5');">系统管理</a></td>
						</tr>
					</table></td>
				</tr>
			</table>

			<table id="subtree5" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="20"><img id="xiaotu13" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/register.html" target="mainFrame" class="left-font03" onClick="tupian('13');">添加用户</a></td>
				</tr>
				<tr>
					<td height="20"><img id="xiaotu14" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td><a href="addquanxian.html" target="mainFrame" class="left-font03" onClick="tupian('14');">添加角色</a></td>
				</tr>
			</table>
			<!-- 管理系统结束-->


			<!-- 系统帮助开始 -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="8%"><img name="img6" id="img6" src="/resources/images/ico04.gif" width="8" height="11" /></td>
							<td width="92%"><a href="/resources/mode.html" target="mainFrame" class="left-font03" onClick="list('6');">系统帮助</a></td>
						</tr>
					</table></td>
				</tr>
			</table>
			<table id="subtree6" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="20"><img id="xiaotu15" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/mode.html" target="mainFrame" class="left-font03" onClick="tupian('15');">用户手册</a></td>
				</tr>
				<tr>
					<td height="20"><img id="xiaotu16" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td><a href="/resources/mode.html" target="mainFrame" class="left-font03" onClick="tupian('16');">规章制度</a></td>
				</tr>
			</table>
			<!-- 系统帮助结束-->

			<!--个人信息管理开始-->

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
				<tr>
					<td height="29">
						<table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="8%"><img name="img9" id="img9" src="/resources/images/ico04.gif" width="8" height="11" /></td>
								<td width="92%">
									<a href="javascript:" target="mainFrame" class="left-font03" onClick="list('9');" >个人管理</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</TABLE>

			<table id="subtree9" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0"
				   cellspacing="0" class="left-table02">
				<tr>
					<td width="9%" height="22" ><img id="xiaotu22" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="listgerenxinxi.htm" target="mainFrame" class="left-font03"
									   onClick="tupian('22');">个人信息查看</a></td>
				</tr>
				<tr>
					<td width="9%" height="23" ><img id="xiaotu23" src="/resources/images/ico06.gif" width="8" height="12" /></td>
					<td width="91%"><a href="/resources/listgerenrenwu.htm" target="mainFrame" class="left-font03"
                                       onClick="tupian('23');">任务信息查看</a></td>
				</tr>
			</table>
			<!--  个人信息结束    -->

		</TD>
	</tr>

</table>
</body>
</html>
