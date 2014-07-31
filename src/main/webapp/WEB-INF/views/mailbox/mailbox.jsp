<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <sec:authentication property="principal.username" var="currentUserName"/>
    <div class="container-fluid">
        <c:set value="${false}" var="isOdd"/>
        <s:forEach items="${messageList}" var="message">
            <c:set value="${!isOdd}" var="isOdd"/>
            <div id="message_${message.id}" class="row mailbox-line"
                 style="background-color: ${(isOdd ? '#edfaff' : '#deebf0')};">
                <div class="col-md-8">
                    <s:url value="/mail/message/${message.id}" var="messageUrl"/>
                    <a href="${messageUrl}">
                            <%-- Bold if new unread message --%>
                    <span ${((isInbox and !message.read) ? 'style="font-weight: bold;"' : '')}>
                        <c:set var="isSubjectEmpty" value="${fn:length(message.subject) eq 0}"/>
                        <c:set var="isSubjectLongerThan50"
                               value="${fn:length(message.subject) gt 50}"/>
                        ${isSubjectEmpty ? "No subject" : fn:substring(message.subject, 0, 50)}
                        ${isSubjectLongerThan50 ? '...' : ''}
                        <%-- Yep, it's better than stupid <c:if> tag --%>
                    </span>
                    </a>
                </div>
                <div class="col-md-1">
                    <c:set value="${(isInbox ? message.sender : message.receiver)}"
                           var="messageAuthor"/>
                    <s:url value="/user/${messageAuthor.id}" var="messageAuthorUrl"/>
                    <a href="${messageAuthorUrl}">
                            ${messageAuthor.name}
                    </a>
                </div>
                <div class="col-md-2">
                    <i>
                        <fmt:formatDate value="${message.date}" pattern="HH mm ss - d-M-y"/>
                    </i>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-warning delete-message-btn"
                            onclick="deleteMessage(${message.id}, ${isInbox})">
                        X
                    </button>
                </div>
            </div>
        </s:forEach>
    </div>
</div>

<s:url var="messageDeleteJsUrl" value="/resources/js/deleteMessage.js"/>
<script language="javascript" type="text/javascript" src="${messageDeleteJsUrl}">
</script>

</div>