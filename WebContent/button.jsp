
<%
	String addClass = "btn btn-large btn-info btn-cell";
%>

<div class="center page-menu">
	<span>
		<a href="index.jsp" class="<%=addClass%>">Latest</a>
		
		<a href="index.jsp?sorted=view" class="<%=addClass%>">Viewed
			Most</a>
			
		<a href="index.jsp?sorted=comment"
		class="<%=addClass%>">Commented Most</a>
		
		<%
			if(session.getAttribute("userId")!=null){
		%>
		<a href="footprints.jsp"
		class="<%=addClass%>">My Footprints</a>
			
		<a href="recommend.jsp"
		class="<%=addClass%>">Recommend</a>
		<% 
			}
		%>
	</span>
</div>
