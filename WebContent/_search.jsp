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
				<div class="input-append">
					<%
						}
					%>
					<input type="search" id="search-box" name="search-box" value=""
						placeholder="Please search images. At last, press enter key."
						class="input-search span4"><input
						type="submit" class="btn" value="Search">
					<%
						if (isWrite) {
					%>
				</div></div>
			</div>
		</div>
		<%
			}
		%>
	</fieldset>
</form>

<ul id="search-result"></ul>
