function callback(array) {
	for ( var i = 0; i < array.length; i++) {
		var $jqLi = $("<li/>");
		var $jqDiv = $("<div/>").addClass("cell");
		var $jqA = $("<a/>").addClass("no-hover").attr("href",
				"card.jsp?cardId=" + array[i].cardId).text("card");
		var $divCard = $("<div/>").addClass("caption");
		var $divName = $("<div/>").addClass("caption").text(
				"@" + array[i].userId);
		var $divCaption = $("<div/>").addClass("caption deco");
		var $divDate = $("<div/>").addClass("caption right");

		$divCaption.text(array[i].comment);
		$divCard.append($jqA);
		$divDate.text(array[i].createDate);

		$jqDiv.append($divCard).append($divName).append($divCaption).append(
				$divDate);
		$jqLi.append($jqDiv);

		$('#content').append($jqLi);
	}
	$('#content li').wookmark({
		offset : 12
	});
};
