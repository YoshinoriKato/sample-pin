<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
</head>

<%
	final String LS = System.getProperty("line.separator");

	Inquiry inquiry = (Inquiry) request.getAttribute("confirm");
	session.setAttribute("confirm", inquiry);

	String message = (String) request.getAttribute("message");
	message = (message != null) ? message : "";
	String error = (message != null && !message.isEmpty())
			? "error"
			: "";
%>

<body class="home">
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Confirmation</div>
	<div id="main">
		<div id="input-window">
			<div class="cell padding20">
				<div class="form-horizontal">
					<form id="comment-form" action="confirm-make-message.do"
						method="post" class="form-horizontal">
						<fieldset>
							<h1 class="tab-header">Confirmation</h1>
							<div class="control-group">
								<label class="control-label">Preview</label>
								<div class="controls">
									<div class="well" style="margin-right: 20px;">
										<div><%=Helper.escapeHTML(inquiry.getMessage())%></div>
										<div><%=Helper.escapeHTML(inquiry.getMail())%></div>
									</div>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="submit" value="Confirm"
										class="btn btn-large btn-primary btn-cell">
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="_footer.jsp"></jsp:include>
</body>

<script type="text/javascript">
	attach(window, 'load', function() {
		$cardId = $("#cardId").text();
		pushPull('#main', '#ajax');
		wookmark();
		$('#comment-form').submit(function() {
			removeText('make-card');
			return true;
		});
	});
	$(window).resize(function() {
		wookmark();
	});
</script>


</html>
