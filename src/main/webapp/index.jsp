<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="zone.framework.model.base.SessionInfo"%>
<%@ page import="zone.framework.util.base.ConfigUtil"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>SSHE</title>
<jsp:include page="inc.jsp"></jsp:include>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
	if (sessionInfo != null) {
		request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
	} else {
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
%>
</head>
<body>
</body>
</html>