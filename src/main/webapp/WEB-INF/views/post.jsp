<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
    <div class="bordered_element">
        <p><i>${post.postDate}</i></p>

        <p><b>${post.title}</b></p>

        <p>${post.text}</p>

        <p>
            Positive: ${post.positiveMarks};
            Negative: ${post.negativeMarks}.
            Vote up | down</p>

        <p>Comments: ${fn:length(post.comments)}</p>
    </div>

    <c:forEach items="${post.comments}" var="comment">
        <div class="bordered_element">
            <p>Date: ${comment.postDate}</p>

            <p>Author: ${comment.author.name}</p>

            <p>${comment.text}</p>

            <p>
                Positive: ${comment.positiveMarks};
                Negative: ${comment.negativeMarks}
                Vote up | down
            </p>
        </div>
    </c:forEach>

    <c:url value="/post/${post.id}/comment" var="addCommentUrl"/>
    <div class="bordered_element">
        Add new comment:
        <form:form commandName="newComment" method="put"
                   action="${addCommentUrl}">
            Text:<br>
            <form:textarea path="text"/><br>
            <form:button>
                Send
            </form:button>
        </form:form>
    </div>
</div>