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
	var addFun = function() {
		var dialog = parent.frm.modalDialog({
			title : '添加用户信息',
			url : frm.contextPath + '/jsp/hpn/cr/CustomerForm.jsp',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var showFun = function(id) {
		var row = $('#grid').datagrid('getSelected');
		var rowId;
		if (row){
			rowId = row.id;
		}else{
			$.messager.alert('Info', "请选择一条记录");
			return;			
		}
		var dialog = parent.frm.modalDialog({
			title : '查看用户信息',
			url : frm.contextPath + '/jsp/hpn/cr/CustomerForm.jsp?id=' + rowId
		});
	};
	var editFun = function(id) {
		var row = $('#grid').datagrid('getSelected');
		var rowId;
		if (row){
			rowId = row.id;
		}else{
			$.messager.alert('Info', "请选择一条记录");
			return;			
		}
		var dialog = parent.frm.modalDialog({
			title : '编辑用户信息',
			url : frm.contextPath + '/jsp/hpn/cr/CustomerForm.jsp?id=' + rowId,
			buttons : [ {
				text : '更新',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		var row = $('#grid').datagrid('getSelected');
		var rowId;
		if (row){
			rowId = row.id;
		}else{
			$.messager.alert('Info', "请选择一条记录");
			return;			
		}
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
					$.post(frm.contextPath + '/hpn/cr/customer!delete.do', {
						id : rowId
					}, function() {
						grid.datagrid('reload');
					}, 'json');
				}
			});
	};
	
	var loginFun = function() {

		var row = $('#grid').datagrid('getSelected');
		var rowId;
		if (row){
			rowId = row.id;
		}else{
			$.messager.alert('Info', "请选择一条记录");
			return;			
		}
		var dialog = parent.frm.modalDialog({
			title : '编辑用户信息',
			url : frm.contextPath + '/jsp/hpn/cr/CustomerLoginForm.jsp?id=' + rowId,
			buttons : [ {
				text : '登录',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
		
	};

	var logoutFun = function(id) {
		var row = $('#grid').datagrid('getSelected');
		var rowId;
		if (row){
			rowId = row.id;
		}else{
			$.messager.alert('Info', "请选择一条记录");
			return;			
		}
		parent.$.messager.confirm('询问', '该用户确认退出吗？', function(r) {
			if (r) {
					$.post(frm.contextPath + '/hpn/cr/customer!logout.do', {
						id : rowId
					}, function() {
						grid.datagrid('reload');
					}, 'json');
				}
			});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/hpn/cr/customer!grid.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createDatetime ',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 5, 10,50, 100, 500 ],
			frozenColumns : [ [ {
				width : '80',
				title : '用户名',
				field : 'number',
				sortable : true
			},{
				width : '80',
				title : '姓名',
				field : 'name',
				sortable : true
			},
			{
				width : '120',
				title : '证件号码',
				field : 'idCode',
				sortable : true
			}  ] ],
			columns : [ [ {
				width : '100',
				title : '联系电话',
				field : 'phoneNumber'
			},{
				width : '100',
				title : '电子邮件',
				field : 'email'
			},{
				width : '40',
				title : '性别',
				field : 'sex',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case '0':
						return '女';
					case '1':
						return '男';
					}
				}
			},{
				width : '70',
				title : '出生日期',
				field : 'birthday',
				sortable : true
			}, {
				width : '100',
				title : '职业',
				field : 'occupation',
				sortable : true
			},  {
				width : '100',
				title : '备用电话',
				field : 'secondPhoneNumber'
			},  {
				width : '250',
				title : '照片',
				field : 'photo',
				formatter : function(value, row) {
					if(value){
						return frm.formatString('<span title="{0}">{1}</span>', value, value);
					}
				},
				hidden : true
			}, {
				width : '150',
				title : '创建时间',
				field : 'createDatetime ',
				sortable : true
			}, {
				width : '150',
				title : '修改时间',
				field : 'updateDatetime',
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
	
	var selectSingle = function() {
		var row = $('#grid').datagrid('getSelected');
		if (row){
			return row;
		}else{
			$.messager.alert('Info', "请选择一条记录");
		}
	};
	
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
			<%if (securityUtil.havePermission("/hpn/cr/customer!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/cr/customer!getById")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="showFun();">查看</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/cr/customer!update")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="editFun();">修改</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/cr/customer!delete")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="removeFun();">删除</a></td>
			<%}%>
			<td><div class="datagrid-btn-separator"></div></td>
			<%if (securityUtil.havePermission("/hpn/cr/customer!login")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="loginFun();">登录</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/cr/customer!logout")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="logoutFun();">退出</a></td>
			<%}%>
			<td><div class="datagrid-btn-separator"></div></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_add',plain:true" onclick="">导入</a></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_go',plain:true" onclick="">导出</a></td>
			</tr>
		</table>
	</div>
	<div class="gridTable">
		<table id="grid"></table>
	</div>
</body>
</html>