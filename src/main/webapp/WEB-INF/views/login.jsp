<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-6 col-md-offset-3 form">
    <form action="<s:url value="/j_spring_security_check"/>" method="post" class="form-group">
        <div class="alert-danger">
            <h4>${(fn:length(errorMessage) ne 0) ? errorMessage : ''}</h4>
        </div>
        <div class="form-unit">
            <div>Name:</div>
            <div><input type="text" name="j_username" class="form-control" placeholder="Name"/></div>
        </div>
        <div class="form-unit">
            <div>Password:</div>
            <div><input type="password" name="j_password" class="form-control" placeholder="Password"/></div>
        </div>
        <div class="form-unit">
            <div class="form-inline">
                Remember me
                <input type="checkbox" name="_spring_security_remember_me" class="checkbox-inline"
                       placeholder="Remember me"/>
            </div>
        </div>
        <div class="form-unit">
            <input type="submit" value="Login" class="btn btn-success form-control"/>
        </div>
    </form>
</div>