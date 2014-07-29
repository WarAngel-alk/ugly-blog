<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div>
    <s:url value="/user" var="addUserUrl"/>
    <form:form action="${addUserUrl}" method="put" commandName="user">
        <div><form:errors cssClass="alert-danger" path="name"/></div>
        <div><form:errors cssClass="alert-danger" path="pass"/></div>
        <div><form:errors cssClass="alert-danger" path="email"/></div>
        <div><form:errors cssClass="alert-danger" path="avatarPath"/></div>
        Name: <form:input path="name"/><br/>
        Password: <form:password path="pass"/><br/>
        Email: <form:input path="email"/><br/>
        Avatar path: <form:input path="avatarPath"/><br/>
        <form:button>
            Sign up
        </form:button>
    </form:form>
</div>