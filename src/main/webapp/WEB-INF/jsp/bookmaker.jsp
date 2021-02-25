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
        </div>
        <div class="offset-lg-1 col-lg-10">
            <c:if test="${not empty requestScope.matches}">
                <h5>Available matches</h5>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">First team</th>
                            <%--                    <th scope="col">Coefficient</th>--%>
                        <th scope="col">Second team</th>
                        <th scope="col">New result</th>
                        <th scope="col">Cancel</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="match" items="${requestScope.matches}">
                        <tr>
                            <td>${match.firstTeam}</td>
                                <%--                        <td>${requestScope.coefficient}</td>--%>
                            <td>${match.secondTeam}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller?command=set_result" method="post">
                                        <%--                                    <input type="text" name="resultType" required>--%>
                                    <div class="form-group">
                                        <select class="form-control" id="result" name="resultType" required>
                                            <option>${match.firstTeam}</option>
                                            <option>${match.secondTeam}</option>
                                            <option>Draw</option>
                                        </select>
                                    </div>
                                    <input type="hidden" name="matchId" value="${match.id}" />
                                    <button class="btn btn-primary">Submit</button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller?command=cancel_match" method="post">
                                    <input type="hidden" name="matchId" value="${match.id}" />
                                    <button class="btn btn-primary">Cancel</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty requestScope.matches}">
                <h5>No matches</h5>
            </c:if>
        </div>

    </div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
