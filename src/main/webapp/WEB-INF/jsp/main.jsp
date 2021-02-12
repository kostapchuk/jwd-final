<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xfase
  Date: 2/11/2021
  Time: 1:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<a href="<c:url value="/controller?command=show_all_bets"/>"></a>
<a href="<c:url value="/controller?command=join"/>">sign up</a>
<a href="<c:url value="/controller?command=log_in"/>">log in</a>
</body>
</html>
