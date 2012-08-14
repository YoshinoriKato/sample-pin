function callback(array) {
	for ( var i = 0; i < array.length; i++) {
		var $url = array[i].url;
		var $jqLi = $("<li/>");
		var $jqDiv = $("<div/>");
		var $jqA = $("<a/>").addClass("no-hover");
		var $divRibon = $("<div/>").addClass("ribon");
		var $divImage = $("<div/>");
		var $divCaption = $("<div/>").addClass("caption deco");
		var $divStar = $("<div/>").addClass("star right");

		if ($url != null && $url != "") {
			$jqDiv.addClass("cell2");
			$jqA.attr("href", "jump.jsp?cardId=" + array[i].cardId
					+ "&redirectUrl=" + $url).attr("target", "_blank");
			$divStar.text($url);
		} else {
			$jqDiv.addClass("cell");
			$jqA.attr("href", "card.jsp?cardId=" + array[i].cardId);
			$divStar.text(array[i].likes + " comment");
		}
		$divRibon.append($("<span/>").addClass("ribon-text").text(
				array[i].view + " view"));
		$jqA.append($("<img/>").addClass("image-shot deco").attr("src",
				array[i].imagePath));
		$divCaption.text(array[i].caption);
		$divImage.append($jqA);

		if (array[i].view > 0) {
			$jqDiv.append($divRibon);
		}
		$jqDiv.append($divImage).append($divCaption).append($divStar);
		$jqLi.append($jqDiv);

		$('#content').append($jqLi);
	}
	$('#content li').wookmark({
		offset : 20
	});
};


function pushWall() {
	$("#cover").css("display", "none");
};

function pushPhoto() {
	alert("push");
	$("#cover").css("display", "block");
};


