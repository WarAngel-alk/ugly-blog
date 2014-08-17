<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="content" class="col-md-9">
    <div class="posts-tip-label">
        ${tipMessage}
    </div>
    <c:forEach items="${postList}" var="post">
        <c:set var="textLongerThan1000" value="${fn:length(post.text) gt 1000}"/>
        <c:set var="postText"
               value="${textLongerThan1000 ? fn:substring(post.text, 0, 1000) : post.text}"/>
        <tags:post postText="${postText}${textLongerThan1000 ? '...' : ''}" post="${post}" isPostPage="${false}"/>
    </c:forEach>

    <ul class="pagination pagination-lg posts-pagination">
        <c:forEach begin="1" end="${maxPages}" var="pageNumber">
            <s:url var="pageUrl" value="/posts/page/${pageNumber}"/>
            <li class="${pageNumber eq currentPage ? 'active' : ''}">
                <a href="${pageUrl}">${pageNumber}</a>
            </li>
        </c:forEach>
    </ul>

    <s:url var="votingJsUrl" value="/resources/js/voting.js"/>
    <script language="javascript" type="text/javascript" src="${votingJsUrl}">
    </script>

    <s:url var="deletePostJsUrl" value="/resources/js/deletePost.js"/>
    <script language="javascript" type="text/javascript" src="${deletePostJsUrl}">
    </script>

</div>