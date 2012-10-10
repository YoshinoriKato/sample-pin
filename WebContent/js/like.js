var $block = false;

$.fn.makeLikeButton = function($userId, $cardId) {
	return function() {
		$action = 'up';
		if (this.hasClass('goodjob')) {
			$action = 'down';
		}
		$star.click(function() {
			ajaxLikeInfo($userId, $cardId, $action);
		});
	};
};

function ajaxLikeInfo($userId, $cardId, $action) {
	if (!$block) {
		$block = true;
		$.ajax({
			cache : false,
			type : 'post',
			scriptCharset : 'UTF-8',
			url : 'like.do',
			data : {
				userId : $userId,
				cardId : $cardId,
				action : $action
			},
			success : function($data) {
				$star = $('#gj_' + $cardId);
				$star.text($data.likes);
				if ($data.userId) {
					$star.removeClass('goojob');
					$star.addClass('goojob');
				} else {
					$star.removeClass('goojob');
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$('#error-dialog').fadeIn(10);
				$block = false;
			},
			dataType : 'jsonp'
		});
	}
};

