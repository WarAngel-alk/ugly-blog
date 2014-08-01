<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div id="content" class="col-md-9">
    <c:forEach items="${postList}" var="post">
        <c:set var="textLongerThan1000" value="${fn:length(post.text) gt 1000}"/>
        <c:set var="postText"
               value="${textLongerThan1000 ? fn:substring(post.text, 0, 1000) : post.text}"/>
        <tags:post postText="${postText}${textLongerThan1000 ? '...' : ''}" post="${post}" isHomePage="${true}"/>
    </c:forEach>

    <c:url var="votingJsUrl" value="/resources/js/voting.js"/>
    <script language="javascript" type="text/javascript" src="${votingJsUrl}">
    </script>

</div>