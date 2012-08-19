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
	var $jqA1 = $('<a/>').addClass('no-hover');
	var $jqA2 = $('<a/>').addClass('no-hover');
	var $divRibon = $('<div/>').addClass('ribon');
	var $divImage = $('<div/>');
	var $divCaption = $('<div/>').addClass('caption deco');
	var $divName = $('<div/>').addClass('star comment');
	var $divFooter = $('<div/>').addClass('star right');
	var $divRibonText = $('<div/>').text($card.view + ' view');
	var $jqImg = $('<img/>').addClass('image-shot deco').attr('src',
			$card.imagePath);
	var $jqIcon = $('<img/>').addClass('image-icon')
			.attr('src', $card.userIcon);

	if ($url != null && $url != '') {
		$divRibonText.addClass('ribon-text color-blue');
		$jqDiv.addClass('cell2');
		$jqA1.attr('href',
				'jump.jsp?cardId=' + $card.cardId + '&redirectUrl=' + $url)
				.attr('target', '_blank');
		$divFooter.text($url);
	} else {
		$divName.text($card.userName);
		$divRibonText.addClass('ribon-text color-red');
		$jqDiv.addClass('cell');
		$jqA1.attr('href', 'card-comment.jsp?cardId=' + $card.cardId
				+ '&type=comment');
		$jqA2.attr('href', 'profile.jsp?userId=' + $card.userId);
		$divFooter.text($card.likes + ' comment').css('clear', 'both');
	}

	// construct
	$('#content').append($jqLi);
	$jqLi.append($jqA1.append($jqDiv));
	if ($card.view > 0) {
		$jqDiv.append($divRibon.append($divRibonText));
	}
	$jqDiv.append($divImage).append($divName).append($divCaption).append(
			$divFooter);
	$divImage.append($jqImg);
	$divCaption.text($card.caption).autoUrlLink().escapeReturn();
	$divName.append($jqA2);
	$jqA2.append($jqIcon);
}

function makeComment($comment) {

	// components
	var $jqLi = $('<li/>').addClass('card opacity80');
	var $jqDiv = $('<div/>')
			.attr('id', $comment.cardId + '+' + $comment.userId);
	var $jqA = $('<a/>').addClass('no-hover');

	var $divName = $('<div/>').addClass('star comment');
	var $divCaption = $('<div/>').addClass('caption comment deco');
	var $divFooter = $('<div/>').addClass('star comment right');
	var $jqIcon = $('<img/>').addClass('image-icon');

	$jqDiv.addClass('cell');
	if ($comment.userIcon != null && $comment.userIcon != '') {
		$jqIcon.attr('src', $comment.userIcon);
		$jqA.attr('href', 'profile.jsp?userId=' + $comment.userId);
	} else {
		$jqIcon.attr('src', $comment.cardIcon);
		$jqA.attr('href', 'card-comment.jsp?cardId=' + $comment.cardId
				+ '&type=comment');
	}
	$divName.text($comment.userName);
	$divCaption.append($comment.caption).autoUrlLink().escapeReturn();
	$divFooter.text($comment.createDate).aboutTimestamp();
	$divFooter.css('clear', 'both');

	// construct
	$('#content').append($jqLi);
	$jqLi.append($jqDiv);
	$jqDiv.append($divName).append($divCaption).append($divFooter);
	$divName.append($jqA);
	$jqA.append($jqIcon);
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

