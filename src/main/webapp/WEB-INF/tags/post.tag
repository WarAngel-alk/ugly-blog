<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="post">
    <div class="post-date"><fmt:formatDate value="${post.postDate}" pattern="HH:mm:ss - dd.MM.yyyy"/></div>
    <div class="post-title">${post.title}</div>

    <div class="post-content">${post.text}</div>

    <div class="post-info container">
        <div class="post-marks col-md-4">
            Positive: <span id="post_${post.id}_positive">${post.positiveMarks}</span>;
            Negative: <span id="post_${post.id}_negative">${post.negativeMarks}</span>
            Vote
            <span onclick="postVote(true, ${post.id})">up</span>
            |
            <span onclick="postVote(false, ${post.id})">down</span>
        </div>
        <div class="post-comments col-md-2 col-md-offset-4">
            Comments: ${fn:length(post.comments)}
        </div>
    </div>
</div>