<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ut" uri="/WEB-INF/tag" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png" type="image/png">
    <title>Tiger bet - Online Football Betting</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="container">
    <div class="offset-lg-1 col-lg-10">
        <c:if test="${not empty requestScope.placedBets}">
            <h5>Current bets</h5>
            <table class="table">
                <tbody>
                <c:forEach var="bet" items="${requestScope.placedBets}">
                    <tr>
                        <td>${bet.start}</td>
                        <td>${bet.opponents}</td>
                        <td class="font-weight-bold">
                            <c:if test="${'Draw' eq bet.placedTeam}">
                                <ut:locale_tag key="draw"/>
                            </c:if>
                            <c:if test="${'Draw' ne bet.placedTeam}">
                                ${bet.placedTeam}
                            </c:if>
                        </td>
                        <td>x${bet.placedCoefficient}</td>
                        <td>
                            <span class="text-muted"><ut:locale_tag key="return"/></span>
                            <span class="font-weight-bold">${bet.expectedWin}</span>
                            <span class="text-muted">$</span>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller?command=cancel_bet"
                                  method="post">
                                <input type="hidden" name="betId" value="${bet.id}"/>
                                <input type="hidden" name="matchId" value="${requestScope.matchId}"/>
                                <button class="btn btn-outline-primary btn-block"><ut:locale_tag key="cancel"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${not empty requestScope.previousPlacedBets}">
            <h5>Previous bets</h5>
            <table class="table">
                <tbody>
                <c:forEach var="previousBet" items="${requestScope.previousPlacedBets}">

                    <c:if test="${previousBet.win == true}">
                        <tr class="table-success">
                    </c:if>

                    <c:if test="${previousBet.win == false}">
                        <tr class="table-danger">
                    </c:if>
                    <td>${previousBet.bet.start}</td>
                    <td>${previousBet.bet.opponents}</td>
                    <td class="font-weight-bold">
                        <c:if test="${'Draw' eq previousBet.bet.placedTeam}">
                            <ut:locale_tag key="draw"/>
                        </c:if>
                        <c:if test="${'Draw' ne previousBet.bet.placedTeam}">
                            ${previousBet.bet.placedTeam}
                        </c:if>
                    </td>
                    <td>x${previousBet.bet.placedCoefficient}</td>
                    <td>
                        <c:if test="${previousBet.win == true}">
                            <span class="text-muted">Won</span>
                            <span class="font-weight-bold">
                                            +
                                            ${previousBet.bet.expectedWin}
                                    </span>
                        </c:if>
                        <c:if test="${previousBet.win == false}">
                            <span class="text-muted">Lost</span>
                            <span class="font-weight-bold">
                                            -
                                            ${previousBet.placedMoney}
                                    </span>
                        </c:if>
                        <span class="text-muted">$</span>
                    </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>

</body>
</html>
