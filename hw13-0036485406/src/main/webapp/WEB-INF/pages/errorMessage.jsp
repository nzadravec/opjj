<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>error message</title>
    <style>
      body {background-color: #<%= session.getAttribute("pickedBgCol") %>;}
    </style>
  </head>
  <body>
    <h1>Error message</h1>

	<p> <%= request.getAttribute("errorMessage") %> </p>

  </body>
</html>