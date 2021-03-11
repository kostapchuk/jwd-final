<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib prefix="ut" uri="/WEB-INF/tag" %>

<div class="container-lg">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <img src="./img/logo.png" width="111" height="70" alt="Tiger bet">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <h5>
                        <a class="nav-link" href="<c:url value="/controller?command=show_events_page"/>">
                            <ut:locale_tag key="header.home"/>
                        </a>
                    </h5>
                </li>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <h5>
                            <a class="nav-link" href="<c:url value="/controller?command=show_bets_page"/>">
                                <ut:locale_tag key="header.my-bets"/>
                            </a>
                        </h5>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <h5>
                            <a class="nav-link" href="<c:url value="/controller?command=show_deposit_page"/>">
                                <ut:locale_tag key="deposit"/>
                            </a>
                        </h5>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <h5>
                            <a class="nav-link" href="<c:url value="/controller?command=show_withdraw_page"/>">
                                <ut:locale_tag key="withdraw"/>
                            </a>
                        </h5>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userRole == 'BOOKMAKER' or sessionScope.userRole == 'ADMIN'}">
                    <li class="nav-item">
                        <h5>
                            <a class="nav-link" href="<c:url value="/controller?command=show_bookmaker_page"/>">
                                <ut:locale_tag key="header.bookmaker"/>
                            </a>
                        </h5>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userRole == 'ADMIN'}">
                    <li class="nav-item">
                        <h5>
                            <a class="nav-link" href="<c:url value="/controller?command=show_users_page"/>">
                                <ut:locale_tag key="users"/>
                            </a>
                        </h5>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <h5>
                            <a class="nav-link" href="<c:url value="/controller?command=logout"/>">
                                <ut:locale_tag key="header.logout"/>
                            </a>
                        </h5>
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
                <ut:locale_tag key="signin"/>
            </button>
        </div>
    </nav>
</div>

    <jsp:include page="fragments/modal-signin.jsp"/>
    <jsp:include page="fragments/modal-signup.jsp"/>

