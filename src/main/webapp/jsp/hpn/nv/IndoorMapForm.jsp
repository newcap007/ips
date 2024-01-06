<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="zone.framework.model.base.SessionInfo"%>
<%@ page import="zone.framework.util.base.ConfigUtil"%>
<%
	String contextPath = request.getContextPath();
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
	String operater =sessionInfo.getUser().getLoginName();
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = frm.contextPath + '/hpn/nv/indoorMap!update.do';
			} else {
				url = frm.contextPath + '/hpn/nv/indoorMap!save.do';
			}
			$.post(url, frm.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	var obtainPictures = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url = frm.contextPath + '/hpn/nv/indoorMap!obtainPictures.do';
			$.post(url, frm.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(frm.contextPath + '/hpn/nv/indoorMap!getById.do', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.number' : result.number,
						'data.name' : result.name,
						'data.mapUrl' : result.mapUrl,
						'data.organization.id' : result.organization.id,
						'data.comment' : result.comment
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form id="form" method="post" class="form">
		<input name="data.id" value="<%=id%>" type="hidden" />
		<input name="data.operater" value="<%=operater%>" type="hidden" />
		<fieldset>
			<legend>地图信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>编码</th>
					<td><input name="data.number" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>名称</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>资源地址</th>
					<td><input name="data.mapUrl" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>				
				<tr>
					<th>组织</th>
					<td>
						<input id = "orgCode" name="data.orgCode" readonly="readonly" />
						<a onclick="pickupOrgFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'"></a>
						<input id="orgId" name="data.organization.id" type="hidden"/><label id="orgName"></label>
					</td>
				</tr>
				<tr>
					<th>说明</th>
					<td colspan="2"><textarea name="data.comment" style="width: 98%; height:60px"/></textarea></td>	
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>