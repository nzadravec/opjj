<%@ page import="java.lang.StringBuilder" import="java.text.SimpleDateFormat" import="java.util.Date" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>appinfo</title>
    <style>
      body {background-color: #<%= session.getAttribute("pickedBgCol") %>;}
    </style>
  </head>
  <body>
  
  <h1>Application duration</h1>
  
    <% 
  	SimpleDateFormat formatter = new SimpleDateFormat("dd:HH:mm:ss");
  	long startTime = (long) application.getAttribute("startTime");
  	long currentTime = System.currentTimeMillis();
  	long diff = currentTime - startTime;

  	long diffSeconds = diff / 1000 % 60;
  	long diffMinutes = diff / (60 * 1000) % 60;
  	long diffHours = diff / (60 * 60 * 1000) % 24;
  	long diffDays = diff / (24 * 60 * 60 * 1000);

  	StringBuilder sb = new StringBuilder();
  	sb.append(diffDays + " days, ");
  	sb.append(diffHours + " hours, ");
  	sb.append(diffMinutes + " minutes, ");
  	sb.append(diffSeconds + " seconds");
  	
  	String appDuration = sb.toString();
  	%>
    
    <p>Application is running <%= appDuration %></p>
  
  </body>
</html>