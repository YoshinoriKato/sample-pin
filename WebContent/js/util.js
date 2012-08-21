function checkLength($key) {
	$text = $('#comment-text').val();
	if ($text != null) {
		if ($text.length > 0) {
			$('#submit-button').removeAttr('disabled');
			var $storage = sessionStorage;
			$storage.setItem($key, $text);
		} else {
			$('#submit-button').attr('disabled', 'disabled');
		}
	}
};

function recoveryText($key) {
	var $storage = sessionStorage;
	$('#comment-text').val($storage.getItem($key));
}

function removeText($key) {
	var $storage = sessionStorage;
	$storage.removeItem($key);
}

function pushPull($on, $off) {
	pushPull($on, $off, 1000);
};

function pushPull($on, $off, $duration) {
	$($off).fadeOut($duration);
	$($on).fadeIn($duration);
};
