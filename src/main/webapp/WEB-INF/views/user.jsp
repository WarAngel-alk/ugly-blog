<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <div class="user-avatar">
        <c:set var="avatarFilename"
               value="${(fn:length(user.avatarPath) ne 0) ? user.avatarPath : 'default_avatar.png' }"/>
        <s:url var="avatarPath" value="/resources/images/avatars/${avatarFilename}"/>
        <img src="${avatarPath}" width="84" height="84"/>
    </div>
    <div class="user-info">
        <div class="user-name">
            Username: ${user.name}
        </div>
        <div class="user-email">
            Email: ${user.email}
        </div>
        <div class="user-date">
            Date of registration: <fmt:formatDate value="${user.registrationDate}" pattern="HH:mm:ss - dd.MM.yyyy"/>
        </div>
    </div>
    <div class="user-buttons">
        <s:url var="sendMsgUrl" value="/mail/send?receiver=${user.name}"/>
        <a href="${sendMsgUrl}">
            <s:url var="sendMsgIconUrl" value="/resources/images/send_message.png"/>
            <img class="user-send-message-btn" src="${sendMsgIconUrl}" width="24" height="24"/>
        </a>
    </div>
</div>