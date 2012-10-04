<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Facebook</title>
</head>
<body>
	<%
		final String FACEBOOK_APP_ID = "WNK6BA7RRI";
		StringBuilder builder = new StringBuilder();
		builder.append("https://www.facebook.com/dialog/oauth");
		builder.append("?client_id=" + FACEBOOK_APP_ID);
		builder.append("&redirect_uri=http://doya.infooauth-facebook.jsp");
		builder.append("&state=" + System.currentTimeMillis());
		builder.append("&scope=offline_access,publish_stream,user_status,read_stream,status_update,manage_pages");
		// 'client_id' => APP_ID,
		// 'redirect_uri' => SITE_URL.'redirect.php',
		// 'state' => $_SESSION['state']

		;
	%>
	<a href="<%=builder.toString() %>">Login</a>
</body>
</html>