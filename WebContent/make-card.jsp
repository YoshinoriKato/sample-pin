<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
</head>


<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<%
						Random r = new Random(System.nanoTime());
					%>
					<div class="form-horizontal">
						<form action="uplaod.do" enctype="multipart/form-data"
							method="post" class="form-horizontal">
							<fieldset>
								<h3>Make Card</h3>
								<div class="control-group">
									<label for="iconPath" class="control-label">Image</label>
									<div class="controls">
										<input type="file" class="span8" name="iconPath" />
									</div>
								</div>
								<div class="control-group">
									<label for="comment" class="control-label">Comment</label>
									<div class="controls">
										<textarea name="comment" class="span8" rows="8"></textarea>
									</div>
								</div>
								<div class="control-group">
									<label for="url" class="control-label">URL</label>
									<div class="controls">
										<input type="url" name="url">
									</div>
								</div>
								<div class="control-group">
									<div class="controls">
										<input type="submit" value="Make" class="btn btn-large">
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>