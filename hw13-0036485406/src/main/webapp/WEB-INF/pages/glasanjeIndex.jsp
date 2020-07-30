<%@ page
	import="hr.fer.zemris.java.hw13.servlets.MusicalBand"
	session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
			MusicalBand[] musicalBands = (MusicalBand[]) request.getAttribute("musicalBands");
			for (MusicalBand band : musicalBands) {
				int id = band.getBandID();
				String name = band.getBandName();
				out.print("		<li><a href=\"glasanje-glasaj?id=" + id + "\">" + name + "</a></li>");
			}
		%>
	</ol>
</body>
</html>