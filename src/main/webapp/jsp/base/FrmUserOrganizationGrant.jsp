<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<%
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
	var submitForm = function($dialog, $grid, $pjq) {
		var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
		var ids = [];
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$.post(frm.contextPath + '/base/frmUser!grantOrganization.do', {
			id : $(':input[name="data.id"]').val(),
			ids : ids.join(',')
		}, function(result) {
			if (result.success) {
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
			$pjq.messager.alert('提示', '修改成功！', 'info');
		}, 'json');
	};
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$('#tree').tree({
			url : frm.contextPath + '/base/frmOrganization!doNotNeedSecurity_getFrmOrganizationsTree.do',
			parentField : 'pid',
			checkbox : true,
			cascadeCheck : false,
			formatter : function(node) {
				return node.name;
			},
			onLoadSuccess : function(node, data) {
				$.post(frm.contextPath + '/base/frmOrganization!doNotNeedSecurity_getFrmOrganizationByUserId.do', {
					id : $(':input[name="data.id"]').val()
				}, function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {
							var node = $('#tree').tree('find', result[i].id);
							if (node) {
								$('#tree').tree('check', node.target);
							}
						}
					}
					parent.$.messager.progress('close');
				}, 'json');
			}
		});
	});
	
	var selectSingle = function() {
		var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
		if(nodes.length>1){
			$.messager.alert('Info', "只能选择一条记录");
		}else{
			return nodes[0];
		}
	};
</script>
</head>
<body>
	<input name="data.id" value="<%=id%>" readonly="readonly" type="hidden" />
	<fieldset>
		<legend>所属机构</legend>
		<ul id="tree"></ul>
	</fieldset>
</body>
</html>