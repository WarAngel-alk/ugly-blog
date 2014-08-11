<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="header">
    <a href="<s:url value="/home"/>">
        <div class="header_logo">
            <img class="header_image"
                 src="<s:url value="/resources/images/header_logo.png"/>" width="64" height="64"/>
        </div>
    </a>
    <span id="header_title">Ugly blog</span>

    <div class="header_icons">
        <sec:authorize access="isAnonymous()">
            <a href="<s:url value="/login"/>">
                <img class="header_image"
                     src="<s:url value="/resources/images/header_login.png"/>" width="64"
                     height="64"/>
            </a>
            <a href="<s:url value="/signup"/>">
                <img class="header_image"
                     src="<s:url value="/resources/images/header_signup.png"/>" width="64"
                     height="64"/>
            </a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <a href="<s:url value="/logout"/>">
                <img class="header_image"
                     src="<s:url value="/resources/images/header_logout.png"/>" width="64"
                     height="64"/>
            </a>
            <a href="<s:url value="/mail/in"/>">
                <s:url var="mailboxIconUrl" value="${headerMailboxRelativeUrl}"/>
                <img class="header_image" src="${mailboxIconUrl}" width="64" height="64"/>
            </a>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href="<s:url value="/post/add"/>">
                    <img class="header_image"
                         src="<s:url value="/resources/images/add_post.png"/>" width="64"
                         height="64"/>
                </a>
            </sec:authorize>
        </sec:authorize>
    </div>
</div>