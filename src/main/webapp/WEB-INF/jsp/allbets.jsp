<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

    <title>Tiger bet - Online Football Betting</title>
</head>
<body>

    <jsp:include page="header.jsp"/>
    <div class="container">
        <div class="offset-lg-1 col-lg-10">
            <c:if test="${not empty requestScope.placedBets}">
                <table class="table">
<%--                    <thead>--%>
<%--                    <tr>--%>
<%--                        <th scope="col">Bet $</th>--%>
<%--                            &lt;%&ndash;                    <th scope="col">Coefficient</th>&ndash;%&gt;--%>
<%--                        <th scope="col">To return $</th>--%>
<%--                        <th scope="col"></th>--%>
<%--                        <th scope="col"></th>--%>
<%--                    </tr>--%>
<%--                    </thead>--%>
                    <tbody>
                    <c:forEach var="bet" items="${requestScope.placedBets}">
                        <tr>
                            <td >${bet.start}</td>
                            <td>${bet.opponents}</td>
                            <td class="font-weight-bold">${bet.placedTeam}</td>
                            <td>${bet.placedCoefficient}</td>
                            <td ><span class="text-muted">To return</span> <span class="font-weight-bold">${bet.expectedWin}</span>  <span class="text-muted">$</span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller?command=update_bet" method="post">
                                    <input type="hidden" name="betId" value="${bet.id}" />
                                    <button class="btn btn-primary btn-block">Update</button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/controller?command=cancel_bet" method="post">
                                    <input type="hidden" name="betId" value="${bet.id}" />
                                    <input type="hidden" name="matchId" value="${requestScope.matchId}" />
                                    <button class="btn btn-outline-primary btn-block">Cancel</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
