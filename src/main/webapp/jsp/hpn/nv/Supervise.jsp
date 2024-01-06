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
     <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:99%; float:left; }
        
        #floorCtl {
        background: #fff;
        position: absolute;
        z-index: 1;
        top: 300px;
        right: 30px;
        border-radius: 3px;
        width: 30px;
        border: 1px solid rgba(0,0,0,0.4);
        font-family: 'Open Sans', sans-serif;
    }
    
    #floorCtl a {
        font-size: 13px;
        color: #404040;
        display: block;
        margin: 0;
        padding: 0;
        padding: 10px;
        text-decoration: none;
        border-bottom: 1px solid rgba(0,0,0,0.25);
        text-align: center;
    }

    #floorCtl a:last-child {
        border: none;
    }

    #floorCtl a:hover {
        background-color: #f8f8f8;
        color: #404040;
    }

    #floorCtl a.active {
        background-color: #3887be;
        color: #ffffff;
    }

    #floorCtl a.active:hover {
        background: #3074a4;
    }
     </style>
</head>
<body>

<div id='map'></div>
<nav id="floorCtl"></nav>
<script>
mapboxgl.accessToken = 'pk.eyJ1IjoibWFwZXIiLCJhIjoiY2l3dm9qdzRiMDAxMTJ6cGY2ZHlzOTRvNCJ9.WcJV0GCgk_4XXHa8cnmi_Q';

var showLayer = function(style,center,zoom) {
	var map = new mapboxgl.Map({
	    container: 'map',
	    style: style,
	    center: center,
	    zoom: zoom
	});
	map.on('load', function () {
	    map.addLayer({
	        "id": "route",
	        "type": "line",
	        "source": {
	            "type": "geojson",
	            "data": '../../../json/2floor/routes.geojson'
	        },
	        "layout": {
	            "line-join": "round",
	            "line-cap": "round"
	        },
	        "paint": {
	            "line-color": "#fff",
	            "line-width": 2
	        }
	    });
	});
}


var showFloorMap = function(n) {
	if(n==1){
		showLayer('mapbox://styles/maper/ciwvpz28c002z2qpqxdg2m5cy',[116.420298, 39.947635],20.5);
	}else if(n==2){
		showLayer('mapbox://styles/maper/cizfl4jyx007m2sji1ndyc4nl',[116.420298, 39.947635],20.5);
	}
/* 	else if(n==3){
		showLayer('mapbox://styles/maper/cizfl4jyx007m2sji1ndyc4nl',[116.420298, 39.947635],20.5);
	}else if(n==4){
		showLayer('mapbox://styles/maper/cizfl4jyx007m2sji1ndyc4nl',[116.420298, 39.947635],20.5);
	} */
}

var floorIds = [ '1', '2'];

for (var i = 0; i < floorIds.length; i++) {
    var id = floorIds[i];

    var link = document.createElement('a');
    link.href = '#';
    link.className = 'active';
    link.textContent = id;

    link.onclick = function (e) {
        var clickedFloor = this.textContent;
        showFloorMap(clickedFloor);
    };

    var floors = document.getElementById('floorCtl');
    floors.appendChild(link);
}
</script>

</body>
</html>
