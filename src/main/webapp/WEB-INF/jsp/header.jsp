<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container-lg">
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
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_all_bets_page"/>">
                            My bets
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_deposit_page"/>">
                            Deposit
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_withdraw_page"/>">
                            Withdraw
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userRole == 'BOOKMAKER' or sessionScope.userRole == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_bookmaker_page"/>">
                            Bookmaker page
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userRole == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_all_users_page"/>">
                            All users
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=logout"/>">
                            Log out
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>

        <div>
            <c:if test="${not empty sessionScope.userName}">
                <li class="navbar-text text-dark">
                        ${sessionScope.userName}
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.userName}">
                <li class="navbar-text text-dark">
                        ${sessionScope.userBalance} $
                </li>
            </c:if>
            <button type="button" class="btn btn-outline-dark btn-lg" data-toggle="modal" data-target="#exampleModalCenter">
                Sign in
            </button>
        </div>
    </nav>
</div>


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
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                This name exists.
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" placeholder="Password" name="userPassword" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                            <div class="invalid-feedback">
                                Invalid password.
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success btn-lg btn-block">Login to account</button>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-dark btn-lg btn-block" data-toggle="modal" data-target="#signUpModal" data-dismiss="modal">Create an account</button>
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
                        <button type="submit" class="btn btn-dark btn-lg btn-block">Create an account</button>
                    </form>
                </div>
            </div>
        </div>
    </div>



    <script>
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>
