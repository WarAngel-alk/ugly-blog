<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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

            <div class="post-voting">

                <sec:authorize access="isAnonymous()">
                    <c:url value="/resources/images/vote_up_disabled.png" var="voteUpUrl"/>
                    <c:url value="/resources/images/vote_down_disabled.png" var="voteDownUrl"/>
                    <img id="post-${post.id}-vote-up" class="post-vote" src="${voteUpUrl}" width="20" height="20"/>
                    <span id="post-${post.id}-rating"
                          title="Positive: ${post.positiveMarks} Negative: ${post.negativeMarks}">
                            ${post.rating}
                    </span>
                    <img id="post-${post.id}-vote-down" class="post-vote" src="${voteDownUrl}" width="20" height="20"/>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <s:eval expression="post.getCurrentUserVote()" var="vote"/>

                    <c:if test="${vote ne null}">
                        <c:url value="/resources/images/vote_up_${vote.mark ? 'active' : 'inactive'}.png"
                               var="voteUpUrl"/>
                        <c:url value="/resources/images/vote_down_${vote.mark ? 'inactive' : 'active'}.png"
                               var="voteDownUrl"/>
                        <img id="post-${post.id}-vote-up" class="post-vote" src="${voteUpUrl}" width="20" height="20"/>
                        <span id="post-${post.id}-rating"
                              title="Positive: ${post.positiveMarks} Negative: ${post.negativeMarks}">${post.rating}</span>
                        <img id="post-${post.id}-vote-down" class="post-vote" src="${voteDownUrl}" width="20" height="20"/>
                    </c:if>

                    <c:if test="${vote eq null}">
                        <c:url value="/resources/images/vote_up_inactive.png" var="voteUpUrl"/>
                        <c:url value="/resources/images/vote_down_inactive.png" var="voteDownUrl"/>
                        <img id="post-${post.id}-vote-up" class="post-vote" src="${voteUpUrl}"
                             onclick="postVote(true, ${post.id})" width="20" height="20"/>
                        <span id="post-${post.id}-rating"
                              title="Positive: ${post.positiveMarks} Negative: ${post.negativeMarks}">${post.rating}</span>
                        <img id="post-${post.id}-vote-down" class="post-vote" src="${voteDownUrl}"
                             onclick="postVote(false, ${post.id})" width="20" height="20"/>
                    </c:if>

                </sec:authorize>

            </div>

            <div class="post-comments">
                Comments: ${fn:length(post.comments)}
            </div>
        </div>
        <div class="row post-tags">
            Tags: ${post.tagsString}
        </div>
    </div>
</div>