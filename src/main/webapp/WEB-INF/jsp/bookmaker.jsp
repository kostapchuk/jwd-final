<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

    <title>Tiger bet - Online Football Betting</title>
</head>
<body>

    <jsp:include page="header.jsp"/>
    <h2>Create match:</h2>
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


    <c:if test="${not empty requestScope.matches}">
        <h2>Matches list</h2>
        <c:forEach var="match" items="${requestScope.matches}">
            <li>${match.firstTeam} vs ${match.secondTeam}</li>
            <form action="${pageContext.request.contextPath}/controller?command=set_status" method="post">
                New Result: <input type="text" name="resultType" required>
                New Status: <input type="text" name="statusType" required>
                <input type="hidden" name="matchId" value="${match.id}" />
                <input type="submit" value="Submit" />
            </form>
        </c:forEach>
    </c:if>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
