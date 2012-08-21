
var $serachBox = '#search-box';

function searchGoogle() {
	var imageSearch = new google.search.ImageSearch();
	imageSearch
			.setSearchCompleteCallback(this, SearchComplete, [ imageSearch ]);
	imageSearch.setRestriction(google.search.ImageSearch.RESTRICT_IMAGESIZE,
			google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_JPG,
			google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_GIF,
			google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_PNG);
	imageSearch.execute($($serachBox).val());
};

function OnLoad() {
};

var $max_height = 320;

function SearchComplete(searcher) {
	var $results = searcher.results;
	if ($results && (0 < $results.length)) {
		var $content = $('#search-result');
		$content.empty();
		for ( var i = 0; i < $results.length; i++) {
			makeImageCell($content, $results[i]);
		}
		
		$('#search-result li').wookmark({
			offset : 12
		});
		
		var len = $content.length;
		for(i = 0; i < len; i++){
			$li = $content.get(i);
			$max_height = $max_height < $li.height ? $li.height : $max_height;
		}
		$content.height($max_height + 20);
	}
}

function makeImageCell($content, $result) {
	var $li = $('<li/>').addClass('search-thumb');
	var $cell = $('<div/>').addClass('cell');
	var $a = $('<a/>').attr(
			'href',
			'upload.do?url=' + encodeURIComponent($result.url) + '&keywords='
					+ encodeURIComponent($($serachBox).val()) + '&site='
					+ encodeURIComponent($result.originalContextUrl));
	var $title = $('<div/>').addClass('caption').append($result.title);
	var $image = $('<img/>').attr('src', $result.tbUrl).addClass('image-thumb');
	$content.append($li);
	$li.append($cell);
	$cell.append($a).append($title);
	$a.append($image);
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