// Twitter 検索
chrome.contextMenus.create({
	"title" : "「%s」をDOYA.infoで検索",
	"type" : "normal",
	"contexts" : [ "selection" ],
	"onclick" : function(info) {
		var url = 'http://doya.info/home.jsp?sorted=search&words='
				+ encodeURIComponent(info.selectionText);
		chrome.tabs.create({
			url : url
		});
	}
});
