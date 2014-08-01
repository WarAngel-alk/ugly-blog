<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-10 col-md-offset-1 form">
    <s:url value="${isNewPost ? '/post' : '/post/'}${isNewPost ? '' : post.id}" var="postUrl"/>
    <c:set var="formMethod" value="${isNewPost ? 'put' : 'post'}"/>
    <form:form commandName="post" action="${postUrl}" method="${formMethod}" cssClass="form-group">
        <div class="form-unit">
            <div>Title:</div>
            <div><form:input path="title" cssClass="form-control" placeholder="Title"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="title"/></div>
        </div>
        <div class="form-unit">
            <div>Text:</div>
            <div><form:textarea path="text" cssClass="form-control" placeholder="Text" rows="20"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="text"/></div>
        </div>
        <div class="form-unit">
            <div>Tags (separated with comma):</div>
            <div><input type="text" name="tagsString" class="form-control" placeholder="Tags" value="${tagsString}"/>
            </div>
            <div>
                <c:if test="${fn:length(tagError) ne 0}">
                    <span class="alert-danger form-control" id="tagsErrorAlert"></span>
                </c:if>
            </div>
        </div>
        <div class="form-unit">
            <div><input type="submit" class="btn btn-success form-control" value="${isNewPost ? 'Add' : 'Update'}"/>
            </div>
        </div>
    </form:form>
</div>