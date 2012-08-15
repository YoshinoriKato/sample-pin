<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		$('#content li').wookmark({
			offset : 12
		});
		$(window).resize(function() {
			$('#content li').wookmark({
				offset : 12
			})
		});
	});
</script>
</head>


<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="main">
		<ul id="content">
			<%
				Random r = new Random(System.nanoTime());
				for (int i = 0; i < 200; i++) {
					int h = 60 + r.nextInt(300);
					int stars = r.nextInt(40);
					int views = r.nextInt(50);
			%>
			<li><div class="cell">
					<%
						if (views != 0) {
					%>
					<div class="ribon">
						<span class="ribon-text"><%=views%> view</span>
					</div>
					<%
						}
					%>
					<div class="center stars star">
						<%
							if (views != 0)
									if (stars > 5) {
						%>
						★
						<%=stars%>
						<%
							} else {
										for (int j = 0; j < stars; j++) {
						%>
						★
						<%
							}
									}
						%>
					</div>
					<%
						int cap_min = 10;
							String caption = "雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ"
									+ " 慾ハナク 決シテ瞋ラズ イツモシヅカニワラッテヰル"
									+ " 一日ニ玄米四合ト 味噌ト少シノ野菜ヲタベ"
									+ " アラユルコトヲ ジブンヲカンジョウニ入レズニ ヨクミキキシワカリ ソシテワスレズ"
									+ " 野原ノ松ノ林ノノ 小サナ萓ブキノ小屋ニヰテ"
									+ " 東ニ病気ノコドモアレバ 行ッテ看病シテヤリ"
									+ " 西ニツカレタ母アレバ 行ッテソノ稲ノ朿ヲ負ヒ"
									+ " 南ニ死ニサウナ人アレバ 行ッテコハガラナクテモイヽトイヒ"
									+ " 北ニケンクヮヤソショウガアレバ ツマラナイカラヤメロトイヒ"
									+ " ヒドリノトキハナミダヲナガシ サムサノナツハオロオロアルキ"
									+ " ミンナニデクノボートヨバレ ホメラレモセズ クニモサレズ"
									+ " サウイフモノニ ワタシハナリタイ"
									+ " 南無無辺行菩薩 南無上行菩薩 南無多宝如来 南無妙法蓮華経 南無釈迦牟尼仏 南無浄行菩薩 南無安立行菩薩";
							caption = caption.substring(0,
									r.nextInt(caption.length() - cap_min) + cap_min);
					%>
					<div class="image-holder">
						<img src="img/6023677971_1a47ac6105_o.jpg" class="image-shot deco">
					</div>
					<div class="caption deco">
						<h4>
							<%=caption%>
							<%=i + 1%></h4>
					</div>
				</div></li>
			<%
				}
			%>
		</ul>
		<br style="clear: both;" />
	</div>
</body>

</html>