<%@page import="hr.fer.zemris.java.hw14.model.Poll"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%
	List<Poll> polls = (List<Poll>) request.getAttribute("polls");
%>
<html>
<body>

	<b>Dostupne ankete:</b>
	<br>

	<%
		if (polls.isEmpty()) {
	%>
	Nema unosa.
	<%
		} else {
	%>
	<ul>
		<%
			for (Poll p : polls) {
				out.print("		<li><a href=\"glasanje?pollID=" + p.getId() + "\">" + p.getTitle() + "</a></li>");
			}
		%>
	</ul>
	<%
		}
	%>

</body>
</html>