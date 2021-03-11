<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ut" uri="/WEB-INF/tag" %>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>

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
        <h2><ut:locale_tag key="users"/></h2>
        <c:if test="${not empty requestScope.users}">
            <div class="row">
                <c:forEach var="user" items="${requestScope.users}">
                    <div class="col-sm-6">
                        <div class="card">
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/controller?command=update_role" method="post">
                                    <h5 class="card-title"><strong><ut:locale_tag key="name"/>:</strong> ${user.name}</h5>
                                    <h5 class="card-title"><strong><ut:locale_tag key="users.role"/>:</strong> ${user.role}</h5>
                                    <input type="hidden" name="userId" value="${user.id}" />
                                    <button class="btn btn-primary"><ut:locale_tag key="users.update-role"/></button>
                                </form>
                                <form action="${pageContext.request.contextPath}/controller?command=rollback_role" method="post">
                                    <input type="hidden" name="userId" value="${user.id}" />
                                    <button class="btn btn-primary"><ut:locale_tag key="users.rollback-role"/></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
