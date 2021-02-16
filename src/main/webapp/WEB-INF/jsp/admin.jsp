<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xfase
  Date: 2/14/2021
  Time: 7:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
    <h1>hello admin</h1>

<%--    <form action="${pageContext.request.contextPath}/controller?command=set_bookmaker">--%>
<%--        <c:if test="${not empty requestScope.users}">--%>
<%--            <h2>Users</h2>--%>
<%--            <ol>--%>
<%--                <c:forEach var="user" items="${requestScope.users}">--%>
<%--                    <li>Username: ${user.name}, role: ${user.role}</li>--%>
<%--                </c:forEach>--%>
<%--            </ol>--%>
<%--        </c:if>--%>
<%--        <a href="<c:url value="/controller?command=set_bookmaker"/>">Update role</a><br>--%>
<%--    </form>--%>
    <form action="${pageContext.request.contextPath}/controller?command=update_role" method="post">
        <input type="submit" value="Update role" />
    </form>

    <form action="${pageContext.request.contextPath}/controller?command=rollback_role" method="post">
        <input type="submit" value="Rollback role" />
    </form>


</body>
</html>
