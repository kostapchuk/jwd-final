<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ut" uri="/WEB-INF/tag" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="modal fade" id="makeBet" tabindex="-1" role="dialog" aria-labelledby="makeBet" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"><ut:locale_tag key="matches.set-stake"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="${pageContext.request.contextPath}/controller?command=place_bet" method="post">
                <input type="hidden" id="matchInput" name="matchId"/>
                <input type="hidden" id="resultInput" name="result"/>
                <div class="modal-body">
                    <div class="form-group">
                        <input type="number" step="0.01" class="form-control" id="betMoney" name="betMoney" required>
                    </div>
                    <span id="toReturn"></span>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success btn-block"><ut:locale_tag
                            key="matches.place-bet"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
