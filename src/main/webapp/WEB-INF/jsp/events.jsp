<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ut" uri="/WEB-INF/tag" %>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png" type="image/png">
    <title>Tiger bet - Online Football Betting</title>
</head>
<body>

    <jsp:include page="header.jsp"/>
    <br>

    <div class="container-lg">
        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${requestScope.error}"/>
            </div>
        </c:if>
<%--        <c:if test="${empty requestScope.error}">--%>
<%--            <div class="alert alert-success" role="alert">--%>
<%--                <c:out value="${requestScope.error}"/>empty--%>
<%--            </div>--%>
<%--        </c:if>--%>
        <c:if test="${not empty requestScope.events}">
            <div class="row">
                <c:forEach var="event" items="${requestScope.events}">
                    <div class="col-sm-6">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">
                                        ${event.matchDto.firstTeam} vs ${event.matchDto.secondTeam}
                                </h5>
                                <p>
                                    <ut:locale_tag key="start"/>: <c:out value="${event.matchDto.start}"/>
                                </p>
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th scope="col" class="text-center">1</th>
                                        <th scope="col" class="text-center">X</th>
                                        <th scope="col" class="text-center">2</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <c:if test="${not empty sessionScope.userName}">
                                                <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#makeBet" data-whatever-result="FIRST_TEAM"
                                                        data-whatever-id="${event.matchDto.id}" data-whatever-coef="${event.firstTeamCoefficient}">

                                                        ${event.firstTeamCoefficient}
                                                </button>
                                            </c:if>
                                            <c:if test="${empty sessionScope.userName}">
                                                <button class="btn btn-primary btn-block" data-toggle="modal" disabled>
                                                        ${event.firstTeamCoefficient}
                                                </button>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty sessionScope.userName}">
                                                <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#makeBet" data-whatever-result="DRAW"
                                                        data-whatever-id="${event.matchDto.id}" data-whatever-coef="${event.drawCoefficient}">

                                                        ${event.drawCoefficient}
                                                </button>
                                            </c:if>
                                            <c:if test="${empty sessionScope.userName}">
                                                <button class="btn btn-primary btn-block" data-toggle="modal" disabled>
                                                        ${event.drawCoefficient}
                                                </button>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty sessionScope.userName}">
                                                <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#makeBet" data-whatever-result="SECOND_TEAM"
                                                        data-whatever-id="${event.matchDto.id}" data-whatever-coef="${event.secondTeamCoefficient}">

                                                        ${event.secondTeamCoefficient}
                                                </button>
                                            </c:if>
                                            <c:if test="${empty sessionScope.userName}">
                                                <button class="btn btn-primary btn-block" data-toggle="modal" disabled>
                                                        ${event.secondTeamCoefficient}
                                                </button>
                                            </c:if>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <br>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>

    <jsp:include page="fragments/modal-setstake.jsp"/>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

    <script>
        $('#makeBet').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget)
            var coefficient = button.data('whatever-coef')
            var matchId = button.data('whatever-id')
            var result = button.data('whatever-result')
            document.getElementById('matchInput').value = matchId
            document.getElementById('resultInput').value = result

            const input = document.querySelector('#betMoney');
            const log = document.getElementById('toReturn');
            input.addEventListener('input', updateValue);
            function updateValue(e) {
                log.textContent = '<ut:locale_tag key="return"/> ' + Math.round((e.target.value * coefficient + Number.EPSILON) * 100) / 100;
            }
        })
    </script>

</body>
</html>
