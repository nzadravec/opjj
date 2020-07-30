<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Register</title>

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
	<h1>
		Register
	</h1>

	<form action="saveUser" method="post">

		<div>
			<div>
				<span class="formLabel">First name</span><input type="text" name="firstName"
					value='<c:out value="${user.firstName}"/>' size="20">
			</div>
			<c:if test="${user.hasError('firstName')}">
				<div class="error">
					<c:out value="${user.getError('firstName')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Last name</span><input type="text"
					name="lastName" value='<c:out value="${user.lastName}"/>' size="20">
			</div>
			<c:if test="${user.hasError('lastName')}">
				<div class="error">
					<c:out value="${user.getError('lastName')}" />
				</div>
			</c:if>
		</div>
		
		<div>
			<div>
				<span class="formLabel">Nickname</span><input type="text" name="nick"
					value='<c:out value="${user.nick}"/>' size="50">
			</div>
			<c:if test="${user.hasError('nick')}">
				<div class="error">
					<c:out value="${user.getError('nick')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">EMail</span><input type="text" name="email"
					value='<c:out value="${user.email}"/>' size="50">
			</div>
			<c:if test="${user.hasError('email')}">
				<div class="error">
					<c:out value="${user.getError('email')}" />
				</div>
			</c:if>
		</div>
		
		<div>
			<div>
				<span class="formLabel">Password</span><input type="password" name="password"
					value='<c:out value="${user.password}"/>' size="50">
			</div>
			<c:if test="${user.hasError('password')}">
				<div class="error">
					<c:out value="${user.getError('password')}" />
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
