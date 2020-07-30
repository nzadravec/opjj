<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String appName = application.getAttribute("my.application.name").toString();
%>

<html>
<head>
<title>CreateBlog</title>

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
	<%
		if (session.getAttribute("current.user.id") != null) {
			out.print(
					"<h1>" + session.getAttribute("current.user.fn") + " " + session.getAttribute("current.user.ln")
							+ " (" + session.getAttribute("current.user.nick") + ")</h1>");
			out.print("<p><a href=\"/"+appName+"/servleti/logout\">Logout</a></p>");
		} else {
			out.print("<h1>not loged in</h1>");
		}
	%>
	<h2>Create blog</h2>

	<form action="<%out.print("/" + appName + "/servleti/saveBlog");%>"
		method="post">

		<div>
			<div>
				<span class="formLabel"></span><input type="hidden" name="id"
					value="${blog.id}">
			</div>
		</div>

		<div>
			<div>
				<span class="formLabel">Naslov</span><input type="text" name="title"
					value='<c:out value="${blog.title}"/>'>
			</div>
			<c:if test="${blog.hasError('title')}">
				<div class="error">
					<c:out value="${blog.getError('title')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Tekst</span>
				<textarea name="text" rows="4" cols="50">${blog.text}</textarea>
			</div>
			<c:if test="${blog.hasError('text')}">
				<div class="error">
					<c:out value="${blog.getError('text')}" />
				</div>
			</c:if>
		</div>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Save"> <input type="submit"
				name="method" value="Cancel">
		</div>

	</form>

</body>
</html>