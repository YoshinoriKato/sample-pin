

function pushPull(on, off) {
	pushPull(on, off, 1000);
};

function pushPull(on, off, duration) {
	$(off).fadeOut(duration);
	$(on).fadeIn(duration);
};

