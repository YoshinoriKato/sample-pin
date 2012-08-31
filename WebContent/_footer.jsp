<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String url = request.getRequestURI();
	String logined = (String)request.getAttribute("login");
%>

<!-- 共有 -->

<div style="display: none" id="page"><%=url%></div>

<div id="copyrights" class="center caption large">&copy; <%=Helper.NAME %></div>

<div id="ajax">
	<% if(logined !=null) { %>
		<div class="caption star">Auto login...</div>
	<% } %>
	<img src="img/ajax.gif">
	<div class="caption star">Working for you.</div>
</div>

<div id="error-dialog">
	<div id="error-close" class="tab-button">X</div>
	<img src="img/broken.gif" class="image-shot">
	<div class="caption large">Sorry, server error.</div>
</div>

<a href="#top"><span id="bottom-label">Go top</span></a>

<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {	
		$('#error-close').attr("onclick",
		"pushPull('#null','#error-dialog')");
	});
	
	var $scroll = 240;

	$(function() {
		$('#bottom-label').hide();
		$(window)
				.scroll(
						function() {
							if ($(this).scrollTop() > 60) {
								$('#bottom-label').fadeIn();
							} else {
								$('#bottom-label').fadeOut();
							}
<%
		if (url.contains("/home.jsp") || url.contains("/card-comment.jsp")) {
%>

		if (($(window).height() * 0.4) < ($(document).height() - $(this).scrollTop())) {
								callAjax($('#sorted').text(), 20, $counter, $(
										'#userId').text(), $('#cardId').text(),
										$('#type').text(), $('#words').text());
							}
							
<%
	}
%>
		});
		$('#bottom-label a').click(function() {
			$('body').animate({
				scrollTop : 0
			}, 500);
			return false;
		});

	});
</script>

