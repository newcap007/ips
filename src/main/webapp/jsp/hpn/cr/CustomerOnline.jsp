<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="zone.framework.util.base.SecurityUtil"%>
<%
	String contextPath = request.getContextPath();
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var grid;
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/hpn/cr/customerOnline!grid.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'loginDatetime ',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 5, 10,50, 100, 500 ],
			frozenColumns : [ [ {
				width : '80',
				title : '用户名',
				field : 'customer.number',
				sortable : true
			},{
				width : '80',
				title : '姓名',
				field : 'customer.name',
				sortable : true
			}] ],
			columns : [ [ {
				width : '100',
				title : 'MAC码',
				field : 'macCode'
			}, {
				width : '100',
				title : '状态',
				field : 'status',
				formatter : function(value, row, index) {
					switch (value) {
					case '0':
						return '在线';
					case '1':
						return '正常退出';
					case '2':
						return '异常退出';
					}
				}
			}, {
				width : '150',
				title : '登录时间',
				field : 'loginDatetime ',
				sortable : true
			}, {
				width : '150',
				title : '退出时间',
				field : 'logoutDatetime',
				sortable : true
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', frm.pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<form id="searchForm" class="searchForm">
		  <div class="searchDiv1">
			姓名：<input name="QUERY_t#name_S_LK" style="width: 80px;" />
			证件号码：<input name="QUERY_t#idNumber_S_LK" style="width: 198px;" />
		  </div>
		  <div class="searchDiv2">
			性别：<select name="QUERY_t#sex_S_EQ" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width: 84px;" >
						<option value="">请选择</option>
						<option value="1">男</option>
						<option value="0">女</option>
					</select>
			出生日期：<input name="QUERY_t#birthday_D_GE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" style="width: 90px;" />
					-
					<input name="QUERY_t#birthday_D_LE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" style="width: 90px;" />
		  </div>
		  <div class="searchDiv3">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',frm.serializeObject($('#searchForm')));">过滤</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置</a>
		  </div>
	</form>
	<div id="toolbar" >
		<table>
			<tr>
			<%if (securityUtil.havePermission("/hpn/cr/customerOnline!login")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="loginFun();">登录</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/cr/customerOnline!logout")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="logoutFun();">退出</a></td>
			<%}%>
			</tr>
		</table>
	</div>
	<div class="gridTable">
		<table id="grid"></table>
	</div>
</body>
</html>