<%@page import="hr.fer.zemris.java.hw14.model.Poll.PollOption"%>
<%@page import="java.util.List"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<PollOption> options = (List<PollOption>) request.getAttribute("options");
	long pollID = (Long) request.getAttribute("pollID");
%>

<html>
<head>
<title>glasanjeIndex</title>
<style>
body {
	background-color: #<%=session.getAttribute("pickedBgCol")%>;
}
</style>
</head>
<body>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<%
			for (PollOption option : options) {
				long id = option.getId();
				String title = option.getOptionTitle();
				out.print("		<li><a href=\"glasanje-glasaj?pollID="+pollID+"&id=" + id + "\">" + title + "</a></li>");
			}
		%>
	</ol>
</body>
</html>