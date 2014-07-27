<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div>
    <div class="bordered_element">
        <p><i>${message.date}</i></p>

        <p><b>${message.sender}</b></p>

        <p><b>${message.subject}</b></p>

        <p>${message.text}</p>
    </div>

    <div class="bordered_element">
        Answer:<br/>
        <s:url value="/mail/send" var="sendMessageUrl"/>
        <form:form action="${sendMessageUrl}" method="put" commandName="newMessage">
            Receiver name:<br/>
            <input type="text" name="receiver_name"/><br/>
            Subject:<br/>
            <form:input type="text" path="subject"/><br/>
            Text:<br/>
            <form:textarea path="text"/>
            <form:button>
                Answer
            </form:button>
        </form:form>
    </div>
</div>