<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div class="col-md-6 col-md-offset-3 new-message-form">
    <s:url value="/mail/send" var="sendMessageUrl"/>
    <form:form action="${sendMessageUrl}" method="put" commandName="newMessage"
               cssClass="form-group">
        <div class="form-unit">
            <div>Receiver name:</div>
            <div><form:input type="text" path="receiver.name" cssClass="form-control"
                             placeholder="Receiver name"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="receiver"/></div>
        </div>

        <div class="form-unit">
            <div>Subject:</div>
            <div><form:input type="text" path="subject" cssClass="form-control"
                             placeholder="Subject"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="subject"/></div>
        </div>

        <div class="form-unit">
            <div>Text:</div>
            <div><form:textarea path="text" cssClass="form-control" placeholder="Text"/></div>
            <div><form:errors cssClass="alert-danger form-control" path="text"/></div>
        </div>
        <div class="form-unit">
            <input type="submit" class="btn btn-success form-control" value="Send"/>
        </div>

    </form:form>
</div>