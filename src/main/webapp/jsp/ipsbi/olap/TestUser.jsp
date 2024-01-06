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
			url : frm.contextPath + '/jsp/ipsbi/olap/TestUserForm.jsp',
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
			url : frm.contextPath + '/jsp/ipsbi/olap/TestUserForm.jsp?id=' + rowId
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : frm.contextPath + '/ipsbi/olap/testUser!grid.do',
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
				width : '180',
				title : '排名',
				field : 'paiming',
				sortable : true
			}  ] ],
			columns : [ [ {
				width : '140',
				title : '楼层',
				field : 'louceng',
				sortable : true
				
			},
			{
				width : '220',
				title : '客流量',
				field : 'keliu',
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
			排名：<input name="QUERY_t#paiming_S_EQ" style="width: 80px;" />
			楼层：<input name="QUERY_t#louceng_S_EQ" style="width: 198px;" />
		  </div>
		  <div class="searchDiv3">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',frm.serializeObject($('#searchForm')));">过滤</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置</a>
		  </div>
	</form>
	<div id="toolbar" >
		<table>
			<tr>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">查看报表</a></td>
			<%if (securityUtil.havePermission("/ipsbi/olap/testUser!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
			<%}%>
			<%if (securityUtil.havePermission("/ipsbi/olap/testUser!getById")) {%>
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