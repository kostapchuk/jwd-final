<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
    <h2>Users</h2>
    <c:if test="${not empty requestScope.users}">
        <c:forEach var="user" items="${requestScope.users}">
            <li>Username: ${user.name}, role: ${user.role}</li>
            <form action="${pageContext.request.contextPath}/controller?command=update_role" method="post">
                <input type="hidden" name="userName" value="${user.name}" />
                <input type="submit" value="Update role" />
            </form>
            <form action="${pageContext.request.contextPath}/controller?command=rollback_role" method="post">
                <input type="hidden" name="userName" value="${user.name}" />
                <input type="submit" value="Rollback role" />
            </form>
        </c:forEach>
    </c:if>
</body>
</html>
