<%@page import="hr.fer.zemris.java.tecaj_13.web.forms.CommentForm"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String appName = application.getAttribute("my.application.name").toString();
	BlogEntry blog = (BlogEntry) request.getAttribute("blog");
	CommentForm commentForm = (CommentForm) request.getAttribute("comment");
%>

<html>
<head>
<title>ShowBlog</title>

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
			out.print("<p><a href=\"/" + appName + "/servleti/logout\">Logout</a></p>");
		} else {
			out.print("<h1>not loged in</h1>");
		}
	%>
	<h2>
		<%
			out.print(blog.getTitle());
		%>
	</h2>
	<p>
		<%
			out.print(blog.getText());
		%>
	</p>

	<%
		if (request.getAttribute("providedNick") != null) {
			out.print("<p><a href=\"" + "edit?EID="+blog.getId()+"\">Edit blog</a></p>");
		}
	%>

	<%
		List<BlogComment> comments = blog.getComments();
		if (comments == null || comments.isEmpty()) {
			out.print("<h2>No comments.</h2>");
		} else {
			out.print("<h2>Comments:</h2>");
			out.print("<ol>");
			for (BlogComment comment : comments) {
				out.print("<li>");
				out.print("<h3>Posted by " + comment.getUsersEMail() + " on " + comment.getPostedOn() + "</h3>");
				out.print("<p>" + comment.getMessage() + "</p>");
				out.print("</li>");
			}
			out.print("</ol>");
		}
	%>

	<form action="<%out.print("/" + appName + "/servleti/saveComment");%>">

		<div>
			<div>
				<span class="formLabel"></span><input type="hidden" name="id"
					value="${blog.id}">
			</div>
		</div>
		
		<div>
			<div>
				<span class="formLabel">Comment</span>
				<textarea name="message" rows="4" cols="50"></textarea>
			</div>
			<c:if test="${commentForm.hasError('message')}">
				<div class="error">
					<c:out value="${commentForm.getError('message')}" />
				</div>
			</c:if>
		</div>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Add comment">
		</div>

	</form>

</body>
</html>