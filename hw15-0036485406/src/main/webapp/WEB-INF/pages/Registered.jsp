<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Registered</title>
<style>
body {
	background-color: #<%=session.getAttribute("pickedBgCol")%>;
}
</style>
</head>
<body>
	<h1>
		You are already registered as
		<%=session.getAttribute("current.user.fn") + " " + session.getAttribute("current.user.ln")%></h1>
		
		<p><a href="main/logout">Logout</a></p>
		<p><a href="main">Main page</a></p>
</body>
</html>