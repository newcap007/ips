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
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = frm.contextPath + '/base/frmResource!update.do';
			} else {
				url = frm.contextPath + '/base/frmResource!save.do';
			}
			$.post(url, frm.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.treegrid('reload');
					$dialog.dialog('destroy');
					$mainMenu.tree('reload');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	var showIcons = function() {
		var dialog = parent.frm.modalDialog({
			title : '浏览小图标',
			url : frm.contextPath + '/style/icons.jsp',
			buttons : [ {
				text : '确定',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, $('#iconCls'));
				}
			} ]
		});
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(frm.contextPath + '/base/frmResource!getById.do', {
				id : $(':input[name="data.id"]').val(),
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
						'data.url' : result.url,
						'data.frmResourceType.id' : result.frmResourceType.id,
						'data.description' : result.description,
						'data.frmResource.id' : result.frmResource ? result.frmResource.id : '',
						'data.iconCls' : result.iconCls,
						'data.seq' : result.seq,
						'data.target' : result.target
					});
					$('#iconCls').attr('class', result.iconCls);//设置背景图标
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<input name="data.operater" value="<%=operater%>" type="hidden" />
		<fieldset>
			<legend>资源基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>编号</th>
					<td><input name="data.id" value="<%=id%>" readonly="readonly" /></td>
					<th>资源名称</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>资源路径</th>
					<td><input name="data.url" /></td>
					<th>资源类型</th>
					<td><select name="data.frmResourceType.id" class="easyui-combobox" data-options="required:true,editable:false,valueField:'id',textField:'name',url:'<%=contextPath%>/base/frmResourceType!doNotNeedSecurity_combobox.do',panelHeight:'auto'" style="width: 155px;"></select></td>
				</tr>
				<tr>
					<th>上级资源</th>
					<td><select id="syresource_id" name="data.frmResource.id" class="easyui-combotree" data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'<%=contextPath%>/base/frmResource!doNotNeedSecurity_getMainMenu.do'" style="width: 155px;"></select><img class="iconImg ext-icon-cross" onclick="$('#syresource_id').combotree('clear');" title="清空" /></td>
					<th>资源图标</th>
					<td><input id="iconCls" name="data.iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;" /><img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;<img class="iconImg ext-icon-cross" onclick="$('#iconCls').val('');$('#iconCls').attr('class','');" title="清空" /></td>
				</tr>
				<tr>
					<th>顺序</th>
					<td><input name="data.seq" class="easyui-numberspinner" data-options="required:true,min:0,max:100000,editable:false" style="width: 155px;" value="100" /></td>
					<th>目标</th>
					<td><input name="data.target" /></td>
				</tr>
				<tr>
					<th>资源描述</th>
					<td><textarea name="data.description"></textarea></td>
					<th></th>
					<td></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>