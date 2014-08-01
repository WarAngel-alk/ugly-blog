<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="col-md-9">

    <sec:authentication property="name" var="currentUserName"/>

    <tags:post postText="${post.text}" post="${post}" isHomePage="${false}"/>

    <div class="comments">
        <c:forEach items="${post.comments}" var="comment">
            <div class="comment">
                <div class="comment-title row">
                    <div class="col-md-4">
                        <div class="comment-avatar">
                            <s:url var="commentAuthorUrl" value="/user/${comment.author.id}"/>
                            <a href="${commentAuthorUrl}">
                                <c:set var="avatarFilename"
                                       value="${(fn:length(comment.author.avatarPath) ne 0) ? comment.author.avatarPath : 'default_avatar.png' }"/>
                                <s:url var="avatarPath" value="/resources/images/avatars/${avatarFilename}"/>
                                <img src="${avatarPath}" class="comment-author-avatar" width="50" height="50"/>
                            </a>
                        </div>
                        <div class="comment-info">
                            <div class="comment-date">
                                <fmt:formatDate value="${comment.postDate}" pattern="HH:mm:ss - dd.MM.yyyy"/>
                            </div>
                            <div class="comment-author">
                                <s:url var="commentAuthorUrl" value="/user/${comment.author.id}"/>
                                <a href="${commentAuthorUrl}">${comment.author.name}</a>
                            </div>
                        </div>
                    </div>

                    <div class="comment-buttons">

                        <c:set value="${comment.author.name eq currentUserName}" var="isUsersComment"/>
                        <sec:authorize access="hasRole('ROLE_ADMIN') or ${isUsersComment}">
                            <div class="comment-delete-panel">
                                <button class="btn btn-danger comment-delete-btn"
                                        onclick="deleteComment(${post.id}, ${comment.id})">
                                    X
                                </button>
                            </div>
                        </sec:authorize>

                        <div class="comment-voting">

                            <sec:authorize access="isAnonymous()">
                                <c:url value="/resources/images/vote_up_disabled.png" var="voteUpUrl"/>
                                <c:url value="/resources/images/vote_down_disabled.png" var="voteDownUrl"/>
                                <img id="comment-${comment.id}-vote-up" class="comment-vote" src="${voteUpUrl}"
                                     width="15" height="15"/>
                                <span id="comment-${comment.id}-rating"
                                      title="Positive: ${comment.positiveMarks} Negative: ${comment.negativeMarks}">${comment.rating}</span>
                                <img id="comment-${comment.id}-vote-down" class="comment-vote" src="${voteDownUrl}"
                                     width="15" height="15"/>
                            </sec:authorize>

                            <sec:authorize access="isAuthenticated()">
                                <s:eval expression="comment.getCurrentUserVote()" var="vote"/>

                                <c:if test="${vote ne null}">
                                    <c:url value="/resources/images/vote_up_${vote.mark ? 'active' : 'inactive'}.png"
                                           var="voteUpUrl"/>
                                    <c:url value="/resources/images/vote_down_${vote.mark ? 'inactive' : 'active'}.png"
                                           var="voteDownUrl"/>
                                    <img id="comment-${comment.id}-vote-up" class="comment-vote" src="${voteUpUrl}"
                                         width="15" height="15"/>
                                    <span id="comment-${comment.id}-rating"
                                          title="Positive: ${comment.positiveMarks} Negative: ${comment.negativeMarks}">${comment.rating}</span>
                                    <img id="comment-${comment.id}-vote-down" class="comment-vote"
                                         src="${voteDownUrl}" width="15" height="15"/>
                                </c:if>

                                <c:if test="${vote eq null}">
                                    <c:url value="/resources/images/vote_up_inactive.png" var="voteUpUrl"/>
                                    <c:url value="/resources/images/vote_down_inactive.png" var="voteDownUrl"/>
                                    <img id="comment-${comment.id}-vote-up" class="comment-vote" src="${voteUpUrl}"
                                         onclick="commentVote(true, ${post.id}, ${comment.id})" width="15" height="15"/>
                                    <span id="comment-${comment.id}-rating"
                                          title="Positive: ${comment.positiveMarks} Negative: ${comment.negativeMarks}">${comment.rating}</span>
                                    <img id="comment-${comment.id}-vote-down" class="comment-vote" src="${voteDownUrl}"
                                         onclick="commentVote(false, ${post.id}, ${comment.id})" width="15"
                                         height="15"/>
                                </c:if>

                            </sec:authorize>

                        </div>

                    </div>

                </div>
                <div class="comment-content">
                        ${comment.text}
                </div>
            </div>
        </c:forEach>
    </div>

    <c:url value="/post/${post.id}/comment" var="addCommentUrl"/>
    <div class="col-md-6">
        <div class="form-label">
            Add new comment:
        </div>
        <div class="form">
            <form:form commandName="newComment" method="put" action="${addCommentUrl}" cssClass="form-group">
                <div class="form-unit">
                    Text:
                    <form:textarea path="text" cssClass="form-control"/>
                    <form:errors cssClass="alert-danger form-control" path="text"/>
                </div>
                <div class="form-unit">
                    <input class="btn btn-success form-control" type="submit">
                </div>
            </form:form>
        </div>
    </div>

    <s:url var="votingJsUrl" value="/resources/js/voting.js"/>
    <script language="javascript" type="text/javascript" src="${votingJsUrl}">
    </script>

    <c:url var="deletePostJsUrl" value="/resources/js/deletePost.js"/>
    <script language="javascript" type="text/javascript" src="${deletePostJsUrl}">
    </script>

    <c:url var="deleteCommentJsUrl" value="/resources/js/deleteComment.js"/>
    <script language="javascript" type="text/javascript" src="${deleteCommentJsUrl}">
    </script>

</div>