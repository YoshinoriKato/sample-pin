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
<style type="text/css">
</style>
</head>

<body id="full-body" class="home">
	<%-- <jsp:include page="_topbar.jsp"></jsp:include> --%>
	<table height="100%" width="100%" class="gradient">
		<tbody>
			<tr>
				<td align="center" valign="middle">
					<div>
						<h1>Not Found</h1>
						<p class="margin-top-default">お探しのページは見つかりませんでした。</p>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<img src="img/doya_neko.png" style="position: fixed; right: 10px; bottom: 10px; ">
	<%-- <jsp:include page="_footer.jsp"></jsp:include> --%>
</body>
</html>