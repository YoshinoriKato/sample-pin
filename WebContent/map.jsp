<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Open Street Map</title>
<script src="http://www.openlayers.org/api/OpenLayers.js"></script>
<script>
	function init() {
		var map = new OpenLayers.Map("canvas2");
		var mapnik = new OpenLayers.Layer.OSM();
		map.addLayer(mapnik);

		var lonLat = new OpenLayers.LonLat(139.76, 35.68).transform(
				new OpenLayers.Projection("EPSG:4326"),
				new OpenLayers.Projection("EPSG:900913"));
		map.setCenter(lonLat, 15);
	}
	/* 	attach(window, 'load', function() {	
	 init();
	 });
	 */
</script>
<style type="text/css">
#canvas2 {
	width: 230px;
	height: 230px;
}

#canvas2 .olControlAttribution {
	font-size: 13px;
	bottom: 3px;
}

.olTileImage {
	width: 230px;
	height: 230px;
}
</style>
</head>
<body onload="init()">
	<div id="canvas2" class="image-shot"></div>
</body>
</html>