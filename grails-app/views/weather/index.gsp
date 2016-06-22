<%--
  Created by IntelliJ IDEA.
  User: cristiana.stan
  Date: 6/22/16
  Time: 12:41 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
    Hello world!
    <g:form name="getLocationForm" controller="Weather" action="save">
    Location:<br>
    <g:textField  name="location" value="" />
    <g:submitButton name="find" value="Find" />
</g:form>
</body>
</html>