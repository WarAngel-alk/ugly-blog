<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div>
    <div class="bordered_element">
        Answer:<br/>
        <s:url value="/mail/send" var="sendMessageUrl"/>
        <form:form action="${sendMessageUrl}" method="put" commandName="newMessage">
            <div><form:errors cssClass="alert-danger" path="subject"/></div>
            <div><form:errors cssClass="alert-danger" path="text"/></div>
            Receiver name:<br/>
            <input type="text" name="receiverName"/><br/>
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