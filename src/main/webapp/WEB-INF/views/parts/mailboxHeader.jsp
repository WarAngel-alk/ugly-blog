<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="background-color: #fbffe9;">
    <c:set var="inboxLinkWight" value="${isInbox ? 'font-weight: bold;' : ''}"/>
    <c:set var="outboxLinkWight" value="${isOutbox ? 'font-weight: bold;' : ''}"/>
    <c:set var="newMessageLinkWight" value="${isNewMessage ? 'font-weight: bold;' : ''}"/>

    <a href="<s:url value="/mail/in"/>" style="${inboxLinkWight}">Inbox</a>
    |
    <a href="<s:url value="/mail/out"/>" style="${outboxLinkWight}">Outbox</a>
    |
    <a href="<s:url value="/mail/send"/>" style="${newMessageLinkWight}">New message</a>
</div>