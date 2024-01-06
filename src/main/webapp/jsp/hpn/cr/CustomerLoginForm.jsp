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
			var url=frm.contextPath + '/hpn/cr/customer!login.do';
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
			$.post(frm.contextPath + '/hpn/cr/customer!getById.do', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				parent.$.messager.progress('close');
				if (result.id != undefined) {
					var birthday;
					if (result.birthday !== null && result.birthday !== undefined && result.birthday !== '') { 
						birthday = result.birthday.substring(0,10); 
					}
					$('form').form('load', {
						'data.id' : result.id,
						'data.number' : result.number,
						'data.name' : result.name
					});
				}
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
			<legend>客户登录</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>用户名</th>
					<td><input name="data.number" class="easyui-validatebox" data-options="required:true" maxlength="16"/></td>	
				</tr>
				<tr>
					<th>姓名</th>
					<td><input name="data.name"/></td>	
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="data.password" /></td>			
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>