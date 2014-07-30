<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div>
    <div class="bordered_element">
        <p><i>${message.date}</i></p>

        <p><b>${message.sender.name}</b></p>

        <p><b>${message.subject}</b></p>

        <p>${message.text}</p>
    </div>

    <%@ include file="newMessageForm.jsp" %>

</div>