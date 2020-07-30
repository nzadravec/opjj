<%@ page import="java.util.Random" import="java.awt.Color" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>funny</title>
    <style>
      body {background-color: #<%= session.getAttribute("pickedBgCol") %>;}
    </style>
  </head>
  <body>
    <h1>Funny page</h1>
    
    <%
	    Random rand = new Random();
	    int colorInt = rand.nextInt(256*256*256);
	    String colorHexStr = String.format("#%06x", colorInt);
    %>

	<p><font color=#<%= colorHexStr %>>funny story...</font></p>

  </body>
</html>