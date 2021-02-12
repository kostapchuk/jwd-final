<%--
  Created by IntelliJ IDEA.
  User: xfase
  Date: 2/12/2021
  Time: 11:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Join TigerBet</title>
</head>
<body>
    <h2>Create your account</h2>
    <form action="${pageContext.request.contextPath}/user?command=show_all_bets" method="post">
        <p>Name: <input type="text" name="username"></p>
        <p>Email: <input type="text" name="email"></p>
        <p>Password: <input type="text" name="password"></p>
        <input type="submit" value="Submit">
    </form>

</body>
</html>
