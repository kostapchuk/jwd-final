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
    <div class="container col-lg-5">
        <form action="${pageContext.request.contextPath}/controller?command=withdraw" method="post">
            <div class="form-group">
                <label for="withdrawMoney"><ut:locale_tag key="money-amount"/> ($)</label>
                <input type="number" step="0.01" name="withdrawMoney" required class="form-control" id="withdrawMoney" aria-describedby="emailHelp">
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger" role="alert">
                        You cannot withdraw <c:out value="${requestScope.error}"/> $
                    </div>
                </c:if>
                <c:if test="${not empty requestScope.success}">
                    <div class="alert alert-success" role="alert">
                        You successfully withdrew <c:out value="${requestScope.success}"/> $
                    </div>
                </c:if>
            </div>
            <button type="submit" class="btn btn-warning"><ut:locale_tag key="withdraw"/></button>
        </form>
    </div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
