<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="language" value="${not empty param.language ? param.language : (not empty language ? language : pageContext.request.locale)}" scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="page" var="bundle"/>

<div class="modal fade" id="signUpModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="signUpModalTitle">
                    <fmt:message key="modal.signup" bundle="${bundle}"/>
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/controller?command=signup" method="post">
                    <div class="form-group">
                        <label>
                            <fmt:message key="name" bundle="${bundle}"/>
                        </label>
                        <input type="text" class="form-control" aria-describedby="nameHelp" placeholder="Enter name" name="userName" required>
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="password" bundle="${bundle}"/>
                        </label>
                        <input type="password" class="form-control" placeholder="Password" name="userPassword" required>
                    </div>
                    <button type="submit" class="btn btn-dark btn-lg btn-block">
                        <fmt:message key="modal.button.create-acc" bundle="${bundle}"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>