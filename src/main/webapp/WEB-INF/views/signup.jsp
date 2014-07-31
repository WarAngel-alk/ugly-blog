<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div class="col-md-6 col-md-offset-3 form">
    <s:url value="/user" var="addUserUrl"/>
    <form:form action="${addUserUrl}" method="put" commandName="user" cssClass="form-group">
        <div class="form-unit">
            <div>Username:</div>
            <div><form:input path="name" cssClass="form-control" placeholder="Username"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="name"/></div>
        </div>
        <div class="form-unit">
            <div>Password:</div>
            <div><form:input path="pass" cssClass="form-control" placeholder="Password"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="pass"/></div>
        </div>
        <div class="form-unit">
            <div>Email:</div>
            <div><form:input path="email" cssClass="form-control" placeholder="Email"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="email"/></div>
        </div>
        <div class="form-unit">
            <div>Avatar URL</div>
            <div><form:input path="avatarPath" cssClass="form-control" placeholder="Avatar URL"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="avatarPath"/></div>
        </div>
        <div class="form-unit">
            <input type="submit" class="btn btn-success form-control" value="Sign up"/>
        </div>
    </form:form>
</div>