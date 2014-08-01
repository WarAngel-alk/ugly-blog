<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="postText" %>
<%@ attribute name="post" type="com.my.model.Post" %>
<%@ attribute name="isHomePage" type="java.lang.Boolean" %>

<div class="post">
    <div class="post-date"><fmt:formatDate value="${post.postDate}" pattern="HH:mm:ss - dd.MM.yyyy"/></div>
    <div class="post-title">
        <c:if test="${isHomePage}">
        <s:url var="postUrl" value="/post/${post.id}"/>
        <a href="${postUrl}">
            </c:if>
            ${post.title}
            <c:if test="${isHomePage}">
        </a>
        </c:if>
    </div>

    <div class="post-content">${postText}</div>

    <c:if test="${isHomePage}">
        <s:url var="postUrl" value="/post/${post.id}"/>
        <a href="${postUrl}">
            <div class="btn btn-default">
                To post page
            </div>
        </a>
    </c:if>

    <div class="post-info container">
        <div class="row">
            <div class="post-voting col-md-2">
                <c:url value="/resources/images/vote_up_inactive.png" var="inactiveVoteUpUrl"/>
                <c:url value="/resources/images/vote_down_inactive.png" var="inactiveVoteDownUrl"/>
                <img id="post-${post.id}-vote-up" class="post-vote-up" src="${inactiveVoteUpUrl}"
                     onclick="postVote(true, ${post.id})"/>
                <span id="post-${post.id}-rating"
                      title="Positive: ${post.positiveMarks} Negative: ${post.negativeMarks}">${post.rating}</span>
                <img id="post-${post.id}-vote-down" class="post-vote-down" src="${inactiveVoteDownUrl}"
                     onclick="postVote(false, ${post.id})"/>
            </div>
            <div class="post-comments col-md-2 col-md-offset-4">
                Comments: ${fn:length(post.comments)}
            </div>
        </div>
        <div class="row post-tags">
            Tags: ${post.tagsString}
        </div>
    </div>
</div>