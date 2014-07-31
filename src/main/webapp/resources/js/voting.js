function postVote(mark, postId) {
    $.ajax({
        type: "POST",
        url: deployPath + "post/" + postId + "/vote",
        data: {mark: mark, _method: "PUT"},
        success: function (response) {
            var responseElements = response.split(',');

            var positiveMarks = responseElements[0];
            var negativeMarks = responseElements[1];

            document.getElementById("post_" + postId + "_positive").innerText = positiveMarks;
            document.getElementById("post_" + postId + "_negative").innerText = negativeMarks;
        }
    });
}

function commentVote(mark, postId, commentId) {
    $.ajax({
        type: "POST",
        url: deployPath + "post/" + postId + "/comment/" + commentId + "/vote",
        data: {mark: mark, _method: "PUT"},
        success: function (response) {
            var responseElements = response.split(',');

            var positiveMarks = responseElements[0];
            var negativeMarks = responseElements[1];

            document.getElementById("comment_" + commentId + "_positive").innerText = positiveMarks;
            document.getElementById("comment_" + commentId + "_negative").innerText = negativeMarks;
        }
    });
}