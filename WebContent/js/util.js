function checkLength() {
	if ($('#comment-text').val() != null) {
		if ($('#comment-text').val().length > 0) {
			$('#submit-button').removeAttr('disabled');
		} else {
			$('#submit-button').attr('disabled', 'disabled');
		}
	}
};

function pushPull($on, $off) {
	pushPull($on, $off, 1000);
};

function pushPull($on, $off, $duration) {
	$($off).fadeOut($duration);
	$($on).fadeIn($duration);
};
