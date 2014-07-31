<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <div class="bordered_element">
        <p><i>${post.postDate}</i></p>

        <p><b>${post.title}</b></p>

        <p>${post.text}</p>

        <p>
            Positive: <span id="post_${post.id}_positive">${post.positiveMarks}</span>;
            Negative: <span id="post_${post.id}_negative">${post.negativeMarks}</span>
            Vote
            <span onclick="postVote(true, ${post.id})">up</span>
            |
            <span onclick="postVote(false, ${post.id})">down</span>
        </p>

        <p>Comments: ${fn:length(post.comments)}</p>
    </div>

    <c:forEach items="${post.comments}" var="comment">
        <div class="bordered_element">
            <p>Date: ${comment.postDate}</p>

            <p>Author: <a
                    href="<s:url value="/user/${comment.author.id}"/>">${comment.author.name}</a>
            </p>

            <p>${comment.text}</p>

            <p>
                Positive: <span id="comment_${comment.id}_positive">${comment.positiveMarks}</span>;
                Negative: <span id="comment_${comment.id}_negative">${comment.negativeMarks}</span>
                Vote
                <span onclick="commentVote(true, ${post.id}, ${comment.id})">up</span>
                |
                <span onclick="commentVote(false, ${post.id}, ${comment.id})">down</span>
            </p>
        </div>
    </c:forEach>

    <c:url value="/post/${post.id}/comment" var="addCommentUrl"/>
    <div class="bordered_element">
        Add new comment:
        <form:form commandName="newComment" method="put"
                   action="${addCommentUrl}">
            <div><form:errors cssClass="alert-danger" path="text"/></div>
            Text:<br>
            <form:textarea path="text"/><br>
            <form:button>
                Send
            </form:button>
        </form:form>
    </div>

    <s:url var="messageDeleteJsUrl" value="/resources/js/voting.js"/>
    <script language="javascript" type="text/javascript" src="${messageDeleteJsUrl}">
    </script>

</div>