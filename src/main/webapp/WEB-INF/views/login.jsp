<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div>
    <form action="<s:url value="/j_spring_security_check"/>" method="post">
        Name: <input type="text" name="j_username"/><br/>
        Password: <input type="password" name="j_password"/><br/>
        Remember me: <input type="checkbox" name="_spring_security_remember_me"/><br/>
        <input type="submit" value="Login"/>
    </form>
</div>