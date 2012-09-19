
<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {	
		$('#sns-close').attr("onclick",
		"pushPull('#null','#sns-link')");
	});
</script>

<!-- twitter -->
<script type="text/javascript" charset="UTF-8">
	!function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (!d.getElementById(id)) {
			js = d.createElement(s);
			js.id = id;
			js.src = "//platform.twitter.com/widgets.js";
			fjs.parentNode.insertBefore(js, fjs);
		}
	}(document, "script", "twitter-wjs");
</script>

<!-- facebook -->
<div id="fb-root"></div>
<script type="text/javascript" charset="UTF-8">
	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/ja_JP/all.js#xfbml=1";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
</script>

<!-- mixi -->
<script type="text/javascript" charset="UTF-8">
	(function(d) {
		var s = d.createElement('script');
		s.type = 'text/javascript';
		s.async = true;
		s.src = '//static.mixi.jp/js/plugins.js#lang=ja';
		d.getElementsByTagName('head')[0].appendChild(s);
	})(document);
</script>

<!-- google+ -->
<!-- Place this tag after the last +1 button tag. -->
<script type="text/javascript">
	window.___gcfg = {
		lang : 'ja'
	};

	(function() {
		var po = document.createElement('script');
		po.type = 'text/javascript';
		po.async = true;
		po.src = 'https://apis.google.com/js/plusone.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(po, s);
	})();
</script>

<div id="sns-link">
	<div id="sns-close" class="close-button">x</div>
	<div id="twitter-button">
		<a href="https://twitter.com/share" class="twitter-share-button"
			data-lang="ja">Twitter</a>
	</div>
	
	<div id="facebook-button" class="margin-top20">
		<div class="fb-like" data-href="http://doya.info/" data-send="false"
			data-layout="button_count" data-width="450" data-show-faces="false"></div>
	</div>
	
	<div id="mixi-button" class="margin-top20">
		<div data-plugins-type="mixi-favorite"
			data-service-key="bf28d3d3db4ee8eeb397cac5038c541e30c1dd65"
			data-size="medium" data-href="http://doya.info"
			data-show-faces="false" data-show-count="true"
			data-show-comment="false" data-width=""></div>
	</div>

	<div id="google-button" class="margin-top20">
		<!-- Place this tag where you want the +1 button to render. -->
		<div class="g-plusone" data-size="medium" data-href="http://doya.info"></div>
	</div>
</div>
