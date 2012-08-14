<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {
		$('#main').fadeIn(1000);
		$('#cover').fadeOut(1000);
	});
</script>
</head>

<body>
	<jsp:include page="topbar.jsp"></jsp:include>
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="hero-unit">
					<h1>Error</h1>
					<p>
					<%
						Exception e = (Exception) session.getAttribute("error");

						if (e != null) {
							out.print(e.getMessage());
						}
					%></p>
				</div>
			</div>
		</div>
	</div>
	<div id="cover" class="center"></div>
</body>
</html>