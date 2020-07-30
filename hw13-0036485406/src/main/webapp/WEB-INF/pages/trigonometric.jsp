<%@ page import="hr.fer.zemris.java.hw13.servlets.TrigonometricServlet.Triplet" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>trigonometric</title>
    <style>
      body {background-color: #<%= session.getAttribute("pickedBgCol") %>;}
    </style>
  </head>
  <body>
    <h1>Trigonometric page</h1>

    <table border="1">
      <tr><td><b>x</b></td><td><b>sin(x)</b></td><td><b>cos(x)</b></td></tr>
      
      <%
	  Triplet[] triplets = (Triplet[]) request.getAttribute("triplets");
      for(int i = 0; i < triplets.length; i++) {
    	  Triplet t = triplets[i];
    	  out.print("      <tr><td>"+t.getFirst()+"</td><td>"+t.getSecond()+"</td><td>"+t.getThird()+"</td></tr>");
      }
      %>

    </table>

  </body>
</html>