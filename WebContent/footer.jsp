<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 共有 -->

<div class="center caption">&copy; Sample-Pin</div>
<div id="cover" class="center">
	<div id="ajax">
		<img src="img/ajax.gif">
		<div class="caption">Working for You.</div>
	</div>
</div>
<div id="cover2" class="center">
	<div id="error-dialog">
		<img src="img/broken.gif" class="image-shot">
		<div class="caption">Sorry, Server Error.</div>
	</div>
</div>
<div id="bottom-label">
	<a href="#top">Go Top</a>
</div>
<script type="text/javascript" charset="UTF-8">
	$(function() {
		$('#bottom-label').hide();
		$(window).scroll(function() {
			$('#pos').text($(this).scrollTop());
			if ($(this).scrollTop() > 60) {
				$('#bottom-label').fadeIn();
			} else {
				$('#bottom-label').fadeOut();
			}
		});
		$('#bottom-label a').click(function() {
			$('body').animate({
				scrollTop : 0
			}, 500);
			return false;
		});
	});
</script>

