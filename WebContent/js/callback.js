var $timer;

var $offset = 12;

var $counter = 0;

var $block = false;

function callback($array) {
	var $i = 0;
	var $len = $array.length;
	var $interval = 3;
	$counter += $len;

	$timer = setInterval(function() {
		makeCell($array[$i]);
		wookmark();
		$i++;
		if ($i >= $len) {
			clearInterval($timer);
			wookmark();
			$('#content').css('height', $(document).height() - 100);
			$('#read-cards').text('read ' + $counter + ' cards');
			$('#ajax').fadeOut(1000);
			$block = false;
		}
	}, $interval);
};

function wookmark() {
	$('#content li').wookmark({
		offset : $offset
	});
};

function makeCell($card) {

	// components
	var $url = $card.url;
	var $jqLi = $('<li/>');
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
		$jqA.attr('href', 'card.jsp?cardId=' + $card.cardId);
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
	$divCaption.text($card.caption).autoUrlLink();
}

function callAjax($sorted, $limit, $offset) {
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
				limit : $limit
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
