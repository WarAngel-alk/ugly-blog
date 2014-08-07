<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
    <div class="col-md-6 col-md-offset-3 message-body">
        <div class="row message-info">
            <s:url value="/user/${message.id}" var="userUrl"/>
            <a href="${userUrl}">
                ${message.sender.name}
            </a>
            -
            <fmt:formatDate value="${message.date}" pattern="HH:mm:ss - dd.MM.yyyy"/>
        </div>
        <div class="row message-subject">
            ${fn:length(message.subject) ne 0 ? message.subject : ''}
        </div>
        <div class="row message-text">
            ${message.text}
        </div>
    </div>
    <div class="col-md-6 col-md-offset-3 form-label">
        Answer:
    </div>
    <%@ include file="newMessageForm.jsp" %>

</div>