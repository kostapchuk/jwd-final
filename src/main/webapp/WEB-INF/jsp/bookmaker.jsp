<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="language" value="${not empty param.language ? param.language : (not empty language ? language : pageContext.request.locale)}" scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="page" var="bundle"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png" type="image/png">
    <title>Tiger bet - Online Football Betting</title>
</head>
<body>

    <jsp:include page="header.jsp"/>
    <div class="container">
        <div class="offset-md-2 col-md-8">
            <h2><fmt:message key="bookmaker.create" bundle="${bundle}"/></h2>
            <form action="${pageContext.request.contextPath}/controller?command=create_event" method="post">
                <div class="form-group">
                    <label for="start"><fmt:message key="start" bundle="${bundle}"/></label>
                    <input type="datetime-local" name="startTime" class="form-control" id="start" required>
                </div>
                <div class="form-group">
                    <label for="firstTeam"><fmt:message key="bookmaker.opponent" bundle="${bundle}"/></label>
                    <select class="form-control" id="firstTeam" name="firstTeam" required>
                        <c:if test="${not empty requestScope.teams}">
                            <c:forEach var="team" items="${requestScope.teams}">
                                <option>${team}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="form-group">
                    <label for="secondTeam"><fmt:message key="bookmaker.opponent" bundle="${bundle}"/></label>
                    <select class="form-control" id="secondTeam" name="secondTeam" required>
                        <c:if test="${not empty requestScope.teams}">
                            <c:forEach var="team" items="${requestScope.teams}">
                                <option>${team}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="form-group">
                    <label for="firstTeamCoefficientId"><fmt:message key="bookmaker.first-team-coefficient" bundle="${bundle}"/></label>
                    <input type="number" step="0.01" class="form-control" id="firstTeamCoefficientId" name="firstTeamCoefficient" required>
                </div>
                <div class="form-group">
                    <label for="secondTeamCoefficientId"><fmt:message key="bookmaker.second-team-coefficient" bundle="${bundle}"/></label>
                    <input type="number" step="0.01" class="form-control" id="secondTeamCoefficientId" name="secondTeamCoefficient" required>
                </div>
                <div class="form-group">
                    <label for="drawCoefficientId"><fmt:message key="bookmaker.draw-coefficient" bundle="${bundle}"/></label>
                    <input type="number" step="0.01" class="form-control" id="drawCoefficientId" name="drawCoefficient" required>
                </div>
                <button type="submit" class="btn btn-success"><fmt:message key="bookmaker.create" bundle="${bundle}"/></button>
            </form>
        </div>
        <div class="offset-lg-1 col-lg-10">
            <c:if test="${not empty requestScope.matches}">
                <h5><fmt:message key="bookmaker.available-matches" bundle="${bundle}"/></h5>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="start" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="bookmaker.first-team" bundle="${bundle}"/></th>
                            <%--                    <th scope="col">Coefficient</th>--%>
                        <th scope="col"><fmt:message key="bookmaker.second-team" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="bookmaker.new-result" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="cancel" bundle="${bundle}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="match" items="${requestScope.matches}">
                        <tr>
                            <td>${match.start}</td>
                            <td>${match.firstTeam}</td>
                                <%--                        <td>${requestScope.coefficient}</td>--%>
                            <td>${match.secondTeam}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller?command=finish_event" method="post">
                                        <%--                                    <input type="text" name="resultType" required>--%>
                                    <div class="form-group">
                                        <select class="form-control" id="result" name="resultType" required>
                                            <option>${match.firstTeam}</option>
                                            <option>${match.secondTeam}</option>
                                            <option><fmt:message key="draw" bundle="${bundle}"/></option>
                                        </select>
                                    </div>
                                    <input type="hidden" name="matchId" value="${match.id}" />
                                    <button class="btn btn-primary"><fmt:message key="bookmaker.submit" bundle="${bundle}"/></button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller?command=cancel_event" method="post">
                                    <input type="hidden" name="matchId" value="${match.id}" />
                                    <button class="btn btn-danger"><fmt:message key="cancel" bundle="${bundle}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty requestScope.matches}">
                <h5><fmt:message key="bookmaker.no-matches" bundle="${bundle}"/></h5>
            </c:if>
        </div>

    </div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
