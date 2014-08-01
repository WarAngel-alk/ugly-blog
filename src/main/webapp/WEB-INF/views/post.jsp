<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div class="col-md-9">

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
                    <div class="comment-voting col-md-4 col-md-offset-4">
                        <c:url value="/resources/images/vote_up_inactive.png" var="voteUpUrl"/>
                        <c:url value="/resources/images/vote_down_inactive.png" var="voteDownUrl"/>
                        <img id="comment-${comment.id}-vote-up" class="comment-vote-up" src="${voteUpUrl}"
                             onclick="commentVote(true, ${post.id}, ${comment.id})"/>
                        <span id="comment-${comment.id}-rating"
                              title="Positive: ${comment.positiveMarks} Negative: ${comment.negativeMarks}">${comment.rating}</span>
                        <img id="comment-${comment.id}-vote-down" class="comment-vote-down" src="${voteDownUrl}"
                             onclick="commentVote(false, ${post.id}, ${comment.id})"/>
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

</div>