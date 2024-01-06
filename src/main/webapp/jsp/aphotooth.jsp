<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Photobooth.js</title>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="../js/photobooth_min.js"></script>
<script type="text/javascript" src="../js/photoscript.js"></script>
<link type="text/css" rel="stylesheet" media="screen" href="../css/photoTake.css" />
<script type="text/javascript">
var selectSingle = function() {
	var pictureValue = $(':input[name="picture"]').val();
	return pictureValue;
};
</script>
</head>
<body>
<div id="wrapper">
	<div id="camera" style="width: 300; height:300; float:left"></div>
	<div id="gallery" style="float:right"></div>
</div>
</body>
</html>