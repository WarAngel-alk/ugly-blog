<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <s:forEach items="${messageList}" var="message">
        <div class="bordered_element">
            <a href="<s:url value="/mail/message/${message.id}"/>">
                <b>${message.subject}</b> - ${message.date}
            </a>
        </div>
    </s:forEach>
</div>