<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xfase
  Date: 2/11/2021
  Time: 10:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bets</title>
</head>
<body>
<h2>Bets list</h2>
<c:if test="${not empty requestScope.bets}">
    <h2>Columns</h2>
    <c:forEach var="bet" items="${requestScope.bets}">
        <li>${bet.id}</li>
    </c:forEach>
</c:if>
</body>
</html>
