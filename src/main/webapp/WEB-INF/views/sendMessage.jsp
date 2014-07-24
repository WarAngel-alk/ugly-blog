<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div>
    <s:url value="/mail/send" var="sendMessageUrl"/>
    <form action="${sendMessageUrl}" method="post">
        Receiver name:<br/>
        <input type="text" name="receiver_name"/><br/>
        Subject:<br/>
        <input type="text" name="subject"/><br/>
        Text:<br/>
        <textarea name="text"></textarea><br/>
        <input type="submit" value="Send"/>
    </form>
</div>