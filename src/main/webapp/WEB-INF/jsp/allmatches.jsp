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

    <div class="container-lg">
        <c:if test="${not empty requestScope.events}">
            <div class="row">
                <c:forEach var="event" items="${requestScope.events}">
                    <div class="col-sm-6">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">
                                        ${event.firstTeam} vs ${event.secondTeam}
                                </h5>
                                <p>
                                    Start: <c:out value="${event.start}"/>
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
                                            <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#makeBet" data-whatever-result="FIRST_TEAM"
                                                    data-whatever-id="${event.matchId}" data-whatever-coef="${event.firstTeamCoefficient}">
                                                    ${event.firstTeamCoefficient}
                                            </button>
                                        </td>
                                        <td>
                                            <button class="btn btn-primary btn-block">
                                                    ${event.drawCoefficient}
                                            </button>
                                        </td>
                                        <td>
                                            <button class="btn btn-primary btn-block">
                                                    ${event.secondTeamCoefficient}
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>

    <div class="modal fade" id="makeBet" tabindex="-1" role="dialog" aria-labelledby="makeBet" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle">Set stake</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="${pageContext.request.contextPath}/controller?command=make_bet" method="post">
                    <input type="hidden" id="matchInput" name="matchId"/>
                    <input type="hidden" id="resultInput" name="result"/>
                    <div class="modal-body">
                        <div class="form-group">
                            <input type="number" step="0.01" class="form-control" id="betMoney" name="betMoney" required>
                        </div>
                        <span id="toReturn"></span>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success btn-block">Place bet</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

    <script>
        $('#makeBet').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget)
            var recipient = button.data('whatever-coef')
            var matchId = button.data('whatever-id')
            var result = button.data('whatever-result')
            var modal = $(this)
            document.getElementById('matchInput').value = matchId
            document.getElementById('resultInput').value = result

            const input = document.querySelector('#betMoney');
            const log = document.getElementById('toReturn');
            input.addEventListener('input', updateValue);
            function updateValue(e) {
                log.textContent = 'To return ' + e.target.value * recipient;
            }

        })
    </script>

</body>
</html>
