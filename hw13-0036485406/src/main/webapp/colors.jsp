<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>colors</title>
    <style>
      body {background-color: #<%= session.getAttribute("pickedBgCol") %>;}
    </style>
  </head>
  <body>
   <h1>Colors page</h1>
   <ul><li><a href="setcolor?bgCol=FFFFFF" target="_blank">WHITE</a></li></ul>
   <ul><li><a href="setcolor?bgCol=FF0000" target="_blank">RED</a></li></ul>
   <ul><li><a href="setcolor?bgCol=00FF00" target="_blank">GREEN</a></li></ul>
   <ul><li><a href="setcolor?bgCol=00FFFF" target="_blank">CYAN</a></li></ul>
  </body>
</html>