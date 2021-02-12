<%--
  Created by IntelliJ IDEA.
  User: xfase
  Date: 2/12/2021
  Time: 9:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
    <h2>Log in:</h2>
    <form action="${pageContext.request.contextPath}/controller?command=login" method="post">
        <p>Name: <input type="text" name="userName" required></p>
        <p>Password: <input type="password" name="userPassword" required></p>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
