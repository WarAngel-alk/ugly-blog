<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <s:url value="/post" var="postUrl"/>
    <c:set var="formMethod" value="${(post.id == 0) ? 'put' : 'post'}"/>
    <form:form commandName="post" action="${postUrl}" method="${formMethod}">
        <div><form:errors cssClass="alert-danger" path="title"/></div>
        <div><form:errors cssClass="alert-danger" path="text"/></div>
        Title:<br/>
        <form:input path="title"/><br/>
        Text:<br/>
        <form:textarea path="text"/><br/>
        Tags:<br/>
        <input type="text" name="tagsString"/><br/>
        <form:button>
            Save
        </form:button>
    </form:form>
</div>