<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="zone.framework.util.base.ConfigUtil"%>
<%@ page import="zone.framework.model.base.SessionInfo"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
<%String contextPath = request.getContextPath();%>
<%String version = "20131115";%>

<%

Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
Cookie[] cookies = request.getCookies();
if (null != cookies) {
	for (Cookie cookie : cookies) {
		cookieMap.put(cookie.getName(), cookie);
	}
}
String easyuiTheme = "bootstrap";//指定如果用户未选择样式，那么初始化一个默认样式
if (cookieMap.containsKey("easyuiTheme")) {
	Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
	easyuiTheme = cookie.getValue();
}
%>
<style type="text/css">
 	@media screen and (min-width:1000px){
    	.searchDiv1{ position:absolute; top:10px; left:10px;  width:400px; height:30px; font-size:small} 
		.searchDiv2{ position:absolute; top:10px; left:410px; width:400px; height:30px; font-size:small}  
		.searchDiv3{ position:absolute; top:10px; left:810px; width:150px; height:30px; font-size:small} 
		.gridTable{ position:relative; top:35px;} 
		/* .responsiveDiv[type="style"]{ height:60px;} */
	}
	
	@media screen and (min-width: 620px) and (max-width: 999px) {
    	.searchDiv1{ position:relative; top:10px; left:10px;  width:400px; font-size:small} 
		.searchDiv2{ position:relative; top:12px; left:10px; width:400px; font-size:small}  
		.searchDiv3{ position:relative; top:-12px; left:410px; width:150px; font-size:small} 
		.toolbarTable{ position:relative; top:0px;} 
		.datagrid-view{ position:relative; top:12px;} 
	}
	
	@media only screen and (max-width: 619px) {
    	.searchDiv1{ position:relative; top:10px; left:10px;  width:400px; font-size:small} 
		.searchDiv2{ position:relative; top:12px; left:10px; width:400px; font-size:small}  
		.searchDiv3{ position:relative; top:12px; left:10px; width:150px; font-size:small} 
		.toolbarTable{ position:relative; top:0px;} 
		.datagrid-view{ position:relative; top:12px;} 
	} 
	
	.underline label{ text-decoration:underline;}
	@media print { 
		.noprint { display: none; }
	} 
</style>
<script type="text/javascript">
var frm = frm || {};
frm.contextPath = '<%=contextPath%>';
frm.basePath = '<%=basePath%>';
frm.version = '<%=version%>';
frm.pixel_0 = '<%=contextPath%>/style/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>

<%-- 引入my97日期时间控件 --%>
<script type="text/javascript" src="<%=contextPath%>/js/My97DatePicker4.8Beta3/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<%-- 引入ueditor控件 --%>
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = '<%=contextPath%>/js/ueditor1_2_6_1-utf8-jsp/';</script>
<script src="<%=contextPath%>/js/ueditor1_2_6_1-utf8-jsp/ueditor.config.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=contextPath%>/js/ueditor1_2_6_1-utf8-jsp/ueditor.all.min.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入jQuery --%>
<%
String User_Agent = request.getHeader("User-Agent");
if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1 && (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1)) {
	out.println("<script src='" + contextPath + "/js/jquery-1.9.1.js' type='text/javascript' charset='utf-8'></script>");
} else {
	out.println("<script src='" + contextPath + "/js/jquery-2.0.3.js' type='text/javascript' charset='utf-8'></script>");
}
%>
<%-- 引入jquery扩展 --%>
<script src="<%=contextPath%>/js/frmExtJquery.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入Highcharts 
<script src="<%=contextPath%>/js/Highcharts-3.0.6/js/highcharts.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=contextPath%>/js/Highcharts-3.0.6/js/modules/exporting.src.js" type="text/javascript" charset="utf-8"></script>
--%>

<script src="https://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<%-- 引入Highcharts扩展 
<script src="<%=contextPath%>/js/frmExtHighcharts.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入plupload --%>
<script type="text/javascript" src="<%=contextPath%>/js/plupload-2.0.0/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/plupload-2.0.0/zh_CN.js"></script>

<%-- 引入EasyUI --%>
<link id="easyuiTheme" rel="stylesheet" href="<%=contextPath%>/js/jquery-easyui-1.5.1/themes/<%=easyuiTheme%>/easyui.css" type="text/css">
<!-- <link rel="stylesheet" href="<%=contextPath%>/js/jquery-easyui-1.5.1/themes/icon.css" type="text/css"> -->
<script type="text/javascript" src="<%=contextPath%>/js/jquery-easyui-1.5.1/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/js/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<%-- 引入EasyUI Portal插件 --%>
<link rel="stylesheet" href="<%=contextPath%>/js/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="<%=contextPath%>/js/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<%-- 引入easyui扩展 --%>
<script src="<%=contextPath%>/js/frmExtEasyUI.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入扩展图标 --%>
<link rel="stylesheet" href="<%=contextPath%>/style/syExtIcon.css?version=<%=version%>" type="text/css">

<%-- 引入自定义样式 --%>
<link rel="stylesheet" href="<%=contextPath%>/style/syExtCss.css?version=<%=version%>" type="text/css">

<%-- 引入javascript扩展 --%>
<script src="<%=contextPath%>/js/frmExtJavascript.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入公共JS扩展 --%>
<script src="<%=contextPath%>/js/frmCommon.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>