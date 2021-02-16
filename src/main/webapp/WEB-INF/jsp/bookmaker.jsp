<%--
  Created by IntelliJ IDEA.
  User: xfase
  Date: 2/16/2021
  Time: 3:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create match</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller?command=create_match" method="post">
        <p>Sport type: <input type="text" name="sportType" required></p>
        <p>Start: <input type="datetime-local" name="startTime" required></p>
        <p>First team: <input type="text" name="firstTeam" required></p>
        <p>Second team: <input type="text" name="secondTeam" required></p>
        <p>First team coefficient: <input type="number" step="0.01" name="firstTeamCoefficient" required></p>
        <p>Second team coefficient: <input type="number" step="0.01" name="secondTeamCoefficient" required></p>
        <p>Draw coefficient: <input type="number" name="drawTeamCoefficient" required></p>
        <input type="submit" value="Create match" />
    </form>
</body>
</html>
