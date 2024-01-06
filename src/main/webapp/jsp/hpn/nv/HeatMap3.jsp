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

map.on('load', function() {
    map.addSource("customerPositions", {
        type: "geojson",
        data: "../../../json/heatMap.geojson",
        cluster: true,
        clusterMaxZoom: 30, // Max zoom to cluster points on
        clusterRadius: 2 // Radius of each cluster when clustering points (defaults to 50)
    });

    // Each point range gets a different fill color.
    var layers = [
        [0, 'green'],
        [10, 'orange'],
        [50, 'red']
    ];
    
    layers.forEach(function (layer, i) {
        map.addLayer({
            "id": "cluster-" + i,
            "type": "circle",
            "source": "customerPositions",
            "paint": {
                "circle-color": layer[1],
                "circle-radius": 30,
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
        "source": "customerPositions",
        "paint": {
            "circle-color": 'rgba(0,255,0,0.5)',
            "circle-radius": 20,
            "circle-blur": 1
        },
        "filter": ["!=", "cluster", true]
    }, 'waterway-label');
    
});

//create the popup
var popup = new mapboxgl.Popup({offset: 25})
    .setText('Construction on the Washington Monument began in 1848.');

// create DOM element for the marker
var el = document.createElement('div');
el.id = 'marker';

// create the marker
new mapboxgl.Marker(el, {offset:[-25, -25]})
    .setLngLat(monument)
    .setPopup(popup) // sets a popup on this marker
    .addTo(map);

</script>

</body>
</html>
