<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deposit</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=deposit" method="post">
        <p>Amount of money: <input type="number" step="0.01" name="depositMoney" required> $</p>
        <input type="submit" value="Deposit" />
    </form>
</body>
</html>
