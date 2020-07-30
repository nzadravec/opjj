<%@page import="hr.fer.zemris.java.hw14.model.Poll.PollOption"%>
<%@page import="java.util.List"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	List<PollOption> options = (List<PollOption>) request.getAttribute("options");
	List<PollOption> bestOptions = (List<PollOption>) request.getAttribute("bestOptions");
	long pollID = (Long) request.getAttribute("pollID");
%>
<html>
<head>
<style>
body {
	background-color: #<%=session.getAttribute("pickedBgCol")%>;
}
</style>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (PollOption option : options) {
					String title = option.getOptionTitle();
					long votesCount = option.getVotesCount();
					out.print("<tr><td>" + title + "</td>" + "<td>" + votesCount + "</td></tr>");
				}
			%>
		</tbody>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart"
		src="glasanje-grafika?pollID=<%out.print(pollID);%>"
		width="400" height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a
			href="glasanje-xls?pollID=<%out.print(pollID);%>">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<%
			for (PollOption option : bestOptions) {
				String link = option.getOptionLink();
				String title = option.getOptionTitle();
				out.print("<li><a href=\"" + link + "\"target=\"_blank\">" + title + "</a></li>");
			}
		%>
	</ul>
</body>
</html>