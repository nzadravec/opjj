<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String nick = request.getAttribute("user.nick").toString();
	String appName = application.getAttribute("my.application.name").toString();
	List<BlogEntry> blogs = (List<BlogEntry>) request.getAttribute("blogs");
%>

<html>
<head>
<title>Main</title>
</head>

<body>
	<%
		if (session.getAttribute("current.user.id") != null) {
			out.print(
					"<h1>" + session.getAttribute("current.user.fn") + " " + session.getAttribute("current.user.ln")
							+ " (" + session.getAttribute("current.user.nick") + ")</h1>");
			out.print("<p><a href=\"/"+appName+"/servleti/main\">Logout</a></p>");
		} else {
			out.print("<h1>not loged in</h1>");
		}

		out.print("<p><a href=\"/" + appName + "/servleti/main\">Main page</a></p>");
	%>



	<%
		if (blogs == null || blogs.isEmpty()) {
			out.print("<p>Nema unosa!</p>");
		} else {
			if (request.getAttribute("providedNick") != null) {
				out.print("<p>List of your blogs:</p>");
			} else {
				out.print("<p>List of " + nick + " blogs:</p>");
			}

			out.print("<ol>");
			for (BlogEntry blog : blogs) {
				Long id = blog.getId();
				String title = blog.getTitle();
				out.print("		<li><a href=\"" + nick + "/" + id + "\">" + title + "</a></li>");
			}
			out.print("</ol>");
		}
	%>

	<%
		if (request.getAttribute("providedNick") != null) {
			out.print("<p><a href=\""+nick+"/new\">Add new blog</a></p>");
		} else {
		}
	%>

</body>
</html>
