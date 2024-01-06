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
	var obtainCollectionses = function() {
		if ($('form').form('validate')) {
			var url = frm.contextPath + '/app/hpn/spotData!findCollectionses.do';
			$.post(url, frm.serializeObject($('form')), function(result) {
				if(result.collectionses.length>0) {
					showPopGrid(result.collectionses);
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	}
	
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(frm.contextPath + '/hpn/nv/spotData!getById.do', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.macCode' : result.macCode,
						'data.latitude' : result.latitude,
						'data.longitude' : result.longitude,
						'data.azimuth' : result.azimuth
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
			<legend>定位信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>MAC码</th>
					<td><input name="data.macCode" value="08:00:20:0A:8C:6D" class="easyui-validatebox" data-options="required:true" /></td>
					<th>方位角</th>
					<td><input name="data.azimuth" value="0"/></td>	
				</tr>
				<tr>
					<th>经度</th>
					<td><input name="data.longitude" value="112.520855"/></td>
					<th>纬度</th>
					<td><input name="data.latitude" value="-0.008065"/></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<div class="rightButtons" style="text-align:center">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'hpn-icon-spotNavi',plain:true" onclick="obtainCollectionses()">导览</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div class="gridTable">
		<table id="popGrid"></table>
	</div>
</body>
</html>