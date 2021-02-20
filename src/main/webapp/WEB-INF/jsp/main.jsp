<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<a href="<c:url value="/controller?command=show_all_bets_page"/>">all bets</a><br>
<a href="<c:url value="/controller?command=show_signup_page"/>">sign up</a><br>
<a href="<c:url value="/controller?command=show_login_page"/>">log in</a><br>
<a href="<c:url value="/controller?command=logout"/>">log out</a><br>
<a href="<c:url value="/controller?command=show_all_matches_page"/>">all matches</a><br>
<a href="<c:url value="/controller?command=show_bookmaker_page"/>">bookmaker page</a><br>
<a href="<c:url value="/controller?command=show_all_users_page"/>">all users</a><br>
<a href="<c:url value="/controller?command=show_deposit_page"/>">deposit</a><br>
<a href="<c:url value="/controller?command=show_withdraw_page"/>">withdraw</a><br>

</body>
</html>
