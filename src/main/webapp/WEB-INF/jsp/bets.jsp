<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bets</title>
</head>
<body>
<h2>Bets list</h2>
<c:if test="${not empty requestScope.bets}">
    <h2>Columns</h2>
    <c:forEach var="bet" items="${requestScope.bets}">
        <li>${bet.betMoney}</li>
    </c:forEach>
</c:if>
</body>
</html>
