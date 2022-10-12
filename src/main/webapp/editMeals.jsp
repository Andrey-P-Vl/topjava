<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="ru">
<head>
    <title>${meal eq null ? 'Add': 'Edit'} Meals</title>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${meal eq null ? 'Add': 'Edit'} Meal</h2>
<form method="POST" action="meals" name="frmAddMeal">
    DateTime:<input type="datetime-local" name="datetime"
    <c:choose>
        <c:when test="${meal eq null}">value="${meal.dateTime}"/> <br/></c:when>
        <c:otherwise>value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}" /> <br/></c:otherwise>
    </c:choose>
    Description:<input type="text" name="description"
                       value="${meal.description}"/> <br/>
    Calories:<input type="number" name="calories"
                    value="${meal.calories}"/> <br/>
    <input type="hidden" name="id"
           value="${meal.id}"/> <br/>
    <input type="submit" value="${meal eq null ? 'Add': 'Edit'}">
    <button onclick="location.href='meals'" type="button">
        Back</button>
</form>
</body>
</html>
