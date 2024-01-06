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
				url = frm.contextPath + '/hpn/nv/collections!update.do';
			} else {
				url = frm.contextPath + '/hpn/nv/collections!save.do';
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
			var url = frm.contextPath + '/hpn/nv/collections!obtainPictures.do';
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
			$.post(frm.contextPath + '/hpn/nv/collections!getById.do', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
						'data.latitude' : result.latitude,
						'data.longitude' : result.longitude,
						'data.pictureUrl' : result.pictureUrl,
						'data.voiceUrl' : result.voiceUrl,
						'data.commentText' : result.commentText
					});
					$("#picture").attr("src",frm.contextPath+result.pictureUrl);
					$("#voice").attr("src",frm.contextPath+result.voiceUrl);
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
			<legend>获取藏品信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>名称</th>
					<td><input name="data.name" class="easyui-validatebox" data-options="required:true" /></td>
					<td rowspan="6"><img id="picture" src="" style="width: 150px; height: 150px;"></td>
				</tr>
				<tr>
					<th>纬度</th>
					<td><input name="data.latitude" /></td>
				</tr>				
				<tr>
					<th>经度</th>
					<td><input name="data.longitude" /></td>
				</tr>
				<tr>
					<th>图片</th>
					<td>
						<input name="data.pictureUrl"/>						
					</td>	
				</tr>
				<tr>
					<th>语音</th>
					<td><input name="data.voiceUrl" value="0"/></td>
				</tr>
				<tr>
					<th>播放</th>	
					<td>
						<audio controls style="width: 160px">
						  <source id="voice" src="" type="audio/mp3">
						</audio>
					</td>
				</tr>
				<tr>
					<th>文字</th>
					<td colspan="2"><textarea name="data.commentText" style="width: 98%; height:60px"/></textarea></td>	
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>