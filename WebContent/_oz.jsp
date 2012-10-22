<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String cardId = request.getParameter("cardId");
	Card card = (Card) request.getAttribute("card");
	if (Helper.valid(card.getKeywords())) {

		String keywords[] = card.getKeywords().split("[ 　\t\f\r\n]+");
		for (String keyword : keywords) {
%>
<div class="full-body">
	<h2 class="card-header">
		オズボーン「<%=card.getKeywords()%>」
	</h2>
	<table height="80%" width="80%" class="center gradient"
		style="margin-left: 20px;">
		<tr>
			<td><h3>転用</h3> <span>他に使い道はないか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 他に")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
			<td><h3>応用</h3> <span>他からアイデアを借りられないか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 借り")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
			<td><h3>変更</h3> <span>変えてみたらどうか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 変え")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
		</tr>
		<tr>
			<td><h3>拡大</h3> <span>大きくしてみたらどうか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 大きく")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
			<td><h3>縮小</h3> <span>小さくしてみたらどうか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 小さく")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
			<td><h3>代用</h3> <span>他のもので代用できないか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 代用")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
		</tr>
		<tr>
			<td><h3>置換</h3> <span>入れ替えてみたらどうか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 入れ替え")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
			<td><h3>逆転</h3> <span>逆にしてみたらどうか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 逆")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
			<td><h3>結合</h3> <span>組み合わせてみたらどうか？</span> 
			
			<%
 			for (Card idea : Helper.searchCards(request, keyword
 					+ " 組み合わせ")) {
 				request.setAttribute("card", idea);
			 %>
				<div class="card"><jsp:include page="_card.jsp"></jsp:include></div>
			<%
				}
				request.setAttribute("card", card);
			%>
			</td>
		</tr>
	</table>
</div>
<%
	}
	}
%>