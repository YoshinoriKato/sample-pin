<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="_header.jsp" flush="false"></jsp:include>
<script type="text/javascript">
	function callAlert(val) {
		var mills = new Date() - 0;
		var old = $('#old').text();
		var gap = mills - old;
		var ret = gap;
		var second = 1000;
		var minute = second * 60;
		var hour = minute * 60;
		var day = hour * 24;

		if (minute > gap) {
			ret = ((gap - gap % second) / second) + ' seconds';

		} else if (hour > gap) {
			ret = ((gap - gap % minute) / minute) + ' minutes';

		} else if ((day * 2) > gap) {
			ret = ((gap - gap % hour) / hour) + ' hours';

		} else {
			var dateFormat = new DateFormat("yyyy/MM/dd 'at' HH");
			ret = dateFormat.format(new Date());
		}

		alert(ret);
	};
</script>
</head>
<body>
	<h2 id="old"><%=System.currentTimeMillis()%></h2>
	<h2><%=System.nanoTime()%></h2>
	<h2><%=Long.MAX_VALUE%></h2>
	<input type="button" onclick="callAlert('OK');" value="PUSH">
</body>
</html>