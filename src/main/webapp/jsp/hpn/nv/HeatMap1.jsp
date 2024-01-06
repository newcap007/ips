<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8' />
    <title></title>
    <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
    <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v0.37.0/mapbox-gl.js'></script>
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v0.37.0/mapbox-gl.css' rel='stylesheet' />
    <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:100%; }
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

<div id='map'></div>
<pre id='info'></pre>

<script>
mapboxgl.accessToken = 'pk.eyJ1IjoibWFwZXIiLCJhIjoiY2l3dm9qdzRiMDAxMTJ6cGY2ZHlzOTRvNCJ9.WcJV0GCgk_4XXHa8cnmi_Q';
var map = new mapboxgl.Map({
	container: 'map',
    style: 'mapbox://styles/mapbox/dark-v9',
    center: [-103.59179687498357, 40.66995747013945],
    zoom: 3
});

map.on('mousemove', function (e) {
    document.getElementById('info').innerHTML =
        JSON.stringify(e.point) + '<br />' +
        JSON.stringify(e.lngLat)+ '<br />' +
        JSON.stringify(map.getZoom());
});
map.on('load', function() {
    // Add a new source from our GeoJSON data and set the
    // 'cluster' option to true. GL-JS will add the point_count property to your source data.
    map.addSource("earthquakes", {
        type: "geojson",
        // Point to GeoJSON data. This example visualizes all M1.0+ earthquakes
        // from 12/22/15 to 1/21/16 as logged by USGS' Earthquake hazards program.
        data: "../../../json/earthquakes.geojson",
        cluster: true,
        clusterMaxZoom: 10, // Max zoom to cluster points on
        clusterRadius: 50 // Radius of each cluster when clustering points (defaults to 50)
    });

    map.addLayer({
        id: "clusters",
        type: "circle",
        source: "earthquakes",
        filter: ["has", "point_count"],
        paint: {
            "circle-color": {
                property: "point_count",
                type: "interval",
                stops: [
                	 [0, "#51bbd6"],
                     [100, "#f1f075"],
                     [750, "#f28cb1"],
                ]
            },
            "circle-radius": {
                property: "point_count",
                type: "interval",
                stops: [
                	[0, 20],
                    [20, 30],
                    [100, 40]
                ]
            }
        }
    });

    map.addLayer({
        id: "cluster-count",
        type: "symbol",
        source: "earthquakes",
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
        source: "earthquakes",
        filter: ["!has", "point_count"],
        paint: {
            "circle-color": "#11b4da",
            "circle-radius": 4,
            "circle-stroke-width": 1,
            "circle-stroke-color": "#fff"
        }
    });
});
</script>

</body>
</html>
