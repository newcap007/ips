<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>欢迎页面</title>
<jsp:include page="inc.jsp"></jsp:include>
<style type="text/css">
.outside
{
    height:180px;
    overflow:hidden;
    float:left;
    margin:2px;
    padding:2px;
}
.inside
{
    position:relative;
}
#text
{
    width:160px;
    height:160px;
    /* background:#F0F8FF; */
    padding:10px;
    color:#000000;
}
</style>
<script type="text/javascript">
function go(id, d1, px1, val1, d2, px2, val2) {
    $(id).delay(d1);
    $(id).animate({ bottom: px1 }, val1, function () {
        $(id).delay(d2);
        $(id).animate({ bottom: px2 }, val2);
    });
};

$(function() {
    timer1 = setInterval(function () {
        go("#navi", 1000, "185px", 1200, 1500, "0px", 2000);
        go("#navigate", 1500, "185px", 1200, 1000, "0px", 2000);
        go("#supervise", 2000, "185px", 1200, 1000, "0px", 2000);
    }, 3000);
    $("#naviImg").attr("src",frm.contextPath + "/style/images/navi.png");
    $("#navigateImg").attr("src",frm.contextPath + "/style/images/navigate.png");
    $("#superviseImg").attr("src",frm.contextPath + "/style/images/supervise.png");
});
</script>
</head>
<body>
	<div class="outside" >
    	<div id="navi" class="inside">
        	<img id="naviImg" width="180px" height="180px" />
        	<div id="text">
            	<span>导览</span>
            	<p></p>
            	<p>客户通过APP上传所在的位置信息，系统根据此位置信息反馈附近藏品的图片、语音及文字信息</p>
        	</div>
    	</div>
	</div>
	<div class="outside">
    	<div id="navigate" class="inside">
        	<img id="navigateImg" width="180px" height="180px"/>
        	<div id="text">
            	<span>导航</span>
            	<p></p>
            	<p>室内高精度导航</p>
        	</div>
    	</div>
	</div>
	<div class="outside">
    	<div id="supervise" class="inside">
        	<img id="superviseImg" width="180px" height="180px"/>
        	<div id="text">
            	<span>监控平台</span>
            	<p></p>
            	<p>通过监控平台，您可以很直观的了解客户的具体位置，哪些地方人气高</p>
        	</div>
    	</div>
	</div>
</body>
</html>