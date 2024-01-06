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
<script type="text/javascript" src="../../../js/photoscript.js"></script>
<script type="text/javascript">
	var closeForm = function($dialog, $grid, $pjq) {
		$dialog.dialog('destroy');
	};

	var obtainCollections = function() {
		if ($('form').form('validate')) {
			var url = frm.contextPath + '/hpn/nv/shotNavi!findCollections.do';
			$.post(url, frm.serializeObject($('form')), function(result) {
				if(result.collectionses) {
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
			$.post(frm.contextPath + '/hpn/nv/shotNavi!getById.do', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.macCode' : result.macCode,
						'data.photoUrl' : result.photoUrl
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});	
	var takePhotoFun = function() {		
		var dialog = parent.frm.modalDialog({
			title : '拍照上传',
			url : frm.contextPath + '/jsp/aphotooth.jsp?',
			buttons : [ {
				text : '确定',		
				handler : function() {
					var img = dialog.find('iframe').get(0).contentWindow.selectSingle();					
					 $("#photo").attr("src",img);
					 Img("photo").Resize(300,300);
					 var imgData = $("#photo").attr("src");
					 $(':input[name="data.photo"]').attr("value",imgData);
					 dialog.dialog('close');
				}
			} ]
		})
	};

</script>
</head>
<body>
	<form id="form" method="post" class="form">
		<input name="data.id" value="<%=id%>" type="hidden" />
		<input name="data.operater" value="<%=operater%>" type="hidden" />
		<fieldset>
			<legend>拍照导览信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>MAC码</th>
					<td><input name="data.macCode" value="08:00:20:0A:8C:6D" class="easyui-validatebox" data-options="required:true" /></td>
					<td colspan="1" rowspan="3">					  
					  <img id="photo" src="" style="width: 90px; height: 90px;">
					</td>
				</tr>
				<tr>
					<th>照片</th>
					<td><input name="data.photo" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>照片上传</th>
					<td>
						<img class="iconImg ext-icon-webcam" title="拍照" onclick="takePhotoFun();"/>
						<input type="button" style="width:50px;" value="拍照" onclick="takePhotoFun();"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<div class="rightButtons" style="text-align:center">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'hpn-icon-spotNavi',plain:true" onclick="obtainCollections()">导览</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div class="gridTable">
		<table id="popGrid"></table>
	</div>
	<div class="uploadForm">
		
	</div>
</body>
</html>