<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div>
    Name: ${user.name}<br/>
    Email: ${user.email}<br/>
    Registration date: ${user.registrationDate}<br/>
    <c:if test="${user.avatarPath ne null and fn:length(user.avatarPath) ne 0 }">
        <s:url value="/resources/images/avatars/{avatarPath}" var="avatarImgPath">
            <s:param name="avatarPath" value="${user.avatarPath}"/>
        </s:url>
    </c:if>
    <c:if test="${user.avatarPath eq null or fn:length(user.avatarPath) eq 0 }">
        <s:url value="/resources/images/avatars/{avatarPath}" var="avatarImgPath">
            <s:param name="avatarPath" value="default_avatar.png"/>
        </s:url>
    </c:if>
    Avatar: <img src="${avatarImgPath}" width="64" height="64"/>
</div>