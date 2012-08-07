<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample-Pin</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript" src="jquery.wookmark.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
	function callback(array) {
		for ( var i = 0; i < array.length; i++) {
			var $jqLi = $("<li/>");
			var $jqDiv = $("<div/>").addClass("cell");
			var $jqDiv1 = $("<div/>").addClass("ribon");
			var $jqDiv2 = $("<div/>").addClass("center stars star");
			var $jqDiv3 = $("<div/>").addClass("image-holder");
			var $jqDiv4 = $("<div/>").addClass("title deco");
			var $jqA = $("<a/>").addClass("no-hover").attr("href", "card.jsp?id=" + array[i].id);

			$jqDiv1.append($("<span/>").addClass("ribon-text").text(
					array[i].view + " view"));
			$jqDiv2.text("★" + array[i].likes);
			$jqDiv3.append($("<img/>").addClass("image-shot deco").attr("src",
					array[i].url));
			$jqDiv4.text(array[i].caption);
			$jqDiv.append($jqA);
			
			$jqA.append($jqDiv1).append($jqDiv2).append($jqDiv3).append(
					$jqDiv4);
			$jqLi.append($jqDiv);

			$('#content').append($jqLi);
		}
		$('#content li').wookmark({
			offset : 12
		});
	}
	$(document).ready(function() {
		$(window).resize(function() {
			$('#content li').wookmark({
				offset : 12
			})
		});
	});

	$.ajax({
		type : 'get',
		url : 'http://localhost:8080/sample-pin/xxx.do',
		data : {
			name : 'サンプル',
			key : '0381075127472'
		},
		success : callback,
		dataType : 'json'
	});
</script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css" rel="stylesheet">
</head>


<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<ul id="content">
		</ul>
		<br style="clear: both;" />
	</div>
</body>

</html>