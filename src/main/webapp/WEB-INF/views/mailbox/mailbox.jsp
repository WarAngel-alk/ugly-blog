<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
    <sec:authentication property="principal.username" var="currentUserName"/>
    <s:forEach items="${messageList}" var="message">
        <div id="message_${message.id}" class="bordered_element">
            <a href="<s:url value="/mail/message/${message.id}"/>">

                <s:if test="${isInbox and !message.read}">
                    <b>${message.subject}</b>
                </s:if>
                <s:if test="${!isInbox or message.read}">
                    ${message.subject}
                </s:if>
                - ${message.date}
            </a>

            <div style="display: inline;" onclick="deleteMessage(${message.id}, ${isInbox})">
                Delete
            </div>
        </div>
    </s:forEach>

    <s:url var="messageDeleteJsUrl" value="/resources/js/deleteMessage.js"/>
    <script language="javascript" type="text/javascript" src="${messageDeleteJsUrl}">
    </script>
</div>