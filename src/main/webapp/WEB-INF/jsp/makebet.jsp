<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bet</title>
</head>
<body>
    <h2>Creating bet...</h2>
    <form action="${pageContext.request.contextPath}/controller?command=make_bet" method="post">
        <p>Prediction: <input type="text" name="userResult" required></p>
        <p>Money: <input type="number" step="0.01" name="betMoney" required></p>
        <input type="submit" value="Make bet" />
    </form>
</body>
</html>
