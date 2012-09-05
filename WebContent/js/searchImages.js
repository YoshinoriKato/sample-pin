var $serachBox = '#search-box';

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
	imageSearch.execute($($serachBox).val());
};

function OnLoad() {
};

var $max_height = 200;

function SearchComplete(searcher) {
	var $results = searcher.results;
	if ($results && (0 < $results.length)) {
		var $content = $('#search-result');
		$content.empty();
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

function makeImageCell($content, $result) {
	var $li = $('<li/>').addClass('search-thumb');
	var $cell = $('<div/>').addClass('cell');
	var $capture = $('<div/>').addClass('float-left');
	var $a = $('<a/>').attr(
			'href',
			'upload.do?url=' + encodeURIComponent($result.url) + '&keywords='
					+ encodeURIComponent($($serachBox).val()) + '&site='
					+ encodeURIComponent($result.originalContextUrl));
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
	var $a = $('<a/>').attr(
			'href',
			'upload.do?url=' + encodeURIComponent($result.url) + '&keywords='
					+ encodeURIComponent($($serachBox).val()) + '&site='
					+ encodeURIComponent($result.originalContextUrl));
	var $title = $('<div/>').addClass('caption').append($result.title);
	var $image = $('<img/>').attr('src', $result.tbUrl).addClass('image-shot');
	$content.append($li);
	$li.append($cell);
	$cell.append($a).append($title);
	$a.append($image);
}

google.load('search', '1');

google.setOnLoadCallback(OnLoad);
