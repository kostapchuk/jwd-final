<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<c:url value="/controller?command=show_all_matches_page"/>">Tiger bet</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/controller?command=show_all_matches_page"/>">
                    Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/controller?command=show_all_bets_page"/>">
                    My bets
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/controller?command=show_deposit_page"/>">
                    Deposit
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/controller?command=show_withdraw_page"/>">
                    Withdraw
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/controller?command=show_bookmaker_page"/>">
                    Bookmaker page
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/controller?command=show_all_users_page"/>">
                    All users
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/controller?command=logout"/>">
                    Log out
                </a>
            </li>
        </ul>
    </div>

    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
        Sign in
    </button>

    <!-- Sign in MODAL -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle">Sign in</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/controller?command=login" method="post">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" aria-describedby="emailHelp" placeholder="Enter name" name="userName" required>
<%--                            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
                        </div>
                        <div class="form-group">
                            <labe>Password</labe>
                            <input type="password" class="form-control" placeholder="Password" name="userPassword" required>
                        </div>
<%--                        <div class="form-check">--%>
<%--                            <input type="checkbox" class="form-check-input" id="exampleCheck1">--%>
<%--                            <label class="form-check-label" for="exampleCheck1">Check me out</label>--%>
<%--                        </div>--%>
                        <button type="submit" class="btn btn-primary">Login to account</button>
                    </form>
                    <br>
                    <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#signUpModal" data-dismiss="modal">Create an account</button>
                </div>
            </div>
        </div>
    </div>

<%--    Sign up MODAL--%>
    <div class="modal fade" id="signUpModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="signUpModalTitle">Sign up</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/controller?command=signup" method="post">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" aria-describedby="nameHelp" placeholder="Enter name" name="userName" required>
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" placeholder="Password" name="userPassword" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Create an account</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</nav>
