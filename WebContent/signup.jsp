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

<%
	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";

	String fromUrl = (String) request.getAttribute("fromUrl");
	fromUrl = fromUrl != null ? fromUrl : "index.jsp";
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<form action="signup.do" method="post" class="form-horizontal">
						<fieldset>
							<h3>Sign up</h3>
							<input type="hidden" name="redirectUrl" value="<%=fromUrl%>" />
							<div class="control-group <%=error%>">
								<label for="mail" class="control-label">Mail Address</label>
								<div class="controls">
									<input type="email" name="mail" class="span6 input"
										placeholder="xxxxxx@xxx.xxx" /><span class="help-inline"><%=message%></span>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="submit" name="submit" value="Sign up"
										class="btn btn-large btn-primary btn-cell" />
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>