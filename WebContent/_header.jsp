<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!String	suffix	= String.valueOf(System.currentTimeMillis());%>
<!-- 共有 -->

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type"
	content="text/javascript charset=UTF-8" />
<meta charset="UTF-8">

<title>Sample-Pin</title>

<link rel="shortcut icon" href="img/favicon.ico?<%=suffix%>">

<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="css/common.css?<%=suffix%>" rel="stylesheet">
<link href="css/custom.css?<%=suffix%>" rel="stylesheet">

<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.wookmark.js"></script>
<script type="text/javascript" src="js/jquery.dateFormat-1.0.js"></script>
<script type="text/javascript" src="js/dateformat.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>

<!-- Google -->
<script type="text/javascript" src="http://www.google.com/jsapi"></script>

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


