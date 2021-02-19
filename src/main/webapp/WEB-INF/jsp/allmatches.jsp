<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matches</title>
</head>
<body>
    <h1>BEST MATCHEES, RRRRRRR tiger!</h1>
    <h2>Matches list</h2>

    <c:if test="${not empty requestScope.matches}">
        <h2>Columns</h2>
        <c:forEach var="match" items="${requestScope.matches}">
            <form action="${pageContext.request.contextPath}/controller?command=show_make_bet_page" method="post">
                <li>${match.firstTeam} vs ${match.secondTeam}</li>
                <input type="hidden" name="matchId" value="${match.id}" />
                <input type="submit" value="Make bet" />
            </form>
        </c:forEach>
    </c:if>
</body>
</html>
