function checkLength() {
	if ($('#comment-text').val().length > 0) {
		$('#submit-button').removeAttr('disabled');
	} else {
		$('#submit-button').attr('disabled', 'disabled');
	}
};

function pushPull($on, $off) {
	pushPull($on, $off, 1000);
};

function pushPull($on, $off, $duration) {
	$($off).fadeOut($duration);
	$($on).fadeIn($duration);
};

$.fn.escapeReturn = function($text) {
	return this.each(function() {
		var srcText = this.innerHTML;
		this.innerHTML = srcText.replace(/(\r\n|\n\r|\r|\n)/g, '<br />');
	});
};

$.fn.aboutTimestamp = function() {
	return this.each(function() {
		var ret = this.innerHTML;
		var mills = new Date() - 0;
		var gap = mills - ret;
		var second = 1000;
		var minute = second * 60;
		var hour = minute * 60;
		var day = hour * 24;

		if (gap < 0) {
			var dateFormat = new DateFormat("yyyy/MM/dd 'at' HH");
			ret = dateFormat.format(new Date());
		} else if (minute > gap) {
			ret = ((gap - gap % second) / second) + ' seconds';

		} else if (hour > gap) {
			ret = ((gap - gap % minute) / minute) + ' minutes';

		} else if ((day * 2) > gap) {
			ret = ((gap - gap % hour) / hour) + ' hours';

		} else {
			var dateFormat = new DateFormat("yyyy/MM/dd 'at' HH");
			ret = dateFormat.format(new Date());
		}
		this.innerHTML = ret;
	});
};