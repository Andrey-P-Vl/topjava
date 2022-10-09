<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="2">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="mealsTo" items="${mealsToList}">
        <tr style="color: ${mealsTo.excess ? 'red' : 'green'}">
            <td>${f:formatLocalDateTime(mealsTo.dateTime, 'yyyy-MM-dd HH:mm')}</td>
            <td>${mealsTo.description}</td>
            <td>${mealsTo.calories}</td>
            <td></td>
            <td></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
