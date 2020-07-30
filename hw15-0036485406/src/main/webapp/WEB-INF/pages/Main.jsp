<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<BlogUser> users = (List<BlogUser>) request.getAttribute("users");
%>

<html>
<head>
<title>Main</title>
</head>

<body>
	<h1><%=session.getAttribute("current.user.fn") + " " + session.getAttribute("current.user.ln") + " (" + session.getAttribute("current.user.nick")  + ")"%></h1>
	<p><a href="logout">Logout</a></p>
	
	<p><a href=" author/<%=session.getAttribute("current.user.nick")%> ">Your blogs</a></p>

	<p>List of other registered authors:</p>
	<ol>
		<%
			for (BlogUser user : users) {
				String name = user.getFirstName() + " " + user.getLastName();
				String nick = user.getNick();
				if(nick.equals(session.getAttribute("current.user.nick"))) {
					continue;
				}
				out.print("		<li>"+name+" <a href=\"author/" + nick + "\">" + "(" + nick + ")" + "</a></li>");
			}
		%>
	</ol>

</body>
</html>
