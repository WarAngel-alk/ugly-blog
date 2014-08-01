function deleteComment(postId, commentId) {
    if (confirm("Do you want to delete this comment?")) {
        $.ajax({
            type: "POST",
            url: deployPath + "post/" + postId + "/comment/" + commentId,
            data: {_method: 'DELETE'},
            success: function (response) {
                window.location = deployPath + "post/" + postId;
            }
        });
    }
}