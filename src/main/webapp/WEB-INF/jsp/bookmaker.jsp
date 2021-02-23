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
    <div class="container">
        <div class="offset-md-2 col-md-8">
            <h2>Create match</h2>
            <form action="${pageContext.request.contextPath}/controller?command=create_match" method="post">
                <div class="form-group">
                    <label for="start">Start</label>
                    <input type="datetime-local" name="startTime" class="form-control" id="start" required>
                </div>
                <div class="form-group">
                    <label for="firstTeam">Opponent</label>
                    <select class="form-control" id="firstTeam" name="firstTeam" required>
                        <c:if test="${not empty requestScope.teams}">
                            <c:forEach var="team" items="${requestScope.teams}">
                                <option>${team}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="form-group">
                    <label for="secondTeam">Opponent</label>
                    <select class="form-control" id="secondTeam" name="secondTeam" required>
                        <c:if test="${not empty requestScope.teams}">
                            <c:forEach var="team" items="${requestScope.teams}">
                                <option>${team}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="form-group">
                    <label for="firstTeamCoefficientId">First team coefficient</label>
                    <input type="number" step="0.01" class="form-control" id="firstTeamCoefficientId" name="firstTeamCoefficient" required>
                </div>
                <div class="form-group">
                    <label for="secondTeamCoefficientId">Second team coefficient</label>
                    <input type="number" step="0.01" class="form-control" id="secondTeamCoefficientId" name="secondTeamCoefficient" required>
                </div>
                <div class="form-group">
                    <label for="drawCoefficientId">Draw coefficient</label>
                    <input type="number" step="0.01" class="form-control" id="drawCoefficientId" name="drawCoefficient" required>
                </div>
                <button type="submit" class="btn btn-success">Create match</button>
            </form>


            <c:if test="${not empty requestScope.matches}">
                <h2>Matches list</h2>
                <c:forEach var="match" items="${requestScope.matches}">
                    <li>${match.firstTeam} vs ${match.secondTeam}</li>
                    <form action="${pageContext.request.contextPath}/controller?command=set_result" method="post">
                        New Result: <input type="text" name="resultType" required>
                        <input type="hidden" name="matchId" value="${match.id}" />
                        <input type="submit" value="Submit" />
                    </form>
                </c:forEach>
            </c:if>
        </div>
    </div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
