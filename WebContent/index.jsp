<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sorted = request.getParameter("sorted");
	sorted = sorted == null ? "" : "?sorted=" + sorted;
%>
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
			var $jqA = $("<a/>").addClass("no-hover").attr("href",
					"card.jsp?cardId=" + array[i].cardId);
			var $divRibon = $("<div/>").addClass("ribon");
			var $divImage = $("<div/>").addClass("image-holder");
			var $divCaption = $("<div/>").addClass("caption deco");
			var $divStar = $("<div/>").addClass("star right");

			$jqA.append($("<img/>").addClass("image-shot deco").attr("src",
					array[i].url));
			$divRibon.append($("<span/>").addClass("ribon-text").text(
					array[i].view + " view"));
			$divCaption.text(array[i].caption);
			$divImage.append($jqA);
			$divStar.text("★" + array[i].likes);

			if (array[i].view > 0) {
				$jqDiv.append($divRibon);
			}
			$jqDiv.append($divImage).append($divCaption).append($divStar);
			$jqLi.append($jqDiv);

			$('#content').append($jqLi);
		}
		$('#content li').wookmark({
			offset : 12
		});
	};

	$(document).ready(function() {

		$sorted = $('#sorted').text();

		$(window).resize(function() {
			$('#content li').wookmark({
				offset : 12
			})
		});

		$.ajax({
			type : 'get',
			url : 'http://localhost:8080/sample-pin/xxx.do' + $sorted,
			data : {
				name : 'サンプル',
				key : '0381075127472'
			},
			success : callback,
			dataType : 'json'
		});
	});
</script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css?201208080923" rel="stylesheet">
</head>


<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<ul id="content">
		</ul>
		<br style="clear: both;" />
	</div>
	<div style="display: none" id="sorted"><%=sorted%></div>
</body>

</html>