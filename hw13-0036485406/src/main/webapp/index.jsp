<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>index</title>
    <style>
      body {background-color: #<%= session.getAttribute("pickedBgCol") %>;}
    </style>
  </head>
  <body>
    <h1>Index page</h1>
    <!-- Current color: #<%= session.getAttribute("pickedBgCol") %></p> --><p>
    
    <ul>
      <li><a href="colors.jsp" target="_blank">Background color chooser</a></li>
      <li><a href="trigonometric?a=0&b=90" target="_blank">Trigonometric</a></li>
    </ul>
    
    <form action="trigonometric" method="GET">
	  Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
	  Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
	  <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	
    <ul>
      <li><a href="funny.jsp" target="_blank">Funny story</a></li>
      <li><a href="powers?a=1&b=100&n=3" target="_blank">Powers excel document</a></li>
      <li><a href="appinfo.jsp" target="_blank">App duration</a></li>
    </ul>	
    
  </body>
</html>