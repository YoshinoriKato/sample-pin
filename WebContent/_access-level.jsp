<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String userId = Helper.getUserId(request);

String cardId = request.getParameter("cardId");
cardId = (cardId != null) ? cardId : "";

Card card = (Card)request.getAttribute("card");
%>

<div class="margin-top-default opacity50">
	<p>アクセスレベル</p>
	<form action="update-level.do" method="post" class="form">
		<fieldset>
			<input type="hidden" name="cardId" value="<%=cardId%>">
			<div class="control-group">
				<label for="accessLevel" class="control-label"></label>
				<div class="controls">
					<input type="number" min="0" max="100" name="accessLevel"
						class="text" placeholder="Caption"
						value="<%=card.getAccessLevel()%>">
				</div>
			</div>
			<input type="submit" value="Update" class="btn btn-large btn-primary">
		</fieldset>
	</form>
</div>
