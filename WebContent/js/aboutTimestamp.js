

$.fn.aboutTimestamp = function() {
	return this.each(function() {
		var time = this.innerHTML;
		var mills = new Date() - 0;
		var gap = mills - time;
		var second = 1000;
		var minute = second * 60;
		var hour = minute * 60;
		var day = hour * 24;
		var ret = '???';
		var date = new Date();
		date.setTime(time);

		if (gap < 0) {
			var dateFormat = new DateFormat("yyyy/MM/dd 'at' HH");
			ret = dateFormat.format(date);
		} else if (minute > gap) {
			ret = ((gap - gap % second) / second) + ' seconds';

		} else if (hour > gap) {
			ret = ((gap - gap % minute) / minute) + ' minutes';

		} else if ((day * 2) > gap) {
			ret = ((gap - gap % hour) / hour) + ' hours';

		} else {
			var dateFormat = new DateFormat("yyyy-MM-dd");
			ret = dateFormat.format(date);
		}
		this.innerHTML = ret;
	});
};