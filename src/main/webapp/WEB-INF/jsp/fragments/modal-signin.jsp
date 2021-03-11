<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ut" uri="/WEB-INF/tag" %>

<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8" isELIgnored="false" %>

<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">
                    <ut:locale_tag key="signin"/>
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/controller?command=login" method="post">
                    <div class="form-group">
                        <label>
                            <ut:locale_tag key="name"/>
                        </label>
                        <input type="text" class="form-control" aria-describedby="emailHelp" placeholder="<ut:locale_tag key="enter-name"/>" name="userName" required>
                    </div>
                    <div class="form-group">
                        <label>
                            <ut:locale_tag key="password"/>
                        </label>
                        <input type="password" class="form-control" placeholder="<ut:locale_tag key="enter-password"/>" name="userPassword" required>
                    </div>
                    <button type="submit" class="btn btn-success btn-lg btn-block">
                        <ut:locale_tag key="modal.button.login"/>
                    </button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-dark btn-lg btn-block" data-toggle="modal" data-target="#signUpModal" data-dismiss="modal">
                    <ut:locale_tag key="modal.button.create-acc"/>
                </button>
            </div>
        </div>
    </div>
</div>
