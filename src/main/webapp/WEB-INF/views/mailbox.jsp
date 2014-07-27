<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
    <sec:authentication property="principal.username" var="currentUserName"/>
    <s:forEach items="${messageList}" var="message">
        <div class="bordered_element">
            <a href="<s:url value="/mail/message/${message.id}"/>">

                <s:if test="${currentUserName eq message.receiver.name and !message.read}">
                    <b>${message.subject}</b>
                </s:if>
                <s:if test="${currentUserName ne message.receiver.name or message.read}">
                    ${message.subject}
                </s:if>
                - ${message.date}
            </a>
        </div>
    </s:forEach>
</div>