var $scroll = 240;

var $scrolled = 0;

function bottomLabel() {
	if ($(this).scrollTop() > 60) {
		$('#bottom-label').fadeIn();
	} else {
		$('#bottom-label').fadeOut();
	}
}

function readMore() {
	callAjax($('#sorted').text(), 20, $counter, $('#userId').text(), $(
			'#cardId').text(), $('#type').text(), $('#words').text());
}

function gotoTop() {
	$('#bottom-label a').click(function() {
		$('body').animate({
			scrollTop : 0
		}, 1000);
		return false;
	});
}

function isNeed() {
	return ($(window).height() * 0.4) < ($(document).height() - $(this)
			.scrollTop());
}

//function isTarget($page) {
//	return $page
//			&& $page != ''
//			&& ($page.indexOf('/home.jsp') > 0 || $page
//					.indexOf('/card-comment.jsp') > 0);
//}

$(function() {
	$('#bottom-label').hide();

	attach(window, 'scroll', function() {
		bottomLabel();
		$scrolled = $(this).scrollTop();
	});

	gotoTop();
});
