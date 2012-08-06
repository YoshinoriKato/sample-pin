<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>販売商品ページ</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript" src="jquery.wookmark.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#content li').wookmark({
			offset : 2
		//要素間の余白
		});
	});
</script>

<style type="text/css">
#content {
	position: relative;
}

body {
	background-color: #eee;
	background-image: url("wallpaper.png");
	text-shadow: 1px 1px 0px white;
}

.pic {
	width: 200px;
	height: 200px;
}

.btn {
	width: 200px;
	height: 40px;
	border-radius: 10px; /* CSS3草案 */
	-webkit-border-radius: 10px; /* Safari,Google Chrome用 */
	-moz-border-radius: 10px; /* Firefox用 */
}

.gradient {
	background: -moz-linear-gradient(top, rgba(60, 100, 50, 0.5), #066 );
	background: -webkit-gradient(linear, left top, left bottom, from(rgba(60, 100, 50,
		0.5) ), to(#066) );
	padding: 20px;
}

.pop {
	border: 1px solid white;
	background-color: rgba(255, 255, 255, 0.25);
	padding-top: 10px;
	padding-bottom: 10px;
	padding-left: 20px;
	padding-right: 20px;
	border-radius: 2px 20px 2px; /* CSS3草案 */
	-webkit-border-radius: 2px 20px 2px; /* Safari,Google Chrome用 */
	-moz-border-radius: 2px 20px 2px; /* Firefox用 */
	margin-bottom: 20px;
}

.picture {
	width: 600px;
}

.action {
	width: 600px;
}

.span1 {
	width: 100px;
}

.span2 {
	width: 200px;
}

.span3 {
	width: 300px;
}

.span4 {
	width: 400px;
}

.span5 {
	width: 500px;
}

.span6 {
	width: 600px;
}

.span7 {
	width: 700px;
}

.span8 {
	width: 800px;
}

.span9 {
	width: 900px;
}

.span10 {
	width: 1000px;
}

.span11 {
	width: 1100px;
}

.span12 {
	width: 1200px;
}
</style>
</head>

<body>
	あとは上記で指定した要素を記述すれば完成です。
	<ul id="content">
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
		<li><img src="" class="pic">hoge</li>
	</ul>

	<div class="header">
		<h2>商品名</h2>
	</div>
	<div class="body">
		<div class="pop span8">
			<div class="picture span4">
				<img src="" class="pic">
			</div>
			<div class="action span4">
				<input type="submit" value="購入？" class="btn">
			</div>
		</div>
		<div class="explain pop span8">
			<h3>商品の説明</h3>
			<p>こちらの商品は……………………………………………………………………………</p>
		</div>
		<div class="buyer pop span8">
			<h3>販売者の説明</h3>
			<p>こちらの商品の販売者は……………………………………………………………</p>
		</div>
	</div>
	<div class="footer">
		<h4>運営</h4>
	</div>
</body>

</html>