<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String keyword = request.getParameter("keyword");
	keyword = Helper.valid(keyword) ? keyword : "DOYA.info";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	attach(window, 'load', function() {
		$('#main').fadeIn(1000);
	});
</script>
</head>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="main" style="padding-left: 30px; padding-right: 30px;">
		<h2 class="card-header">
			SCAMPER法 [<b><%=keyword%></b>]
		</h2>
		<div style="margin-left: 20px;">
			<h3 class="sub-header">Substitute（代える、代用する）</h3>
			<ol>
				<li id="s01"><b><%=keyword%></b>で代用可能な部分はどれか</li>
				<li id="s02">何を<b><%=keyword%></b>の代わりに使うことができるか
				</li>
				<li id="s03"><b><%=keyword%></b>のほかに誰を含めることができるか</li>
				<li id="s04"><b><%=keyword%></b>のほかにどんなグループを含めることができるか</li>
				<li id="s05"><b><%=keyword%></b>の代わりにどんなプロセスを使うことができるか</li>
				<li id="s06"><b><%=keyword%></b>の代わりにどんなマテリアル（モノ）を使うことができるか</li>
			</ol>
			<h3 class="sub-header">Combine（組み合わせる）</h3>
			<ol>
				<li id="c01"><b><%=keyword%></b>と何を組み合わせることができるか</li>
				<li id="c02"><b><%=keyword%></b>とブレンドする（混ぜ合わせる）ことはできるか</li>
				<li id="c03"><b><%=keyword%></b>はどんな種類のアンサンブル（取り合わせ）を使うことできるか、創ることができるか
				</li>
				<li id="c04"><b><%=keyword%></b>の部分同士を、どのように組み合わせることができるか</li>
				<li id="c05"><b><%=keyword%></b>の目的同士を、どのように組み合わせることができるか</li>
				<li id="c06"><b><%=keyword%></b>のアプリケーション（応用方法）同士を、どのように組み合せることができるか
				</li>
				<li id="c07"><b><%=keyword%></b>のマテリアル（モノ）同士を、どのように組み合せることができるか
				</li>
			</ol>
			<h3 class="sub-header">Adapt（適応させる）</h3>
			<ol>
				<li id="a01"><b><%=keyword%></b>は、ほかのどのような考えを思い付かせるか</li>
				<li id="a02">何かほかに、<b><%=keyword%></b>に似たものはないか
				</li>
				<li id="a03">過去に<b><%=keyword%></b>に似た状況はないか
				</li>
			</ol>
			<h3 class="sub-header">Modify（修正する）</h3>
			<ol>
				<li id="m01"><b><%=keyword%></b>にさらにTwist（ひねり、コトの意外な曲折）を加えることができないか
				</li>
				<li id="m02"><b><%=keyword%></b>の意味あいを、どのくらい変えることができるか</li>
				<li id="m03"><b><%=keyword%></b>の色や外形を、どのくらい変えることができるか</li>
				<li id="m04"><b><%=keyword%></b>のサウンド（音、騒音、音声）を、どのくらい変えることができるか
				</li>
				<li id="m05"><b><%=keyword%></b>に何を加えることができるか</li>
				<li id="m06"><b><%=keyword%></b>の高さ・高度をどれくらい増やせるか</li>
				<li id="m07"><b><%=keyword%></b>の重さをどれくらい増やせるか</li>
				<li id="m08"><b><%=keyword%></b>の強度をどれくらい増やせるか</li>
				<li id="m09"><b><%=keyword%></b>の頻度をどれくらい増やせるか</li>
				<li id="m10"><b><%=keyword%></b>の価値をどれくらい増やせるか</li>
				<li id="m11"><b><%=keyword%></b>の何を減らすことができるか</li>
				<li id="m12"><b><%=keyword%></b>の何を縮小することができか</li>
				<li id="m13"><b><%=keyword%></b>の何を簡素化することができるか</li>
				<li id="m14">控えめに言うことができるのは、<b><%=keyword%></b>のどんな部分か
				</li>
				<li id="m15"><b><%=keyword%></b>のサイズをどれくらい小さくできるか</li>
				<li id="m16"><b><%=keyword%></b>の重さをどれくらい軽くできるか</li>
			</ol>
			<h3 class="sub-header">Put to other uses（ほかの使いみち）</h3>
			<ol>
				<li id="p01">そのままの<b><%=keyword%></b>で、何かほかへ使えないか
				</li>
				<li id="p02">もし<b><%=keyword%></b>の一部を変えたら、新たに生まれるほかの用途は何か
				</li>
				<li id="p03"><b><%=keyword%></b>のほかにどんなマーケットが受け入れるか</li>
			</ol>
			<h3 class="sub-header">Eliminate（省略する、除去する）</h3>
			<ol>
				<li id="e01"><b><%=keyword%></b>から何を、取り除くことができるか、省略することができるか</li>
				<li id="e02"><b><%=keyword%></b>のある部分がない時、どうやって実行するか</li>
				<li id="e03"><b><%=keyword%></b>は何を犠牲にできるか</li>
				<li id="e04">あげてしまえるものは、<b><%=keyword%></b>の何か
				</li>
			</ol>
			<h3 class="sub-header">Rearrange（再調整する）</h3>
			<ol>
				<li id="r01"><b><%=keyword%></b>のほかに、どんなパターンが使えるか。</li>
				<li id="r02"><b><%=keyword%></b>のほかに、どんな配置が使えるか</li>
				<li id="r03"><b><%=keyword%></b>のほかに、どんなレイアウトが使えるか</li>
				<li id="r04"><b><%=keyword%></b>の何を交換できるか</li>
				<li id="r05"><b><%=keyword%></b>の何を置換できるか。言い換えられるか。</li>
				<li id="r05"><b><%=keyword%></b>の何を、再結合できるか</li>
				<li id="r07"><b><%=keyword%></b>を逆にしたらどうなるか</li>
				<li id="r08"><b><%=keyword%></b>の上下逆さまにしたらどうなるか</li>
				<li id="r09"><b><%=keyword%></b>の内外を裏返したらどうなるか</li>
			</ol>
		</div>
	</div>

</body>
</html>