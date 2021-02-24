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
            <h2>Creating bet...</h2>
<%--            <form action="${pageContext.request.contextPath}/controller?command=make_bet" method="post">--%>
<%--                <c:if test="${not empty sessionScope.event}">--%>
<%--                    <p>${sessionScope.event.firstTeam} - ${sessionScope.event.firstTeamCoefficient}</p>--%>
<%--                    <p>${sessionScope.event.secondTeam} - ${sessionScope.event.secondTeamCoefficient}</p>--%>
<%--                    <p>Draw - ${sessionScope.event.drawCoefficient}</p>--%>
<%--                </c:if>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="prediction">Prediction</label>--%>
<%--                    <select class="form-control" id="prediction" name="userResult" required>--%>
<%--                        <option>${sessionScope.event.firstTeam}</option>--%>
<%--                        <option>${sessionScope.event.secondTeam}</option>--%>
<%--                        <option>Draw</option>--%>
<%--                    </select>--%>
<%--                </div>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="betMoney">Money</label>--%>
<%--                    <input type="number" step="0.01" class="form-control" id="betMoney" name="betMoney" required>--%>
<%--                </div>--%>
<%--                <button type="submit" class="btn btn-success">Make bet</button>--%>
<%--            </form>--%>
        </div>
    </div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
