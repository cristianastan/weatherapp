<%--
  Created by IntelliJ IDEA.
  User: cristiana.stan
  Date: 6/22/16
  Time: 12:41 PM
--%>
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="../css/style.css">
    <title></title>

</head>

<body>
<section class="banner">
    <div class="container">
        <div class="search">
            <g:form name="getLocationForm" controller="Weather" action="save">
                <g:textField  class="textarea" name="location" value="" />
                <g:submitButton class="button" name="find" value="Get Weather" />
            </g:form>
            </div>

                <div class="info">
                    <g:if test="${result}">
                        ${result.Status}
                        <div class="card">
                            <h2>Country: ${result.Country} </h2>
                        </div>

                        <div class="card">
                            <h2> Description: ${result.Description} </h2>
                        </div>
                        <div class="card">
                            <h2> Temperature: <g:formatNumber number = "${result.Temperature}" format = "###" /> </h2>
                        </div>
                        <div class="card">
                            <h2> Humidity: <g:formatNumber number = "${result.Humidity}" format = "##" /> </h2>
                        </div>
                </g:if>
            </div>
        </div>
</section>
</body>
</html>