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
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
    	<%-- 引入扩展图标 --%>
	<link rel="stylesheet" href="<%=contextPath%>/style/syExtIcon.css" type="text/css"> 
	 <!--
 	<link rel="stylesheet" href="<%=contextPath%>/js/mapbox-gl/mapbox-gl_v0.32.1.css" type="text/css">
	<script type="text/javascript" src="<%=contextPath%>/js/mapbox-gl/mapbox-gl_v0.32.1.js" charset="utf-8"></script>
     -->
    <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v0.37.0/mapbox-gl.js'></script>
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v0.37.0/mapbox-gl.css' rel='stylesheet' />
    <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:100%; }
        #control { position:absolute; z-index: 1; top: 40px; right:40px; width:10%; }
        #info {
        display: block;
        position: relative;
        margin: 0px auto;
        width: 50%;
        padding: 10px;
        border: none;
        border-radius: 3px;
        font-size: 12px;
        text-align: center;
        color: #222;
        background: #fff;
    }
    </style>
</head>
<body>

<pre id='info'></pre>
<div id='map'></div>
<div id='control'>
	刷新时间：<input id="startTime" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" value='2017-05-25 23:03:28'/><br>
	取数宽度S：<input id="timeWidth" style="width: 60px;"/><br>
	刷新周期S：<input id="periodic" style="width: 60px;"/><br>
	<input id="refrashCtrl" type="button" value="开始" onclick="refrashFun()"/><br>
	<!--<button id='refrashCtrl' onclick="refrashFun()">开始</button><br>
	 <img id='refrashCtrl' class="controlImg hpn-icon-turnRight" onclick="refrashFun();"/> -->
	<img class="controlImg hpn-icon-turnRight" onclick="turnRightFun();"/>
	<img class="controlImg hpn-icon-turnLeft" onclick="turnLeftFun();"/>
</div>
<script>
var bearing = 90;
var turnRightFun = function() {
	bearing = bearing-90;
	map.setBearing(bearing);
}
var turnLeftFun = function() {
	bearing = bearing+90;
	map.setBearing(bearing);
}
var refrashFlag = false;
var websocket = null;
var refrashFun = function(){
	if(refrashFlag){
		websocket.close();
		refrashFlag=false;
		document.getElementById("refrashCtrl").value="开始";
	}else{		
		if('WebSocket' in window){
	          websocket = new WebSocket("ws://localhost:8088/ips/websocket/chat");
	      }else{
	          alert('Not support websocket');
	      }
		
		//连接成功建立的回调方法
		websocket.onopen = function(event){
			var startTime = document.getElementById('startTime').value;//获取input的节点
			var periodic = document.getElementById('periodic').value;
			var timeWidth = document.getElementById('timeWidth').value;
			websocket.send(startTime +' and '+timeWidth +' and '+periodic); 
			refrashFlag=true;
			document.getElementById("refrashCtrl").value="停止";
			/* var refrashCtrlImg = document.getElementById('refrashCtrl'); 
			refrashCtrlImg.setAttribute("class", "controlImg hpn-icon-turnLeft");  */
		}; 

		//接收到消息的回调方法
		websocket.onmessage = function(){
		    mapRefresh(event.data);
		};
	}
}


mapboxgl.accessToken = 'pk.eyJ1IjoibWFwZXIiLCJhIjoiY2l3dm9qdzRiMDAxMTJ6cGY2ZHlzOTRvNCJ9.WcJV0GCgk_4XXHa8cnmi_Q';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/maper/ciwvpz28c002z2qpqxdg2m5cy',
    center: [116.420298, 39.947635],
    zoom: 20.5
});

map.on('mousemove', function (e) {
    document.getElementById('info').innerHTML =
        JSON.stringify(e.point) + '<br />' +
        JSON.stringify(e.lngLat)+ '<br />' +
        JSON.stringify(map.getZoom());
});

var mapRefresh = function(data){
	map.getSource('customerPositions').setData("../../../json/heatMap1.geojson");
};
	map.on('load', function() {
	    map.addSource("customerPositions", {
	        type: "geojson",
	        data: "../../../json/heatMap.geojson",
	        cluster: true,
	        clusterMaxZoom: 30, // Max zoom to cluster points on
	        clusterRadius: 2 // Radius of each cluster when clustering points (defaults to 50)
	    });

	    map.addLayer({
	        id: "clusters",
	        type: "circle",
	        source: "customerPositions",
	        filter: ["has", "point_count"],
	        paint: {
	            "circle-color": {
	                property: "point_count",
	                type: "interval",
	                stops: [
	                	[0, "#008000"],
	                    [10, "#ffff00"],
	                    [150, "#ff0000"],
	                ]
	            },
	            "circle-radius": {
	                property: "point_count",
	                type: "interval",
	                stops: [
	                	[0, 10],
	                    [10, 15],
	                    [150, 30]
	                ]
	            }
	        }
	    });

	    map.addLayer({
	        id: "cluster-count",
	        type: "symbol",
	        source: "customerPositions",
	        filter: ["has", "point_count"],
	        layout: {
	            "text-field": "{point_count_abbreviated}",
	            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
	            "text-size": 12
	        }
	    });

	    map.addLayer({
	        id: "unclustered-point",
	        type: "circle",
	        source: "customerPositions",
	        filter: ["!has", "point_count"],
	        paint: {
	            "circle-color": "#11b4da",
	            "circle-radius": 3,
	            "circle-stroke-width": 1,
	            "circle-stroke-color": "#fff"
	        }
	    });
	});



</script>

</body>
</html>
