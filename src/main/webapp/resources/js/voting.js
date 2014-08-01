function postVote(mark, postId) {
    $.ajax({
        type: "POST",
        url: deployPath + "post/" + postId + "/vote",
        data: {mark: mark, _method: "PUT"},
        success: function (response) {
            var responseElements = response.split(',');

            var positiveMarks = responseElements[0];
            var negativeMarks = responseElements[1];
            var rating = responseElements[2];

            var activeVoteUpUrl = deployPath + 'resources/images/vote_up_active.png';
            var activeVoteDownUrl = deployPath + 'resources/images/vote_down_active.png';

            if (mark) {
                document.getElementById("post-" + postId + "-vote-up").setAttribute("src", activeVoteUpUrl);
            } else {
                document.getElementById("post-" + postId + "-vote-down").setAttribute("src", activeVoteDownUrl);
            }

            var postRatingElem = document.getElementById("post-" + postId + "-rating");
            postRatingElem.setAttribute("title", "Positive: " + positiveMarks + " Negative: " + negativeMarks);
            postRatingElem.innerText = rating;
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