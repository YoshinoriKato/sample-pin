function callback(array) {
	for ( var i = 0; i < array.length; i++) {
		var $jqLi = $("<li/>");
		var $jqDiv = $("<div/>").addClass("cell");
		var $jqA = $("<a/>").addClass("no-hover").attr("href",
				"card.jsp?cardId=" + array[i].cardId);
		var $divRibon = $("<div/>").addClass("ribon");
		var $divImage = $("<div/>").addClass("image-holder");
		var $divCaption = $("<div/>").addClass("caption deco");
		var $divStar = $("<div/>").addClass("star right");

		$jqA.append($("<img/>").addClass("image-shot deco").attr("src",
				array[i].url));
		$divRibon.append($("<span/>").addClass("ribon-text").text(
				array[i].view + " view"));
		$divCaption.text(array[i].caption);
		$divImage.append($jqA);
		$divStar.text("★" + array[i].likes);

		if (array[i].view > 0) {
			$jqDiv.append($divRibon);
		}
		$jqDiv.append($divImage).append($divCaption).append($divStar);
		$jqLi.append($jqDiv);

		$('#content').append($jqLi);
	}
	$('#content li').wookmark({
		offset : 12
	});
};
