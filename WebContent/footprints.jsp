<%@page import="com.samplepin.servlet.CommentServlet"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {
		$('#main').fadeIn(1000);
	});
</script>
</head>



<%
	String userId = (String) session.getAttribute("userId");
	List<View> views = Helper.getViewsInfoByID(userId);
	Random dice = new Random(System.nanoTime());
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<jsp:include page="button.jsp" flush="true" />
		<ul id="content">
			<%
				for (View view : views) {
					Card card = Helper.getCardInfoByID(view.getCardId());
					boolean isExternal = card.getUrl() != null && !card.getUrl().isEmpty();
					String cell = isExternal ? "cell2" : "cell";
					String star = isExternal ? card.getUrl() : card.getLikes() + " comment";
					String blank = isExternal ? "target='_blank'" : "";
					String jsp = isExternal ? "jump.jsp" : "card.jsp";
					String redirextUrl = isExternal ? "&redirectUrl=" + card.getUrl() : "";
			%>

			<li>
				<div class="<%=cell%>">
					<div>
						<a href="<%=jsp %>?cardId=<%=card.getCardId()%><%=redirextUrl %>"
							<%=blank %>> <img src="<%=card.getImagePath()%>"
							class="image-shot">
						</a>
					</div>
					<% if(card.getView() != 0){ %>
					<div class="ribon">
						<span class="ribon-text"> <%=card.getView()%> view
						</span>
					</div>
					<% } %>
					<div class="caption deco">
						<%=card.getCaption()%>
					</div>
					<div class="star right">
						<%=star%>
					</div>
				</div>
			</li>

			<%
				}
			%>
		</ul>
		<br style="clear: both;" />
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(window).load(function() {
		$('#content li').wookmark({
			offset : 20
		});
		cardId = $("#cardId").text();
		$('#cover').fadeOut(1000);
	});
	$(window).resize(function() {
		$('#content li').wookmark({
			offset : 20
		})
	});
</script>
</html>