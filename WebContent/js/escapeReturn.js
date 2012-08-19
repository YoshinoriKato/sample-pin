

$.fn.escapeReturn = function($text) {
	return this.each(function() {
		var srcText = this.innerHTML;
		this.innerHTML = srcText.replace(/(\r\n|\n\r|\r|\n)/g, '<br />');
	});
};

