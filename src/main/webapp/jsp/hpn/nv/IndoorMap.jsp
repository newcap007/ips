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
			title : '添加地图信息',
			url : frm.contextPath + '/jsp/hpn/nv/IndoorMapForm.jsp',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			},
			{
				text : '获取图片',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.findPictures(dialog, grid, parent.$);
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
			title : '查看地图信息',
			url : frm.contextPath + '/jsp/hpn/nv/IndoorMapForm.jsp?id=' + rowId
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
			title : '编辑地图信息',
			url : frm.contextPath + '/jsp/hpn/nv/IndoorMapForm.jsp?id=' + rowId,
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
					parent.$.messager.progress({text : '处理中....'});
					$.post(frm.contextPath + '/hpn/nv/indoorMap!delete.do', {
						id : rowId
					}, function() {
						parent.$.messager.progress('close');
						grid.datagrid('reload');
					}, 'json');
				}
			});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/hpn/nv/indoorMap!grid.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createDatetime ',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 5, 10,50, 100, 500 ],
			frozenColumns : [ [ 
				{
					width : '120',
					title : '地图编码',
					field : 'number',
					sortable : true
				},
				{
					width : '120',
					title : '地图名称',
					field : 'name',
					sortable : true
				}
			] ],
			columns : [ [
				{
					width : '220',
					title : '资源地址',
					field : 'mapUrl',
					sortable : true
				},
				{
					width : '120',
					title : '所属组织',
					field : 'organization.name',
					sortable : true
				},                   
				{
					width : '220',
					title : '说明',
					field : 'comment',
					sortable : true
				},{
					width : '150',
					title : '创建时间',
					field : 'createDatetime ',
					sortable : true
				}, {
					width : '150',
					title : '修改时间',
					field : 'updateDatetime',
					sortable : true
				}				                   
			 ] ],
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

    
	var importExecl = function() {
		var dialog = parent.frm.modalDialog({
			title : '导入地图数据',
			width : 320,
			height : 240,
			url : frm.contextPath + '/jsp/hpn/nv/IndoorMapUpload.jsp',
			buttons : [ {
				text : '导入',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, parseData);
				}
			} ]
		});
	};
	
	var parseData = function(files) {
		var url = frm.contextPath + '/hpn/nv/indoorMap!parseExcel.do';
		$.post(url, '{"files":'+files+'}', function(result) {
			parent.frm.progressBar('close');//关闭上传进度条

			if (result.success) {
				$.messager.alert('提示', result.msg, 'info');
				$grid.datagrid('load');
				$dialog.dialog('destroy');
			} else {
				$.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	}; 
	var exportExecl = function() {
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
			地图编码：<input name="QUERY_t#number_S_LK" style="width: 140px;" />
		  </div>
		  <div class="searchDiv2">
			地图名称：<input name="QUERY_t#name_S_LK" style="width: 140px;" />
		  </div>
		  <div class="searchDiv3">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',frm.serializeObject($('#searchForm')));">过滤</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置</a>
		  </div>
	</form>
	<div id="toolbar" >
		<table>
			<tr>
			<%if (securityUtil.havePermission("/hpn/nv/indoorMap!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/nv/indoorMap!update")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="editFun();">修改</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/nv/indoorMap!getById")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="showFun();">查看</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/hpn/nv/indoorMap!delete")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="removeFun();">删除</a></td>
			<%}%>
			<td><div class="datagrid-btn-separator"></div></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_add',plain:true" onclick="importExecl()">导入</a></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_go',plain:true" onclick="exportExecl()">导出</a></td>
			</tr>
		</table>
	</div>
	<div class="gridTable">
		<table id="grid"></table>
	</div>
</body>
</html>