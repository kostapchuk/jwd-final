<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Withdraw</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=withdraw" method="post">
        <p>Amount of money: <input type="number" step="0.01" name="withdrawMoney" required> $</p>
        <input type="submit" value="Withdraw" />
    </form>
</body>
</html>
