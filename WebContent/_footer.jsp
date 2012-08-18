<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 共有 -->

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
<%
	String url = request.getRequestURI();
%>
<script type="text/javascript" charset="UTF-8">
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
<%if (url.contains("/index.jsp") || url.contains("/card-comment.jsp")) {%>
	if ($(document).height() - $(window).height() - 60 < $(
									this).scrollTop()) {
								callAjax($('#sorted').text(), 10, $counter, $(
										'#userId').text(), $('#cardId').text(),
										$('#type').text());
							}
<%}%>
	});
		$('#bottom-label a').click(function() {
			$('body').animate({
				scrollTop : 0
			}, 500);
			return false;
		});
	});

	$('#comment-close').click(function() {
		pushPull('#error-dialog', 'XXX');
	});
</script>

