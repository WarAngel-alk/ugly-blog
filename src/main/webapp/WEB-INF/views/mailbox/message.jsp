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

    <div class="bordered_element">
        Answer:<br/>
        <s:url value="/mail/send" var="sendMessageUrl"/>
        <form:form action="${sendMessageUrl}" method="put" commandName="newMessage">
            <div><form:errors cssClass="alert-danger" path="receiver"/></div>
            <div><form:errors cssClass="alert-danger" path="subject"/></div>
            <div><form:errors cssClass="alert-danger" path="text"/></div>
            Receiver name:<br/>
            <form:input type="text" path="receiver.name"/><br/>
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