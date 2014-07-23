<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="content" class="col-md-9">
    <c:forEach items="${postList}" var="post">
        <div class="row post">
            <div class="post_date">${post.postDate}</div>
            <div class="post_title">${post.title}</div>
            <div class="post_text">
                    ${fn:substring(post.text, 0, 300)}...
                <form action="<s:url value="/post/${post.id}"/>" method="get">
                    <input type="submit" class="btn post_read_full_btn" value="Read full post"/>
                </form>
            </div>
            <div class="post_info">
                <img src="<s:url value="/resources/images/vote_up_inactive.png"/>"/>
                    ${post.rating}
                <img src="<s:url value="/resources/images/vote_down_inactive.png"/>"/>
                Comments: ${fn:length(post.comments)}
            </div>
        </div>
    </c:forEach>
</div>