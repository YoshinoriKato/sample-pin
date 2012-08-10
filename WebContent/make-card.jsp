<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
</head>


<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<%
						Random r = new Random(System.nanoTime());
					%>
					<%
						int title_min = 5;
						String title = "雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ";
						title = title.substring(0, r.nextInt(title.length() - title_min)
								+ title_min);

						int comm_min = 20;
						String comment = "雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ"
								+ " 慾ハナク 決シテ瞋ラズ イツモシヅカニワラッテヰル" + " 一日ニ玄米四合ト 味噌ト少シノ野菜ヲタベ"
								+ " アラユルコトヲ ジブンヲカンジョウニ入レズニ ヨクミキキシワカリ ソシテワスレズ"
								+ " 野原ノ松ノ林ノノ 小サナ萓ブキノ小屋ニヰテ" + " 東ニ病気ノコドモアレバ 行ッテ看病シテヤリ"
								+ " 西ニツカレタ母アレバ 行ッテソノ稲ノ朿ヲ負ヒ"
								+ " 南ニ死ニサウナ人アレバ 行ッテコハガラナクテモイヽトイヒ"
								+ " 北ニケンクヮヤソショウガアレバ ツマラナイカラヤメロトイヒ"
								+ " ヒドリノトキハナミダヲナガシ サムサノナツハオロオロアルキ"
								+ " ミンナニデクノボートヨバレ ホメラレモセズ クニモサレズ" + " サウイフモノニ ワタシハナリタイ"
								+ " 南無無辺行菩薩 南無上行菩薩 南無多宝如来 南無妙法蓮華経 南無釈迦牟尼仏 南無浄行菩薩 南無安立行菩薩";
						comment = comment.substring(0,
								r.nextInt(comment.length() - comm_min) + comm_min);
					%>
					<div class="form-horizontal">
						<form action="uplaod.do" enctype="multipart/form-data"
							method="post" class="form-horizontal">
							<fieldset>
								<h3>Make Card</h3>
								<div class="control-group">
									<label for="iconPath" class="control-label">Image</label>
									<div class="controls">
										<input type="file" class="span8" name="iconPath" />
									</div>
								</div>
								<div class="control-group">
									<label for="comment" class="control-label">Comment</label>
									<div class="controls">
										<textarea name="comment" class="span8" rows="8"><%=comment%></textarea>
									</div>
								</div>
								<div class="control-group">
									<div class="controls">
										<input type="submit" value="Make" class="btn">
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>