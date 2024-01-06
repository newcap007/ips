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
				url = frm.contextPath + '/hpn/cr/customer!update.do';
			} else {
				url = frm.contextPath + '/hpn/cr/customer!save.do';
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
						'data.password' : '',
						'data.name' : result.name,
						'data.idCode' : result.idCode,
						'data.email' : result.email,
						'data.occupation' : result.occupation,
						'data.sex' : result.sex,
						'data.birthday' : birthday,
						'data.phoneNumber' : result.phoneNumber,
						'data.secondPhoneNumber' : result.secondPhoneNumber,
						'data.photo' : result.photo
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
			<legend>客户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>用户名</th>
					<td><input name="data.number" class="easyui-validatebox" data-options="required:true" maxlength="16"/></td>	
					<th>照片上传</th>
					<td><div id="container">
							<a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
						</div>
					</td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="data.password" class="easyui-validatebox" data-options="required:true" /></td>	
					<td colspan="2" rowspan="8">
					  <input name="data.photo" readonly="readonly" style="display: none;" />
					  <img id="photo" src="" style="width: 180px; height: 180px;">
					</td>
				</tr>
				<tr>
					<th>姓名</th>
					<td><input name="data.name"/></td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td><input name="data.phoneNumber" /></td>			
				</tr>	
				<tr>
					<th>证件号码</th>
					<td><input name="data.idCode" /></td>
				</tr>				
				<tr>
					<th>职业</th>
					<td><input name="data.occupation" /></td>
				</tr>
				<tr>
					<th>性别</th>
					<td><select class="easyui-combobox" name="data.sex" data-options="panelHeight:'auto',editable:false">
							<option value="1">男</option>
							<option value="0">女</option>
					</select></td>
				</tr>
				<tr>
					<th>出生日期</th>
					<td>
						<input name="data.birthday" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
						<!-- <input id="birthday" name="data.birthday" type="text" class="easyui-datebox" style="width: 100px;"> -->
					</td>
				</tr>
				<tr>
					<th>备用电话</th>
					<td><input name="data.secondPhoneNumber" /></td>			
				</tr>
				<tr>
					<th>电子邮件</th>
					<td><input name="data.email" style="width: 210px;"/></td>			
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>