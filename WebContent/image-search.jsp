<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {
		$('#main').fadeIn(1000);
		$('#ajax').fadeOut(1000);
	});
</script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" charset="UTF-8">
	function searchGoogle() {
		var imageSearch = new google.search.ImageSearch();
		// 検索完了時に呼び出されるコールバック関数を登録する 
		imageSearch.setSearchCompleteCallback(this, SearchComplete,
				[ imageSearch ]); // 検索を実行する
		imageSearch.setRestriction(
				google.search.ImageSearch.RESTRICT_IMAGESIZE,
				google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_JPG,
				google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_GIF,
				google.search.ImageSearch.RESTRICT_FILETYPE.FILETYPE_PNG);
		imageSearch.execute($('#search-box').val());
	};
	function OnLoad() {
	};
	function SearchComplete(searcher) {
		var $results = searcher.results;
		if ($results && (0 < $results.length)) {
			var $content = $('#content');
			$content.empty();
			for ( var i = 0; i < $results.length; i++) {
				var $li = $('<li/>').addClass('card');
				var $cell = $('<div/>').addClass('cell');
				var $a = $('<a/>')
						.attr(
								'href',
								'upload.do?url='
										+ encodeURIComponent($results[i].url)
										+ '&keywords='
										+ encodeURIComponent($('#search-box')
												.val())
										+ '&site='
										+ encodeURIComponent($results[i].originalContextUrl));
				var $title = $('<div/>').addClass('caption').append(
						$results[i].title);
				var $image = $('<img/>').attr('src', $results[i].tbUrl)
						.addClass('image-shot');
				$content.append($li);
				$li.append($cell);
				$cell.append($a).append($title);
				$a.append($image);
			}
			wookmark();
		}
	}
	google.load('search', '1');
	google.setOnLoadCallback(OnLoad);
</script>

</head>

<body>
	<jsp:include page="_topbar.jsp"></jsp:include>
	<div id="float-bottun" class="page-menu opacity80">
		<form class="form-search" action="javascript:searchGoogle()">
			<input type="search" id="search-box" value="" placeholder="Keyword"
				class="input-medium span6"> <input type="submit"
				value="Search" id="search-action"
				class="btn btn-large btn-primary btn-cell">
		</form>
	</div>
	<div id="main">
		<ul id="content">
			<!--  ajax -->
		</ul>
		<br style="clear: both;" />
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>