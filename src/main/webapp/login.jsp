<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>系统登录</title>
<jsp:include page="inc.jsp"></jsp:include>
<link rel="stylesheet" href="style/login.css">
<script type="text/javascript">
$(function(){
	$("#login").slideDown(1000);
});

function verify_login(){
	var loginName = $("#loginName").val();
	var pwd = $("#pwd").val();
	$.post(frm.contextPath + '/base/frmUser!doNotNeedSessionAndSecurity_login.do', { 'data.loginName': loginName, 'data.pwd': pwd }, function(result) {
		if (result.success) {
			location.replace('${pageContext.request.contextPath}/index.jsp');
		} else {
			$.messager.alert('提示', result.msg, 'error', function() {
				$('#loginBtn').linkbutton('enable');
			});
		}
	},'json');
}
</script>
</head>
<body>

        <!-- <div class="main_left"></div>  -->
        <div class="login">
<!-- <div class="loginMain" id="loginMain" style="display: none;"></div>
<div class="login" id="login" style="display: none;"> -->
<form method="post" >
<div class="user">账号：<input id="loginName" name="data.username" value="test"></div>
<div class="psw">密码：<input id="pwd" name="data.password" type="password" value="123456"></div>
<div class="dl">
<input id="but_login" type="button" onclick="verify_login()" value="登录" class="denglu">
<input type="reset" value="重置" class="chongzhi">
</div>
</form>
</div>
<!-- 
<div id="loginDialog" title="系统登录" style="display: none; width: 320px; height: 180px; overflow: hidden;">
	<div id="loginTabs" class="easyui-tabs" data-options="fit:true,border:false">
		<div title="用户输入模式" style="overflow: hidden; padding: 10px;">
			<form method="post" class="form">
				<table class="table" style="width: 100%; height: 100%;">
					<tr>
						<th width="50">登录名</th>
						<td><input name="data.loginName" class="easyui-validatebox" data-options="required:true" value="nh" style="width: 210px;" /></td>
					</tr>
					<tr>
						<th>密码</th>
						<td><input name="data.pwd" type="password" class="easyui-validatebox" data-options="required:true" value="123456" style="width: 210px;" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
-->
</body>
</html>