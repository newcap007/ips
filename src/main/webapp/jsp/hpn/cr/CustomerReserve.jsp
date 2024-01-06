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
			title : '添加游客预约信息',
			width : 640,
			height : 480,
			url : frm.contextPath + '/jsp/hpn/cr/CustomerReserveForm.jsp',
			buttons : [ {
				text : '增加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.closeForm(dialog, grid, parent.$);
				}
			} ]
		});
	};

	var updateFun = function(id) {
		var dialog = parent.frm.modalDialog({
			title : '修改游客预约信息',
			url : frm.contextPath + '/jsp/base/CustomerReserveForm.jsp?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var deleteFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(frm.contextPath + '/hpn/cr/customerReserve!delete.do', {
					id : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/hpn/cr/customerReserve!grid.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'reserveDate',
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
			columns : [ [{
				width : '100',
				title : '状态',
				field : 'reserveStatus',
				formatter : function(value, row, index) {
					switch (value) {
					case '1':
						return '已保存';
					case '3':
						return '审批中';
					case '5':
						return '已拒绝';
					case '6':
						return '审批通过';
					}
				}
			}, {
				width : '150',
				title : '预约人数',
				field : 'reserveNum',
				sortable : true
			}, {
				width : '150',
				title : '预约日期',
				field : 'reserveDate',
				sortable : true
			}, {
				width : '350',
				title : '备注',
				field : 'remarks',
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
			状态：<select name="QUERY_t#status_S_EQ" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"  style="width: 84px;" >
						<option value="">请选择</option>
						<option value="1">已保存</option>
						<option value="3">审批中</option>
						<option value="5">已拒绝</option>
						<option value="6">审批通过</option>
					</select>
			预约日：<input name="QUERY_t#reserveDay_D_GE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" style="width: 90px;" />
					-
					<input name="QUERY_t#reserveDay_D_LE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" style="width: 90px;" />
		  </div>
		  <div class="searchDiv3">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',frm.serializeObject($('#searchForm')));">过滤</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置</a>
		  </div>
	</form>
	<div id="toolbar" >
		<table>
			<tr>
			<%if (securityUtil.havePermission("/hpn/cr/customerReserve!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">增加</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/cr/customerReserve!update")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="updateFun();">修改</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/cr/customerReserve!delete")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="deleteFun();">删除</a></td>
			<%}%>
			</tr>
		</table>
	</div>
	<div class="gridTable">
		<table id="grid"></table>
	</div>
</body>
</html>