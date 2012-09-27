
//複数の場所からwindow.onloadを呼ぶことができる
function attach(obj, eve, func) {
	if (obj.attachEvent) {
		obj.attachEvent('on' + eve, func);
	} else {
		obj.addEventListener(eve, func, false);
	}
}

function checkLength($id, $key) {
	$text = $($id).val();
	if ($text != null) {
		if ($text.length > 0) {
			$('#submit-button').removeAttr('disabled');
			var $storage = sessionStorage;
			$storage.setItem($key, $text);
		} else {
			$('#submit-button').attr('disabled', 'disabled');
			removeText($key);
		}
	}
};

function recoveryText($id, $key) {
	var $storage = sessionStorage;
	$($id).val($storage.getItem($key));
};

function removeText($key) {
	var $storage = sessionStorage;
	$storage.removeItem($key);
};

function pushPull($on, $off) {
	pushPull($on, $off, 1000);
};

function observeText($id, $key) {
	recoveryText($id, $key);
	$timer0 = setInterval(function() {
		checkLength($id, $key);
	}, 500);
}


function pushPull($on, $off, $duration) {
	$($off).fadeOut($duration);
	$($on).fadeIn($duration);
};
