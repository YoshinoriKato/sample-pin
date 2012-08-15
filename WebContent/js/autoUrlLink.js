
//http://www.openspc2.org/JavaScript/Ajax/jQuery_plugin/chapter8/index.html

$.fn.autoUrlLink = function(baseURL) {
	return this.each(function() {
		var srcText = this.innerHTML;
		srcText = srcText.replace(/(http:\/\/[\x21-\x7e]+)/gi,
		"<a href='$1' target='_blank'>$1</a>");
		this.innerHTML = srcText.replace(/(https:\/\/[\x21-\x7e]+)/gi,
		"<a href='$1' target='_blank'>$1</a>");
	});
};