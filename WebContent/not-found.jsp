<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {
		$('#ajax').fadeOut(1000);
	});
</script>
<style type="text/css">
</style>
</head>

<body id="full-body">
	<jsp:include page="_topbar.jsp"></jsp:include>
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
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>