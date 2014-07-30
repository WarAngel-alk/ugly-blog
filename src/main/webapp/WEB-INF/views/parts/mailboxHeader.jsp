<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <ol class="breadcrumb message-header">
        <li ${isInbox ? 'class="active-mailbox-header-link"' : ''}>
            <a href="<s:url value="/mail/in"/>">Inbox</a>
        </li>
        <li ${isOutbox ? 'class="active-mailbox-header-link"' : ''}>
            <a href="<s:url value="/mail/out"/>">Outbox</a>
        </li>
        <li ${isNewMessage ? 'class="active-mailbox-header-link"' : ''}>
            <a href="<s:url value="/mail/send"/>">New message</a>
        </li>
    </ol>
</div>