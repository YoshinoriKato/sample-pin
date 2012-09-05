var $timer;

var $offset = 14;

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
	var $userId = $data.userId;
	var $interval = 3;
	$counter += $len;
	if ($len == 0) {
		teaDown();
		return;
	}

	$timer = setInterval(function() {
		$one = $array[$i];
		if ($type == 'comment') {
			makeComment($one, $userId);
		} else {
			makeCard($one);
			wookmark();
		}
		$i++;
		if ($i >= $len) {
			clearInterval($timer);
			$('#content').css('height', $(document).height() - 120);
			$('#read-cards').text('read ' + $counter + ' cards');
			if ($type != 'comment') {
				wookmark();
			}
			teaDown();
		}
	}, $interval);
};

function wookmark() {
	$('#content li').wookmark({
		offset : $offset
	});
};

var $cardWidth = 240;

function makeCard($card) {

	// components
	var $url = $card.url;
	var $height = $card.height;
	var $width = $card.width;
	var $rate = $width / $cardWidth;
	$height = Math.round($height / $rate);
	$width = $cardWidth;
	var $jqLi = $('<li/>').addClass('card');
	var $jqDiv = $('<div/>').attr('id', $card.cardId);
	var $jqA0 = $('<a/>').addClass('no-hover');
	var $jqA1 = $('<a/>').addClass('no-hover');
	var $jqA2 = $('<a/>').addClass('no-hover');
	var $jqA3 = $('<a/>').addClass('no-hover');
	$jqA3.attr(
			'href',
			'jump.jsp?cardId=' + $card.cardId + '&redirectUrl='
					+ encodeURIComponent($card.site)).attr('target', '_blank')
			.text('URL:' + $card.site);
	var $divRibon = $('<div/>').addClass('ribon');
	var $divImage = $('<div/>');
	var $divCaption = $('<div/>').addClass('caption deco');
	var $divName = $('<div/>').addClass('bold deco break-word');
	var $divBr = $('<br/>').css('clear', 'both');
	var $divKey = $('<div/>').addClass('card-info break-word')
			.text('Keywords:');
	var $spanKey = $('<span/>').text($card.keywords);
	var $divUrl = $('<div/>').addClass('card-info break-word').append($jqA3);
	var $divFooter = $('<div/>').addClass('star right');
	var $divRibonText = $('<div/>').text($card.view + ' view');
	var $jqImg = $('<img/>').addClass('image-shot deco').attr('src',
			$card.imagePath);
	if ($width)
		$jqImg.attr('width', $width);
	if ($height)
		$jqImg.attr('height', $height);
	var $jqIcon = $('<img/>').addClass('image-icon')
			.attr('src', $card.userIcon);

	if ($url != null && $url != '') {
		$divRibonText.addClass('ribon-text color-blue');
		$jqDiv.addClass('cell2');
		$link = 'jump.jsp?cardId=' + $card.cardId + '&redirectUrl=' + $url;
		$jqA0.attr('href', $link).attr('target', '_blank');
		$jqA1.attr('href', $link).attr('target', '_blank');
		$divFooter.text($url);
		
	} else {
		$divName.text($card.userName);
		$divRibonText.addClass('ribon-text color-red');
		$jqDiv.addClass('cell');
		$link = 'card-comment.jsp?cardId=' + $card.cardId + '&type=comment';
		$jqA0.attr('href', $link);
		$jqA1.attr('href', $link);
		$jqA2.attr('href', 'profile.jsp?userId=' + $card.userId);
		$divFooter.text($card.likes + ' comment').css('clear', 'both');
	}

	// construct
	$('#content').append($jqLi);
	$jqLi.append($jqDiv);
	if ($card.view > 0) {
		$jqDiv.append($jqA0.append($divRibon.append($divRibonText)));
	}
	$jqDiv.append($jqA1.append($divImage)).append($divName).append($divCaption)
			.append($divBr);
	if ($card.keywords) {
		$jqDiv.append($divKey);
		$divKey.append($spanKey.convLink());
	}
	if ($card.site) {
		$jqDiv.append($divUrl);
	}
	$jqDiv.append($divFooter);
	$divImage.append($jqImg);
	$divCaption.text($card.caption).autoUrlLink().escapeReturn();
	$divName.append($jqA2);
	$jqA2.append($jqIcon);
}

function makeComment($comment, $userId) {

	// components
	var $jqLi = $('<li/>').addClass('opacity80 margin-bottom20');
	var $jqDiv = $('<div/>')
			.attr('id', $comment.cardId + '+' + $comment.userId);
	var $jqA = $('<a/>').addClass('no-hover');

	var $divName = $('<div/>').addClass('bold deco comment');
	var $divCaption = $('<div/>').addClass('caption comment deco');
	var $divFooter = $('<div/>').addClass('star comment right');
	var $jqIcon = $('<img/>').addClass('image-icon');
	var $jqClose = $('<div/>').addClass('close-button');

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

	// delete
	if ($userId && $userId == $comment.userId) {
		var $confirm = $('<a/>').attr(
				'href',
				'confirm-discomment.jsp?cardId=' + $comment.cardId + '&userId='
						+ $comment.userId + '&createDate='
						+ $comment.createDate).text('x');
		$jqDiv.append($jqClose);
		$jqClose.append($confirm);
	}
	$divName.append($jqA);
	$jqA.append($jqIcon);
}

function callAjax($sorted, $limit, $offset, $userId, $cardId, $type, $words) {
	if (!$block) {
		$block = true;
		$.ajax({
			cache : false,
			type : 'post',
			scriptCharset : 'UTF-8',
			url : 'xxx.do',
			data : {
				name : 'home.jsp',
				key : '0381075127472',
				sorted : $sorted,
				offset : $offset,
				limit : $limit,
				userId : $userId,
				cardId : $cardId,
				type : $type,
				words : $words
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

