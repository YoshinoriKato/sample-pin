var $scroll = 240;

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

function isNeed2() {
	return ($scrolled > $(this).scrollTop());
}

function isTarget($page) {
	return $page.indexOf('/home.jsp') || $page.indexOf('/card-comment.jsp');
}

function isTarget2($page) {
	return $page.indexOf('/home.jsp');
}

var $scrolled = 0;

$(function() {
	$('#bottom-label').hide();
	$(window).scroll(function() {
		bottomLabel();
		var $page = $('#page').text();
		if (isTarget($page)) {
			if (isNeed()) {
				readMore();
			}
		}
//		if (isTarget2($page)) {
//			if (isNeed2()) {
//				$('#add-card').fadeIn();
//			} else {
//				$('#add-card').fadeOut();
//			}
//		}
		$scrolled = $(this).scrollTop();
	});
	gotoTop();
});
