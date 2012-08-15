var $timer;

function callback($array) {
	var $i = 0;
	var $len = $array.length;

	$timer = setInterval(function() {
		makeCell($array[$i]);
		$('#content li').wookmark({
			offset : 20
		});
		$i++;
		if ($i >= $len) {
			clearInterval($timer);
			$('#cover').fadeOut(1000);
		}
	}, 10);
};

function makeCell($card) {
	
	var $url = $card.url;
	var $jqLi = $("<li/>");
	var $jqDiv = $("<div/>");
	var $jqA = $("<a/>").addClass("no-hover");
	var $divRibon = $("<div/>").addClass("ribon");
	var $divImage = $("<div/>");
	var $divCaption = $("<div/>").addClass("caption deco");
	var $divFooter = $("<div/>").addClass("star right");
	var $divRibonText = $("<div/>").addClass("ribon-text").text(
			$card.view + " view");
	var $jqImg = $("<img/>").addClass("image-shot deco").attr("src",
			$card.imagePath);

	if ($url != null && $url != "") {
		$jqDiv.addClass("cell2");
		$jqA.attr("href",
				"jump.jsp?cardId=" + $card.cardId + "&redirectUrl=" + $url)
				.attr("target", "_blank");
		$divFooter.text($url);
	} else {
		$jqDiv.addClass("cell");
		$jqA.attr("href", "card.jsp?cardId=" + $card.cardId);
		$divFooter.text($card.likes + " comment");
	}

	$('#content').append($jqLi);
	$jqLi.append($jqDiv);
	if ($card.view > 0) {
		$jqDiv.append($divRibon.append($divRibonText));
	}
	$jqDiv.append($divImage).append($divCaption).append($divFooter);
	$divImage.append($jqA.append($jqImg));
	$divCaption.text($card.caption).autoUrlLink();
}

function callAjax($sorted) {
	$.ajax({
		cache : false,
		type : 'post',
		scriptCharset : 'UTF-8',
		contentType : 'text/javascript+json; charset=utf-8',
		url : 'xxx.do' + $sorted,
		data : {
			name : 'index.jsp',
			key : '0381075127472',
			sorted : $sorted
		},
		success : callback,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$('#cover').fadeOut(10);
			$('#cover2').fadeIn(10);
		},
		dataType : 'json'
	});
};