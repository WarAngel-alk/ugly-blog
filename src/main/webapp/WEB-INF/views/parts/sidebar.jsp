<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div id="sidebar" class="col-md-3">

    <%-- Posts list --%>
    <div class="sidebar-posts-list">

    </div>
    <%-- End of Posts list --%>

    <%-- Tag cloud --%>
    <div class="sidebar-tag-cloud">
        <div id="tags-canvas-container">
            <canvas id="tags-canvas">
                <p>Anything in here will be replaced on browsers that support the canvas element</p>
            </canvas>
        </div>
        <div id="tags">
            <ul>
                <li><a href="http://www.google.com">Google</a></li>
                <li><a href="http://www.google.com">Link</a></li>
                <li><a href="http://www.google.com">Cloud</a></li>
            </ul>
        </div>
    </div>
    <%-- End of Tag cloud --%>

    <%-- Archive --%>
    <div class="sidebar-archive">

    </div>
    <%-- End of Archive --%>

    <s:url var="tagCanvasUrl" value="/resources/js/tagcanvas.min.js"/>
    <script src="${tagCanvasUrl}" type="text/javascript"></script>
    <script type="text/javascript">
        window.onload = function () {
            try {
                TagCanvas.Start('tags-canvas', 'tags', {
                    textColour: '#111111',
                    outlineColour: '#ff00ff',
                    reverse: true,
                    depth: 0.8,
                    maxSpeed: 0.125
                });
            } catch (e) {
                document.getElementById('tags-canvas-container').style.display = 'none';
            }
        };
    </script>

</div>