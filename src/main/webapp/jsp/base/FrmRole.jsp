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
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var grid;
	var addFun = function() {
		var dialog = parent.frm.modalDialog({
			title : '添加角色信息',
			url : frm.contextPath + '/jsp/base/FrmRoleForm.jsp',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var showFun = function(id) {
		var dialog = parent.frm.modalDialog({
			title : '查看角色信息',
			url : frm.contextPath + '/jsp/base/FrmRoleForm.jsp?id=' + id
		});
	};
	var editFun = function(id) {
		var dialog = parent.frm.modalDialog({
			title : '编辑角色信息',
			url : frm.contextPath + '/jsp/base/FrmRoleForm.jsp?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(frm.contextPath + '/base/frmRole!delete.do', {
					id : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	var grantFun = function(id) {
		var dialog = parent.frm.modalDialog({
			title : '角色授权',
			url : frm.contextPath + '/jsp/base/FrmRoleGrant.jsp?id=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/base/frmRole!grid.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'seq',
			sortOrder : 'asc',
			frozenColumns : [ [ {
				width : '100',
				title : '角色名称',
				field : 'name',
				sortable : true
			} ] ],
			columns : [ [ {
				width : '150',
				title : '创建时间',
				field : 'createDatetime ',
				sortable : true
			}, {
				width : '150',
				title : '修改时间',
				field : 'updateDatetime',
				sortable : true
			}, {
				width : '300',
				title : '资源描述',
				field : 'description'
			}, {
				width : '60',
				title : '排序',
				field : 'seq',
				hidden : true,
				sortable : true
			}, {
				title : '操作',
				field : 'action',
				width : '80',
				formatter : function(value, row) {
					var str = '';
					<%if (securityUtil.havePermission("/base/frmRole!getById")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/frmRole!update")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-note_edit" title="修改" onclick="editFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/frmRole!grant")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-key" title="授权" onclick="grantFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/frmRole!delete")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.id);
					<%}%>
					return str;
				}
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
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<%if (securityUtil.havePermission("/base/frmRole!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
				<%}%>
				<td><div class="datagrid-btn-separator"></div></td>
				<td><input id="searchBox" class="easyui-searchbox" style="width: 150px" data-options="searcher:function(value,name){grid.datagrid('load',{'QUERY_t#name_S_LK':value});},prompt:'搜索角色名称'"></input></td>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchBox').searchbox('setValue','');grid.datagrid('load',{});">清空查询</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>