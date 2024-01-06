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
<script src="<%=contextPath%>/js/hpnCommon.js?" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var closeForm = function($dialog, $grid, $pjq) {
		$dialog.dialog('destroy');
	};
	
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = frm.contextPath + '/hpn/cr/customerReserve!update.do';
			} else {
				url = frm.contextPath + '/hpn/cr/customerReserve!save.do';
			}
			$.post(url, frm.serializeObject($('form')), function(result) {
				parent.frm.progressBar('close');//关闭上传进度条

				if (result.success) {
					$pjq.messager.alert('提示', result.msg, 'info');
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
			$.post(frm.contextPath + '/hpn/cr/customerReserve!getById.do', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.customer.number' : result.customer.number,
						'data.customer.name' : result.customer.name,
						'data.status' : result.status,
						'data.reserveDate' : result.reserveDate
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
	
	var pickupCustomerFun = function(id) {
		var dialog = parent.frm.modalDialog({
			title : '选择机构',
			url : frm.contextPath + '/jsp/hpn/cr/Customer.jsp?',
			buttons : [ {
				text : '选择',
				handler : function() {
					var row = dialog.find('iframe').get(0).contentWindow.selectSingle();
					$('#customerId').val(row.id);
					$('#customerName').text(row.name);
					$('#customerNumber').val(row.number);
					dialog.dialog('close');
				}
			} ]
		});
	};
</script>
</head>
<body>
	<form id="form" method="post" class="form">
		<input name="data.id" value="<%=id%>" type="hidden" />
		<input name="data.operater" value="<%=operater%>" type="hidden" />
		<fieldset>
			<legend>游客预约信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>客户名</th>
					<td>
						<input id = "customerNumber" name="data.customer.number" readonly="readonly" />
						<a onclick="pickupCustomerFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'"></a>
					</td>
					<td colspan="2"><input id="customerId" name="data.customer.id" type="hidden"/><label id="customerName"></label></td>
					
				</tr>
				<tr>
					<th>预约日期</th>
					<td><input name="data.reserveDate" value=""/></td>
					<th>状态</th>
					<td><input name="data.reserveStatus" value=""/></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<!-- <div class="rightButtons" style="text-align:center">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'hpn-icon-spotNavi',plain:true" onclick="submitForm()">提交</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div> -->
</body>
</html>