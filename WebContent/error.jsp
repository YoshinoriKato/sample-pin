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
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css?201208080923" rel="stylesheet">
</head>

<body>
	<jsp:include page="topbar.jsp"></jsp:include>
	<div id="main">
		<div class="container">
			<div class="row">
				<h1>Error</h1>
				<div class="herounit">
					<%
						Exception e = (Exception) session.getAttribute("error");

						if (e != null) {
							out.print(e.getMessage());
						}
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>