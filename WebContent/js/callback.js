var $timer;

var $offset = 8;

var $counter = 0;

var $block = false;

function teaDown() {
	$('#ajax').fadeOut(1000);
	$block = false;
}

function callback($data) {
	var $i = 0;
	var $array = $data.array;
	var $type = $data.type;
	var $len = $array.length;
	var $interval = 3;
	$counter += $len;
	if ($len == 0) {
		teaDown();
		return;
	}

	$timer = setInterval(function() {
		$one = $array[$i];
		if ($type == 'comment') {
			makeComment($one);
		} else {
			makeCard($one);
		}
		wookmark();
		$i++;
		if ($i >= $len) {
			clearInterval($timer);
			$('#content').css('height', $(document).height() - 120);
			$('#read-cards').text('read ' + $counter + ' cards');
			wookmark();
			teaDown();
		}
	}, $interval);
};

function wookmark() {
	$('#content li').wookmark({
		offset : $offset
	});
};

function makeCard($card) {

	// components
	var $url = $card.url;
	var $jqLi = $('<li/>').addClass('card');
	var $jqDiv = $('<div/>').attr('id', $card.cardId);
	var $jqA = $('<a/>').addClass('no-hover');
	var $divRibon = $('<div/>').addClass('ribon');
	var $divImage = $('<div/>');
	var $divCaption = $('<div/>').addClass('caption deco');
	var $divFooter = $('<div/>').addClass('star right');
	var $divRibonText = $('<div/>').text($card.view + ' view');
	var $jqImg = $('<img/>').addClass('image-shot deco').attr('src',
			$card.imagePath);

	if ($url != null && $url != '') {
		$divRibonText.addClass('ribon-text color-blue');
		$jqDiv.addClass('cell2');
		$jqA.attr('href',
				'jump.jsp?cardId=' + $card.cardId + '&redirectUrl=' + $url)
				.attr('target', '_blank');
		$divFooter.text($url);
	} else {
		$divRibonText.addClass('ribon-text color-red');
		$jqDiv.addClass('cell');
		$jqA.attr('href', 'card-comment.jsp?cardId=' + $card.cardId
				+ '&type=comment');
		$divFooter.text($card.likes + ' comment');
	}

	// construct
	$('#content').append($jqLi);
	$jqLi.append($jqA.append($jqDiv));
	if ($card.view > 0) {
		$jqDiv.append($divRibon.append($divRibonText));
	}
	$jqDiv.append($divImage).append($divCaption).append($divFooter);
	$divImage.append($jqImg);
	$divCaption.text($card.caption).autoUrlLink().escapeReturn();
}

function makeComment($comment) {

	// components
	var $jqLi = $('<li/>').addClass('card');
	var $jqDiv = $('<div/>')
			.attr('id', $comment.cardId + '+' + $comment.userId);
	var $jqA = $('<a/>').addClass('no-hover');

	var $divNumber = $('<div/>').addClass('star comment');
	var $divCaption = $('<div/>').addClass('caption comment deco');
	var $divFooter = $('<div/>').addClass('star comment right');
	var $jqImg = $('<img/>').addClass('image-icon').attr('src',
			$comment.imagePath);

	$jqDiv.addClass('cell');
	$jqA.attr('href', 'profile.jsp?userId=' + $comment.userId);
	$divNumber.text($comment.cardId);
	$divCaption.append($comment.caption).autoUrlLink();
	$divFooter.text($comment.createDate).aboutTimestamp();
	$divFooter.css('clear', 'both');

	// construct
	$('#content').append($jqLi);
	$jqLi.append($jqDiv);
	$jqDiv.append($divNumber).append($divCaption).append($divFooter);
	$divNumber.append($jqA);
	$jqA.append($jqImg);
}

function callAjax($sorted, $limit, $offset, $userId, $cardId, $type) {
	if (!$block) {
		$block = true;
		$.ajax({
			cache : false,
			type : 'post',
			scriptCharset : 'UTF-8',
			url : 'xxx.do',
			data : {
				name : 'index.jsp',
				key : '0381075127472',
				sorted : $sorted,
				offset : $offset,
				limit : $limit,
				userId : $userId,
				cardId : $cardId,
				type : $type
			},
			success : callback,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$('#ajax').fadeOut(10);
				$('#error-dialog').fadeIn(10);
				$block = false;
			},
			dataType : 'jsonp'
		});
	}
};

