<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header">
    <a href="<s:url value="/home"/>">
        <div class="header_logo">
            <img class="header_image"
                 src="<s:url value="/resources/images/header_logo.png"/>" width="64" height="64"/>
        </div>
    </a>
    <span id="header_title">Ugly blog</span>

    <div class="header_icons">
        <a href="<s:url value="/logout"/>">
            <img class="header_image"
                 src="<s:url value="/resources/images/header_logout.png"/>" width="64" height="64"/>
        </a>
        <a href="<s:url value="/mail/in"/>">
            <img class="header_image"
                 src="<s:url value="/resources/images/header_mail.png"/>" width="64" height="64"/>
        </a>
    </div>
</div>