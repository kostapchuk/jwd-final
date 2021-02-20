<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<c:url value="/controller?command=show_all_matches_page"/>">Tiger bet</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-link" href="<c:url value="/controller?command=show_all_matches_page"/>">Home</a>
            <a class="nav-link" href="<c:url value="/controller?command=show_all_bets_page"/>">My bets</a>
            <a class="nav-link" href="<c:url value="/controller?command=show_deposit_page"/>">Deposit</a>
            <a class="nav-link" href="<c:url value="/controller?command=show_withdraw_page"/>">Withdraw</a>
            <a class="nav-link" href="<c:url value="/controller?command=show_bookmaker_page"/>">Bookmaker page</a>
            <a class="nav-link" href="<c:url value="/controller?command=show_all_users_page"/>">All users</a>
            <%--<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>--%>
            <a class="nav-link" href="<c:url value="/controller?command=show_signup_page"/>">Sign up</a>
            <a class="nav-link" href="<c:url value="/controller?command=show_login_page"/>">Log in</a>
            <a class="nav-link" href="<c:url value="/controller?command=logout"/>">Log out</a>
        </div>
    </div>
</nav>
