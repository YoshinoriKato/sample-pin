
var $KEY_SEARCH_BOX = '#search-box';

var $KEY_PARENT_ID = '#parentId';

var $calledAPI = false;

var $rememmber = "";

function searchKeywords(){
	/* search */
	var $key = 'search-box';
	observeText($KEY_SEARCH_BOX, $key);
	$timer0 = setInterval(function() {
		checkLength($KEY_SEARCH_BOX, $key);
//		if($rememmber != $($KEY_SEARCH_BOX).val()){
//			getKeywords();
//			$rememmber = $($KEY_SEARCH_BOX).val();
//		}
	}, 500);
}

function getKeywords(){
	if (!$calledAPI) {
		$calledAPI = true;
		$.ajax({
//			headers : {'Access-Control-Allow-Origin' : '*'},
			cache : false,
			type : 'GET',
			scriptCharset : 'UTF-8',
			url : 'http://labs.preferred.jp/reflexa/api.php?q='
					+ $($KEY_SEARCH_BOX).val() + "&format=json",
			data : {
			},
			success : function(data) {
				alert(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus);
				alert(errorThrown);
				$('#ajax').fadeOut(10);
				$('#error-dialog').fadeIn(10);
				$calledAPI = false;
			},
			dataType : 'jsonp'
		});
//		$.get('http://labs.preferred.jp/reflexa/api.php?q='
//				+ $($KEY_SEARCH_BOX).val() + "&format=json", function($data) {
//					alert($data);
//				});
	}
}

function setKeywords($data){
	alert($data);
	for(var $i = 0; $i < $data.length; $i++) {
		alert($data[$i]);
	}
	$calledAPI = false;
}

function searchGoogle() {
	$('#ajax').fadeIn(10);
	var imageSearch = new google.search.ImageSearch();
	imageSearch
			.setSearchCompleteCallback(this, SearchComplete, [ imageSearch ]);
	imageSearch.setRestriction(google.search.ImageSearch.RESTRICT_IMAGESIZE,
			google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_JPG,
			google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_GIF,
			google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_PNG);
	imageSearch.setResultSetSize(google.search.Search.LARGE_RESULTSET);
	imageSearch.execute($($KEY_SEARCH_BOX).val());
};

function OnLoad() {
};

var $max_height = 200;

function SearchComplete(searcher) {
	var $results = searcher.results;
	if ($results && (0 < $results.length)) {
		var $content = $('#search-result');
		$content.empty();
		$seq = 0;
		for ( var i = 0; i < $results.length; i++) {
			makeImageCell($content, $results[i]);
		}
		$('#ajax').fadeOut(1000);

		// $('#search-result li').wookmark({
		// offset : 12
		// });
		//
		// $content.height($max_height);
	}
}

var $seq = 0;

function makeImageCell($content, $result) {
	var $formId = 'id_' + $seq++;
	var $li = $('<li/>').addClass('search-thumb');
	var $cell = $('<div/>').addClass('cell');
	var $capture = $('<div/>').addClass('float-left');
	var $parentId = $($KEY_PARENT_ID).text();
	$parentId = ($parentId != null && $parentId != '') ? '&parentId=' + $parentId : '';
	var $a2 = $('<a/>').attr('href', $result.originalContextUrl).attr('target',
			'_blank');
	var $caption = $('<div/>').addClass('caption star');
	var $image = $('<img/>').attr('src', $result.tbUrl).addClass('image-thumb');
	var $clear = $('<br/>').css('clear', 'both');

	var $form = $('<form/>').attr('id', $formId).attr('method', 'post').attr('action', 'upload.do');
	var $inUrl = $('<input/>').attr('type', 'hidden').attr('name','url').attr('value', encodeURIComponent($result.url));
	var $inKeywords = $('<input/>').attr('type', 'hidden').attr('name','keywords').attr('value', encodeURIComponent($($KEY_SEARCH_BOX).val()));
	var $inSite = $('<input/>').attr('type', 'hidden').attr('name','site').attr('value', encodeURIComponent($result.originalContextUrl));
	var $inParent = $('<input/>').attr('type', 'hidden').attr('name','parentId').attr('value', $parentId);
	var $submit = $('<a/>').attr('href', 'javascript:$(' + $formId + ').submit();');
	$form.append($inUrl).append($inKeywords).append($inSite).append($inParent).append($submit);

	$content.append($li);
	$li.append($cell);
	$caption.append($a2);
	$a2.append('[' + $result.width + 'x' + $result.height + '] '
			+ $result.title);
	
	$cell.append($capture).append($caption).append($clear);
	$capture.append($form);
	$submit.append($image);

	// $view_height = Math.round($result.height / ($result.width / 120)) + 200;
	// $max_height = ($max_height >= $view_height) ? $max_height : $view_height;
}

function makeImageCell1($content, $result) {
	var $li = $('<li/>').addClass('search-thumb');
	var $cell = $('<div/>').addClass('cell');
	var $capture = $('<div/>').addClass('float-left');
	var $parentId = $($KEY_PARENT_ID).text();
	$parentId = ($parentId != null && $parentId != '') ? '&parentId=' + $parentId : '';
	var $a = $('<a/>').attr(
			'href',
			'upload.do?url=' + encodeURIComponent($result.url) + '&keywords='
					+ encodeURIComponent($($KEY_SEARCH_BOX).val()) + '&site='
					+ encodeURIComponent($result.originalContextUrl) + $parentId);
	var $a2 = $('<a/>').attr('href', $result.originalContextUrl).attr('target',
			'_blank');
	var $caption = $('<div/>').addClass('caption star');
	var $image = $('<img/>').attr('src', $result.tbUrl).addClass('image-thumb');
	var $clear = $('<br/>').css('clear', 'both');
	$content.append($li);
	$li.append($cell);
	$cell.append($capture).append($caption).append($clear);
	$capture.append($a);
	$caption.append($a2);
	$a.append($image);
	$a2.append('[' + $result.width + 'x' + $result.height + '] '
			+ $result.title);

	// $view_height = Math.round($result.height / ($result.width / 120)) + 200;
	// $max_height = ($max_height >= $view_height) ? $max_height : $view_height;
}

function makeImageCell0($content, $result) {
	var $li = $('<li/>').addClass('card');
	var $cell = $('<div/>').addClass('cell');
	var $parentId = $($KEY_PARENT_ID).text();
	$parentId = ($parentId != null && $parentId != '') ? '&parentId=' + $parentId : '';

	var $form = $('<form/>').attr('method', 'post').attr('action', 'upload.do');
	var $inUrl = $('<input/>').attr('type', 'hidden').attr('name','url').attr('value', encodeURIComponent($result.url));
	var $inKeywords = $('<input/>').attr('type', 'hidden').attr('name','keywords').attr('value', encodeURIComponent($($KEY_SEARCH_BOX).val()));
	var $inSite = $('<input/>').attr('type', 'hidden').attr('name','site').attr('value', encodeURIComponent($result.originalContextUrl));
	var $inParent = $('<input/>').attr('type', 'hidden').attr('name','parentId').attr('value', $parentId);
	var $title = $('<div/>').addClass('caption').append($result.title);
	var $image = $('<img/>').attr('src', $result.tbUrl).addClass('image-shot');
	var $submit = $('<a/>').attr('href', 'javascript:submit();');
	$form.append($inUrl).append($inKeywords).append($inSite).append($inParent).append($submit);
	$content.append($li);
	$li.append($cell);
	$cell.append($form).append($title);
	$submit.append($image);
}

google.load('search', '1');

google.setOnLoadCallback(OnLoad);
