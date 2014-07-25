<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div>
    <s:url value="/user" var="addUserUrl"/>
    <form:form action="${addUserUrl}" method="post" commandName="user">
        Name: <form:input path="name"/><br/>
        Password: <input type="text" name="pass"/><br/>
        Email: <form:input path="email"/><br/>
        Avatar path: <input type="text" name="avatarPath"/><br/>
        <form:button>
            Sign up
        </form:button>
    </form:form>
</div>