<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="language" value="${not empty param.language ? param.language : (not empty language ? language : pageContext.request.locale)}" scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="page" var="bundle"/>

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
                        <fmt:message key="header.home" bundle="${bundle}"/>
                    </a>
                </li>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_all_bets_page"/>">
                            <fmt:message key="header.my-bets" bundle="${bundle}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_deposit_page"/>">
                            <fmt:message key="header.deposit" bundle="${bundle}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_withdraw_page"/>">
                            <fmt:message key="header.withdraw" bundle="${bundle}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userRole == 'BOOKMAKER' or sessionScope.userRole == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_bookmaker_page"/>">
                            <fmt:message key="header.bookmaker" bundle="${bundle}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userRole == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=show_all_users_page"/>">
                            <fmt:message key="header.users" bundle="${bundle}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.userName}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller?command=logout"/>">
                            <fmt:message key="header.logout" bundle="${bundle}"/>
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
                <fmt:message key="signin" bundle="${bundle}"/>
            </button>
        </div>
    </nav>
</div>

    <jsp:include page="fragments/modal-signin.jsp"/>
    <jsp:include page="fragments/modal-signup.jsp"/>

