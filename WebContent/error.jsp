<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="refresh" CONTENT="15; URL=home.jsp">
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	attach(window, 'load', function() {
		$('#ajax').fadeOut(1000);
	});
</script>
</head>

<body id="full-body" class="home">
	<%-- <jsp:include page="_topbar.jsp"></jsp:include> --%>
	<table height="100%" width="100%" class="gradient">
		<tbody>
			<tr>
				<td align="center" valign="middle">
					<div>
						<h1>Error</h1>
						<p class="margin-top-default">技術的な問題により処理できませんでした。</p>
						<p class="margin-top-default">
							<%
								Exception e = (Exception) session.getAttribute("error");

								if (e != null) {
									out.print(e.getMessage());
								}
							%>
						</p>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<%-- <jsp:include page="_footer.jsp"></jsp:include> --%>
</body>
</html>