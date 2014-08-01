function deletePost(postId) {
    if (confirm("Do you want to delete this post?")) {
        $.ajax({
            type: "POST",
            url: deployPath + "post/" + postId,
            data: {_method: 'DELETE'},
            success: function (response) {
                window.location = deployPath + "home";
            }
        });
    }
}