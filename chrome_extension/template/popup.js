(function() {
	var Popup = Class.create({
		initialize : function() {
			this.bg = chrome.extension.getBackgroundPage().bg;
			window.addEventListener("load", function(evt) {
				this.start();
			}.bind(this));
		},
		start : function() {
			this.assignMessages();
			this.assignEventHandlers();
		},
		assignMessages : function() {
			var hash = {
				"popup***" : "popup***"
			};
			for ( var key in hash) {
				$(key).innerHTML = chrome.i18n.getMessage(hash[key]);
			}
		},
		assignEventHandlers : function() {
			$("***").onclick = this.onClickYYYY.bind(this);
		},
		onClickYYYY : function(evt) {
			// 設定値を取得
			var YYYYConfig = this.bg.getYYYYConfig();
			// Ajax通信
			this.bg.loadYYYY({
				onSuccess : function(res) {
				}.bind(this)
			});
		}
	});
	new Popup();
})();