function deleteMessage(messageId, byReceiver) {
    $.ajax({
        type: "POST",
        url: deployPath + "mail/message/" + messageId,
        data: {byReceiver: byReceiver, _method: 'DELETE'},
        success: function (response) {
            var message = document.getElementById("message_" + messageId);
            document.getElementById("message_" + messageId).parentNode.removeChild(message);
        }
    });
}
