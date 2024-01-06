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
     <style>
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
<div id='control'>
	<img class="controlImg hpn-icon-turnRight" onclick="turnRightFun();"/>
	<img class="controlImg hpn-icon-turnLeft" onclick="turnLeftFun();"/>
</div>
<nav id="floorCtl"></nav>
<script>

var floorIds = [ '1', '2', '3', '4'];

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

mapboxgl.accessToken = 'pk.eyJ1IjoibWFwZXIiLCJhIjoiY2l3dm9qdzRiMDAxMTJ6cGY2ZHlzOTRvNCJ9.WcJV0GCgk_4XXHa8cnmi_Q';
var map = new mapboxgl.Map({
    container: 'map', // container id
    style: 'mapbox://styles/maper/ciwvpz28c002z2qpqxdg2m5cy', //stylesheet location
    center: [112.520855,-0.008069], // starting position
    zoom: 10, // starting zoom
    bearing:90,
    attributionControl:true
});	
map.addControl(new mapboxgl.NavigationControl());
	//styleLocation = '../../../json/mapboxData0306.json'
map.on('load', function () {
    map.addLayer({
        "id": "route",
        "type": "line",
        "source": {
            "type": "geojson",
            "data": '../../../json/2floor/routes.geojson'
        },
        "layout": {
        	"visibility": "visible",
            "line-join": "round",
            "line-cap": "round"
        },
        "paint": {
            "line-color": "#fff",
            "line-width": 2
        }
    });
});

var showHeatMap = function(){
	map.on('load', function() {
	    map.addSource("earthquakes", {
	        type: "geojson",
	        data: '../../../json/earthquakes.geojson',
	        cluster: true,
	        clusterMaxZoom: 15, // Max zoom to cluster points on
	        clusterRadius: 20 // Use small cluster radius for the heatmap look
	    });

	    // Use the earthquakes source to create four layers:
	    // three for each cluster category, and one for unclustered points

	    // Each point range gets a different fill color.
	    var layers = [
	        [0, 'green'],
	        [20, 'orange'],
	        [200, 'red']
	    ];

	    layers.forEach(function (layer, i) {
	        map.addLayer({
	            "id": "cluster-" + i,
	            "type": "circle",
	            "source": "earthquakes",
	            "paint": {
	                "circle-color": layer[1],
	                "circle-radius": 70,
	                "circle-blur": 1 // blur the circles to get a heatmap look
	            },
	            "filter": i === layers.length - 1 ?
	                [">=", "point_count", layer[0]] :
	                ["all",
	                    [">=", "point_count", layer[0]],
	                    ["<", "point_count", layers[i + 1][0]]]
	        }, 'waterway-label');
	    });

	    map.addLayer({
	        "id": "unclustered-points",
	        "type": "circle",
	        "source": "earthquakes",
	        "paint": {
	            "circle-color": 'rgba(0,255,0,0.5)',
	            "circle-radius": 20,
	            "circle-blur": 1
	        },
	        "filter": ["!=", "cluster", true]
	    }, 'waterway-label');
	});
}	
var showRouteMap = function(){
	
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
	            "line-color": "#888",
	            "line-width": 8
	        }
	});
 });
}
var showFloorMap = function(n) {
	if(n==1){
		map.setStyle('mapbox://styles/maper/ciwvpz28c002z2qpqxdg2m5cy');
		map.setCenter([112.520855,-0.008069]);
		map.setZoom(10);
		map.setBearing(90);
	}else if(n==2){
		map.setStyle('mapbox://styles/maper/cizfl4jyx007m2sji1ndyc4nl');
		map.setCenter([116.420298, 39.947635]);
		map.setZoom(20.5);
		map.setBearing(-70);
		map.setLayoutProperty('route', 'visibility', 'visible');
	}
	/* else if(n==3){
		map.setStyle('mapbox://styles/mapbox/dark-v9');
		map.setCenter([-103.59179687498357, 40.66995747013945]);
		map.setZoom(3);
		map.setBearing(0);
		showHeatMap();
	}else if(n==4){
		map.setStyle('mapbox://styles/mapbox/streets-v9');
		map.setCenter([-122.486052, 37.830348]);
		map.setZoom(15);
		map.setBearing(-90);
	} */
}

var bearing = 90;
var turnRightFun = function() {
	bearing = bearing-90;
	map.setBearing(bearing);
}
var turnLeftFun = function() {
	bearing = bearing+90;
	map.setBearing(bearing);
}
</script>

</body>
</html>
