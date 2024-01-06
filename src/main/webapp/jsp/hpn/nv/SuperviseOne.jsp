<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="zone.framework.util.base.SecurityUtil"%>
<%
	String contextPath = request.getContextPath();
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8' />
    <title></title>
    <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
	<%-- 引入扩展图标 --%>
	<link rel="stylesheet" href="<%=contextPath%>/style/syExtIcon.css" type="text/css"> 
 	<link rel="stylesheet" href="<%=contextPath%>/js/mapbox-gl/mapbox-gl_v0.32.1.css" type="text/css">
	<script type="text/javascript" src="<%=contextPath%>/js/mapbox-gl/mapbox-gl_v0.32.1.js" charset="utf-8"></script>
 <!--
    <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v0.28.0/mapbox-gl.js'></script>
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v0.28.0/mapbox-gl.css' rel='stylesheet' />
 -->
     <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:99%; float:left; }
        #control { position: absolute; z-index: 1; top: 420px; right: 550px; }
    </style>
</head>
<body>

<div id='maps'></div>
<script>
	mapboxgl.accessToken = 'pk.eyJ1IjoibWFwZXIiLCJhIjoiY2l3dm9qdzRiMDAxMTJ6cGY2ZHlzOTRvNCJ9.WcJV0GCgk_4XXHa8cnmi_Q';

	var showMap = function(styleLocation, containerId) {
		var map = new mapboxgl.Map({
			container : containerId, // container id
			style : styleLocation, //stylesheet location
			center : [ 112.520855, -0.008069 ], // starting position
			zoom : 10, // starting zoom
			bearing : 90,
			attributionControl : true
		});
		map.addControl(new mapboxgl.NavigationControl());
		var bearing = 90;
		var turnRightFun = function() {
			bearing = bearing - 90;
			map.setBearing(bearing);
		}
		var turnLeftFun = function() {
			bearing = bearing + 90;
			map.setBearing(bearing);
		}
	}
	
	var floorIds = [ 'mapbox://styles/maper/ciwvpz28c002z2qpqxdg2m5cy',
		'mapbox://styles/maper/cizfl4jyx007m2sji1ndyc4nl',
		'mapbox://styles/mapbox/streets-v9',
		'mapbox://styles/mapbox/outdoors-v9' ];

for (var i = 0; i < floorIds.length; i++) {
	var styleLocation = floorIds[i];
	var mapDiv = document.createElement('div');
	var maps = document.getElementById('maps');
	maps.appendChild(mapDiv);
	containerId = 'map' + i;
	mapDiv.id = containerId;
	mapDiv.class = 'map';
	showMap(styleLocation, containerId);
};
</script>

</body>
</html>
