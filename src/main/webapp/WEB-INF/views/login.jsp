<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
    <form action="/j_spring_security_check">
        Name: <input type="text" name="j_username"/><br/>
        Password: <input type="password" name="j_password"/><br/>
        <input type="checkbox" value="Remember me?" name="_spring_security_remember_me"/><br/>
        <input type="submit" value="Login"/>
    </form>
</div>