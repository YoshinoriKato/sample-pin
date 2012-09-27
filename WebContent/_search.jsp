<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	boolean isWrite = request.getRequestURI()
			.contains("/make-card.jsp");
%>

<form class="form-search" action="javascript:searchGoogle()">
	<fieldset>
		<%
			if (isWrite) {
		%>
		<div class="form-horizontal">
			<div class="control-group">
				<label for="search-box" class="control-label">Image Search</label>
				<div class="controls">
					<%
						}
					%>
					<input type="search" id="search-box" name="search-box" value=""
						placeholder="Please search images. At last, press enter key."
						class="input-medium input-search text"><input
						type="submit" class="btn  btn-submit" value="Search">
					<div id="float-box" class="input-text text"></div>
					<%
						if (isWrite) {
					%>
				</div>
			</div>
		</div>
		<%
			}
		%>
	</fieldset>
</form>

<ul id="search-result"></ul>
