<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="zone.framework.model.base.SessionInfo"%>
<%@ page import="zone.framework.util.base.ConfigUtil"%>
<%
	String contextPath = request.getContextPath();
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
%>
<script type="text/javascript" charset="utf-8">
	var lockWindowFun = function() {
		$.post(frm.contextPath + '/base/frmUser!doNotNeedSessionAndSecurity_logout.do', function(result) {
			$('#loginDialog').dialog('open');
		}, 'json');
	};
	var logoutFun = function() {
		$.post(frm.contextPath + '/base/frmUser!doNotNeedSessionAndSecurity_logout.do', function(result) {
			location.replace(frm.contextPath + '/index.jsp');
		}, 'json');
	};
	var showMyInfoFun = function() {
		var dialog = parent.frm.modalDialog({
			title : '我的信息',
			url : frm.contextPath + '/jsp/userInfo.jsp'
		});
	};
</script>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 5px;height: 60px;">
	<font size="3" face="arial" color="red">欢迎您！</font>
	<font size="3" face="arial" color="red"><strong>
	<%
		if (sessionInfo != null) {
			out.print(zone.framework.util.base.StringUtil.formateString("{0}", sessionInfo.getUser().getName()));
		}
	%></strong>
	</font>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'hpn-icon-cstyle'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="frm.changeTheme('default');" title="default">default</div>
	<div onclick="frm.changeTheme('gray');" title="gray">gray</div>
	<div onclick="frm.changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-user'" onclick="showMyInfoFun();">我的信息</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-lock'" onclick="lockWindowFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div>