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
			title : '添加定位导览信息',
			width : 640,
			height : 480,
			url : frm.contextPath + '/jsp/hpn/nv/SpotDataForm.jsp',
			buttons : [ {
				text : '关闭',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.closeForm(dialog, grid, parent.$);
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
			title : '查看定位导览信息',
			width : 640,
			height : 480,
			url : frm.contextPath + '/jsp/hpn/nv/SpotDataForm.jsp?id=' + rowId
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/hpn/nv/spotData!grid.do',
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
				width : '130',
				title : 'MAC码',
				field : 'macCode',
				sortable : true
			},
			{
				width : '130',
				title : '纬度',
				field : 'latitude',
				sortable : true
			},
			{
				width : '130',
				title : '经度',
				field : 'longitude',
				sortable : true
			},
			{
				width : '60',
				title : '方位角',
				field : 'azimuth',
				sortable : true
			} ] ],
			columns : [ [ {
				width : '150',
				title : '创建时间',
				field : 'createDatetime',
				sortable : true
			} , {
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
			经度：<input name="QUERY_t#longitude_S_LK" style="width: 140px;" />
		  </div>
		  <div class="searchDiv2">
			维度：<input name="QUERY_t#latitude_S_LK" style="width: 140px;" />
		  </div>
		  <div class="searchDiv3">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',frm.serializeObject($('#searchForm')));">过滤</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置</a>
		  </div>
	</form>
	<div id="toolbar" >
		<table>
			<tr>
			<%if (securityUtil.havePermission("/hpn/nv/spotData!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/nv/spotData!getById")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="showFun();">查看</a></td>
			<%}%>
			</tr>
		</table>
	</div>
	<div class="gridTable">
		<table id="grid"></table>
	</div>
</body>
</html>