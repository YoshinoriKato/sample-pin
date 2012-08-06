<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>販売商品ページ</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript" src="jquery.wookmark.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#content li').wookmark({
			offset : 2
		});
		$(window).resize(function() {
			$('#content li').wookmark({
				offset : 12
			})
		});
	});
</script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css" rel="stylesheet">
</head>


<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href=".">Escrow</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="active"><a href=".">Components</a>
						</li>
						<li class="divider-vertical"></li>
						<li class=""><a href=".">Examples</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div id="main">
		<ul id="content">
			<%
				Random r = new Random(System.nanoTime());
				for (int i = 0; i < 50; i++) {
					int h = 100 + r.nextInt(500);
					int stars = r.nextInt(6);
			%>
			<li>
				<div class="center stars"><p>
					<%
						for (int j = 0; j < stars; j++) {
					%>
					★
					<%
						}
					%>
				</p></div>
				<div class="header deco">
					<h3>
						雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ<%=i + 1%></h3>
				</div>
				<div
					style="height: <%=h%>px; background-color: rgba(0, 0, 255, 0.05); margin: 10px;"></div>
				<div class="deco">
					<blockquote>雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ 慾ハナク
						決シテ瞋ラズ イツモシヅカニワラッテヰル 一日ニ玄米四合ト 味噌ト少シノ野菜ヲタベ アラユルコトヲ ジブンヲカンジョウニ入レズニ
						ヨクミキキシワカリ ソシテワスレズ 野原ノ松ノ林ノノ 小サナ萓ブキノ小屋ニヰテ 東ニ病気ノコドモアレバ 行ッテ看病シテヤリ
						西ニツカレタ母アレバ 行ッテソノ稲ノ朿ヲ負ヒ 南ニ死ニサウナ人アレバ 行ッテコハガラナクテモイヽトイヒ
						北ニケンクヮヤソショウガアレバ ツマラナイカラヤメロトイヒ ヒドリノトキハナミダヲナガシ サムサノナツハオロオロアルキ
						ミンナニデクノボートヨバレ ホメラレモセズ クニモサレズ サウイフモノニ ワタシハナリタイ 南無無辺行菩薩 南無上行菩薩
						南無多宝如来 南無妙法蓮華経 南無釈迦牟尼仏 南無浄行菩薩 南無安立行菩薩</blockquote>
				</div>
				<div>
					<span class="Ribon"><span class="ribon-text"><%=r.nextInt(100000)%>
							view</span></span>
				</div>
			</li>
			<%
				}
			%>
		</ul>
		<br style="clear: both;" />
	</div>
</body>

</html>