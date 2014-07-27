<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
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
        <form action="${sendMessageUrl}" method="put">
            Receiver name:<br/>
            <input type="text" name="receiver_name"/><br/>
            Subject:<br/>
            <input type="text" name="subject"/><br/>
            Text:<br/>
            <textarea name="text"></textarea><br/>
            <input type="submit" value="Send"/>
        </form>
    </div>
</div>