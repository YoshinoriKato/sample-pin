<%@page import="com.samplepin.common.*"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!String suffix = Helper.TIMESTAMP;%>

<%
	ActivityLogger.log(request, this.getClass(), "request");
%>



<!-- meta -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type"
	content="text/javascript charset=UTF-8" />
<meta charset="UTF-8">



<!-- iOS -->
<meta name="viewport" content="width=device-width, maximum-scale=0.5">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">



<!-- title -->
<%
	if(Helper.valid(request.getAttribute("subTitle"))){
		String subTitle = (String) request.getAttribute("subTitle");
%>
	<title><%=Helper.NAME+subTitle%></title>

<% } else { %>
	<title><%=Helper.NAME%></title>

<% } %>

<!-- favicon -->
<link rel="shortcut icon" href="img/favicon.ico?<%=suffix%>">



<!-- css -->
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link href="css/common.css?<%=suffix%>" rel="stylesheet">
<link href="css/custom.css?<%=suffix%>" rel="stylesheet">
<link href="css/smartphone.css?<%=suffix%>" rel="stylesheet">


<!-- javascript -->
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.wookmark.js"></script>
<script type="text/javascript" src="js/jquery.dateFormat-1.0.js"></script>
<script type="text/javascript" src="js/dateformat.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap-dropdown.js"></script>



<!-- Google -->
<script type="text/javascript" src="http://www.google.com/jsapi"></script>



<!-- original -->
<script type="text/javascript" src="js/callback.js?<%=suffix%>"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/aboutTimestamp.js?<%=suffix%>"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/autoUrlLink.js?<%=suffix%>"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/escapeReturn.js?<%=suffix%>"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/searchImages.js?<%=suffix%>"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/util.js?<%=suffix%>"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/smartphone.js?<%=suffix%>"
	charset="UTF-8"></script>
<script type="text/javascript" src="js/pageScroll.js?<%=suffix%>"
	charset="UTF-8"></script>




<!-- Google Analytics -->
<script type="text/javascript">
	var _gaq = _gaq || [];
	_gaq.push([ '_setAccount', 'UA-34481032-1' ]);
	_gaq.push([ '_setDomainName', 'doya.info' ]);
	_gaq.push([ '_trackPageview' ]);

	(function() {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://ssl'
				: 'http://www')
				+ '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();
</script>


