<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create match</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=create_match" method="post">
        <p>Sport type: <input type="text" name="sportType" required></p>
        <p>Start: <input type="datetime-local" name="startTime" required></p>
        <p>First team: <input type="text" name="firstTeam" required></p>
        <p>Second team: <input type="text" name="secondTeam" required></p>
        <p>First team coefficient: <input type="number" step="0.01" name="firstTeamCoefficient" required></p>
        <p>Second team coefficient: <input type="number" step="0.01" name="secondTeamCoefficient" required></p>
        <p>Draw coefficient: <input type="number" step="0.01" name="drawCoefficient" required></p>
        <input type="submit" value="Create match" />
    </form>

    <h2>Matches list</h2>

    <c:if test="${not empty requestScope.matches}">
        <h2>Columns</h2>
        <c:forEach var="match" items="${requestScope.matches}">
            <li>${match.firstTeam} vs ${match.secondTeam}</li>
            <form action="${pageContext.request.contextPath}/controller?command=set_result" method="post">
                New Result: <input type="text" name="resultType" >
                <input type="hidden" name="matchId" value="${match.id}" />
                <input type="submit" value="Submit new result" />
            </form>
            <form action="${pageContext.request.contextPath}/controller?command=set_status" method="post">
                New Status: <input type="text" name="statusType" >
                <input type="hidden" name="matchId" value="${match.id}" />
                <input type="submit" value="Submit new status" />
            </form>
        </c:forEach>
    </c:if>
</body>
</html>
