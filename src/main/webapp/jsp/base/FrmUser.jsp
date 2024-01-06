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
			title : '添加用户信息',
			url : frm.contextPath + '/jsp/base/FrmUserForm.jsp',
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
			title : '查看用户信息',
			url : frm.contextPath + '/jsp/base/FrmUserForm.jsp?id=' + id
		});
	};
	var editFun = function(id) {
		if(id == "60366688-0001-0001-0011-888888user01"){
			parent.$.messager.alert("提示信息","您没有权限操作超级管理员!");
		}else{
			var dialog = parent.frm.modalDialog({
				title : '编辑用户信息',
				url : frm.contextPath + '/jsp/base/FrmUserForm.jsp?id=' + id,
				buttons : [ {
					text : '修改',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});
		}
	};
	var removeFun = function(id) {
		if(id == "60366688-0001-0001-0011-888888user01"){
			parent.$.messager.alert("提示信息","您没有权限操作超级管理员!");
		}else{
			parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
					$.post(frm.contextPath + '/base/frmUser!delete.do', {
						id : id
					}, function() {
						grid.datagrid('reload');
					}, 'json');
				}
			});
		}
	};
	var grantRoleFun = function(id) {
		if(id == "60366688-0001-0001-0011-888888user01"){
			parent.$.messager.alert("提示信息","您没有权限操作超级管理员!");
		}else{
			var dialog = parent.frm.modalDialog({
				title : '修改角色',
				url : frm.contextPath + '/jsp/base/FrmUserRoleGrant.jsp?id=' + id,
				buttons : [ {
					text : '修改',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});
		}
	};
	var grantOrganizationFun = function(id) {
		if(id == "60366688-0001-0001-0011-888888user01"){
			parent.$.messager.alert("提示信息","您没有权限操作超级管理员!");
		}else{
			var dialog = parent.frm.modalDialog({
				title : '修改机构',
				url : frm.contextPath + '/jsp/base/FrmUserOrganizationGrant.jsp?id=' + id,
				buttons : [ {
					text : '修改',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});
		}
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/base/frmUser!grid.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createDatetime ',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '100',
				title : '登录名',
				field : 'loginName',
				sortable : true
			}, {
				width : '80',
				title : '姓名',
				field : 'name',
				sortable : true
			} ] ],
			columns : [ [{
				width : '50',
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
			}, {
				width : '50',
				title : '年龄',
				field : 'age',
				sortable : true
			}, {
				width : '80',
				title : '组织编码',
				field : 'orgCode',
				sortable : true
			}, {
				width : '50',
				title : '所属组织ID',
				field : 'frmOrganization.id',
				hidden : true
			}, {
				width : '200',
				title : '所属组织名称',
				field : 'frmOrganization.name',
				sortable : true
			}, {
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
				title : '操作',
				field : 'action',
				width : '90',
				formatter : function(value, row) {
					var str = '';
					<%if (securityUtil.havePermission("/base/frmUser!getById")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/frmUser!update")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-note_edit" title="修改" onclick="editFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/frmUser!grantRole")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-user" title="用户角色" onclick="grantRoleFun(\'{0}\');"/>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/frmUser!grantOrganization")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-group" title="用户机构" onclick="grantOrganizationFun(\'{0}\');"/>', row.id);
					<%}%>					
					<%if (securityUtil.havePermission("/base/frmUser!delete")) {%>
						str += frm.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.id);
					<%}%>
					return str;
				}
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
			},  ] ],
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
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>登录名</td>
								<td><input name="QUERY_t#loginName_S_LK" style="width: 80px;" /></td>
								<td>姓名</td>
								<td><input name="QUERY_t#name_S_LK" style="width: 80px;" /></td>
								<td>性别</td>
								<td><select name="QUERY_t#sex_S_EQ" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"><option value="">请选择</option>
										<option value="1">男</option>
										<option value="0">女</option></select></td>
								<td>创建时间</td>
								<td><input name="QUERY_t#createDatetime _D_GE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" />-<input name="QUERY_t#createDatetime _D_LE" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',frm.serializeObject($('#searchForm')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<%if (securityUtil.havePermission("/base/frmUser!save")) {%>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<%}%>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_add',plain:true" onclick="">导入</a></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-table_go',plain:true" onclick="">导出</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>