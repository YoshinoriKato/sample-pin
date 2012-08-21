<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String url = request.getRequestURI();
%>

<!-- 共有 -->

<div style="display: none" id="page"><%=url%></div>
<div id="copyrights" class="center caption">&copy; Sample-Pin</div>
<div id="ajax">
	<img src="img/ajax.gif">
	<div class="caption">Working for you.</div>
</div>
<div id="error-dialog">
	<div id="error-close" class="tab-button">x</div>
	<img src="img/broken.gif" class="image-shot">
	<div class="caption large">Sorry, server error.</div>
</div>
<a href="#top"><div id="bottom-label">Go top</div></a>

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
		if (url.contains("/index.jsp") || url.contains("/card-comment.jsp")) {
%>

		if (($(window).height() * 0.4) < ($(document).height() - $(this).scrollTop())) {
								callAjax($('#sorted').text(), 10, $counter, $(
										'#userId').text(), $('#cardId').text(),
										$('#type').text());
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

