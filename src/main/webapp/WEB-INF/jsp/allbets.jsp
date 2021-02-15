<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BETS</title>
</head>
<body>
    <c:if test="${not empty requestScope.bets}">
        <h2>Columns</h2>
        <ol>
            <c:forEach var="bet" items="${requestScope.bets}">
                <li>Bet money: ${bet.betMoney}</li>
            </c:forEach>
        </ol>
    </c:if>
</body>
</html>
