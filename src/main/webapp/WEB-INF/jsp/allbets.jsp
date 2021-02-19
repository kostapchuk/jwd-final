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
                <form action="${pageContext.request.contextPath}/controller?command=cancel_bet" method="post">
                    <li>${bet.betMoney}</li>
                    <input type="hidden" name="betId" value="${bet.id}" />
                    <input type="submit" value="Cancel bet" />
                </form>
            </c:forEach>
        </ol>
    </c:if>
</body>
</html>
