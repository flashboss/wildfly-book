<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Servlet : Samples</title>
</head>
<body>
	<h1>All samples</h1>
	Invoke
	<a href="${pageContext.request.contextPath}/view/nonblocking.jsp">non-blocking
		sample</a>
	<a href="${pageContext.request.contextPath}/view/asynchronous.jsp">asynchronous
		sample</a>
	<a href="${pageContext.request.contextPath}/view/cookies.jsp">cookies
		sample</a>
	<a href="${pageContext.request.contextPath}/view/errormapping.jsp">error-mapping
		sample</a>
	<a href="${pageContext.request.contextPath}/view/eventlistener.jsp">event
		listener sample</a>
	<br />
</body>
</html>