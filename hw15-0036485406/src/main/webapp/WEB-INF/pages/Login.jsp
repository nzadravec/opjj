<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	List<BlogUser> users = (List<BlogUser>) request.getAttribute("users");
%>

<html>
<head>
<title>Login</title>

<style type="text/css">
.error {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>

<body>
	<h1>Login</h1>

	<form action="login" method="post">

		<div>
			<div>
				<span class="formLabel">Nickname</span><input type="text"
					name="nick" value='<c:out value="${user.nick}"/>' size="50">
			</div>
			<c:if test="${user.hasError('nick')}">
				<div class="error">
					<c:out value="${user.getError('nick')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Password</span><input type="password"
					name="password" value='<c:out value="${user.password}"/>' size="50">
			</div>
			<c:if test="${user.hasError('password')}">
				<div class="error">
					<c:out value="${user.getError('password')}" />
				</div>
			</c:if>
		</div>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Login">
		</div>

	</form>
	
	<h2><a href="register">Register</a></h2>

	<p>List of registered authors:</p>
	<ol>
		<%
			for (BlogUser user : users) {
				String name = user.getFirstName() + " " + user.getLastName();
				String nick = user.getNick();
				out.print("		<li>"+name+" <a href=\"author/" + nick + "\">" + "(" + nick + ")" + "</a></li>");
			}
		%>
	</ol>

</body>
</html>
