<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.hw13.servlets.MusicalBand"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
				MusicalBand[] bands = (MusicalBand[]) request.getAttribute("bands");

				for (MusicalBand band : bands) {
					String bandName = band.getBandName();
					int numberOfVotes = band.getNumberOfVotes();
					out.print("<tr><td>" + bandName + "</td>" + "<td>" + numberOfVotes + "</td></tr>");
				}
			%>
		</tbody>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<%
			MusicalBand[] winningBands = (MusicalBand[]) request.getAttribute("winningBands");
			for (MusicalBand band : winningBands) {
				String link = band.getLinkToSong();
				String name = band.getBandName();
				out.print("<li><a href=\"" + link + "\"target=\"_blank\">" + name + "</a></li>");
			}
		%>
	</ul>
</body>
</html>