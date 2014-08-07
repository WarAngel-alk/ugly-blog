<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="postText" %>
<%@ attribute name="post" type="com.my.model.Post" %>
<%@ attribute name="isPostPage" type="java.lang.Boolean" %>

<div class="post">
    <div class="post-title-line">
        <div style="display: inline-block;">
            <div class="post-date">
                <fmt:formatDate value="${post.postDate}" pattern="HH:mm:ss - dd.MM.yyyy"/>
            </div>
            <div class="post-title">
                <c:if test="${!isPostPage}">
                <s:url var="postUrl" value="/post/${post.id}"/>
                <a href="${postUrl}">
                    </c:if>
                    ${post.title}
                    <c:if test="${!isPostPage}">
                </a>
                </c:if>
            </div>
        </div>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="post-title-icons">
                <s:url var="postEditUrl" value="/post/${post.id}/edit"/>
                <a href="${postEditUrl}">
                    <s:url var="editIconUrl" value="/resources/images/edit_icon.png"/>
                    <img class="post-title-icon" src="${editIconUrl}" width="20" height="20"/>
                </a>
                <a onclick="deletePost(${post.id})">
                    <button class="btn btn-danger post-title-icon">
                        X
                    </button>
                </a>
            </div>
        </sec:authorize>
    </div>

    <div class="post-content">${postText}</div>

    <c:if test="${!isPostPage}">
        <s:url var="postUrl" value="/post/${post.id}"/>
        <a href="${postUrl}">
            <button class="btn btn-default">
                To post page
            </button>
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
                        <img id="post-${post.id}-vote-down" class="post-vote" src="${voteDownUrl}" width="20"
                             height="20"/>
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
            Tags:
            <c:forEach var="tag" items="${post.tags}" varStatus="iter">
                <s:url var="tagUrl" value="/posts/tag/${tag.name}"/>
                <a href="${tagUrl}" class="post-tag">${tag.name}</a>${!iter.last ? ', ' : ''}
            </c:forEach>
        </div>
    </div>
</div>