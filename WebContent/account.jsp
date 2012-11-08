<%@page import="java.util.List"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
</head>

<%
	String userId = Helper.getUserId(request);
	User user = Helper.getUserById(userId);

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
%>

<body>
	<jsp:include page="_topbar.jsp"></jsp:include>
	<div id="title">Account</div>
	<div id="main">
		<div id="input-window">
			<div class="form-horizontal">
				<form action="account.do" enctype="multipart/form-data"
					method="post" class="form-horizontal">
					<fieldset>
						<h1 class="tab-header">Account</h1>
						<div class="control-group">
							<label for="imagePath" class="control-label">Image</label>
							<div class="controls">
								<div>
									<img src="<%=user.getImagePath()%>" class="image-shot"
										style="max-width: 85%">
								</div>
								<input type="file" name="imagePath">
							</div>
						</div>
						<div class="control-group">
							<label for="userName" class="control-label">Name</label>
							<div class="controls">
								<input type="text" name="userName" maxlength="40"
									value="<%=user.getUserName()%>" class="text input-text">
							</div>
						</div>
						<div class="control-group">
							<label for="mail" class="control-label">Mail</label>
							<div class="controls">
								<input type="email" class="text input-text" name="mail"
									value="<%=user.getMail()%>" />
							</div>
						</div>
						<div class="control-group">
							<label for="birthDay" class="control-label">Birth Day</label>
							<div class="controls">
								<input type="date" name="birthDay" class="input-short text"
									value="<%=user.getBirthDay()%>" id="country">
							</div>
						</div>
						<div class="control-group">
							<label for="comment" class="control-label">Comment</label>
							<div class="controls">
								<textarea class="textarea input-text" name="comment" rows="4"><%=user.getComment()%></textarea>
							</div>
						</div>
						<div class="control-group">
							<label for="country" class="control-label">Country</label>
							<div class="controls">
								<select id="country" name="country" multiple="multiple"
									class="text input-short">
									<%
										List<Country> countries = Helper.getCountries();
										for (Country country : countries) {
											String selected = country.getCode().equals(user.getCode()) ? "selected=\"selected\""
													: "";
									%>
									<option value="<%=country.getCode()%>" <%=selected%>><%=country.getEnName()%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
						<%
							if (userId.startsWith("ID_") || userId.startsWith("DOYA")) {
						%>
						<div class="control-group <%=error%>">
							<label for="password0" class="control-label">Password</label>
							<div class="controls">
								<input type="password" name="password0" class="text input-short"><span
									class="help-inline"><%=message%></span>
							</div>
						</div>
						<div class="control-group <%=error%>">
							<label for="password1" class="control-label">New Password</label>
							<div class="controls">
								<input type="password" name="password1" class="text input-short"><span
									class="help-inline"><%=message%></span>
							</div>
						</div>
						<div class="control-group <%=error%>">
							<label for="password2" class="control-label">Confirm
								Password</label>
							<div class="controls">
								<input type="password" name="password2" class="text input-short"><span
									class="help-inline"><%=message%></span>
							</div>
						</div>
						<%
							}
						%>
						<div class="control-group">
							<div class="controls">
								<input type="submit" value="Update"
									class="btn btn-large btn-primary btn-cell">
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>