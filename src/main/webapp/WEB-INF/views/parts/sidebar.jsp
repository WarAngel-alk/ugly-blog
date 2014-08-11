<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="sidebar" class="col-md-3">

    <%-- Posts list --%>
    <div class="sidebar-posts">
        <div class="sidebar-item-label">
            TOP-10 posts
        </div>
        <ul id="sidebar-posts-list">
            <c:forEach var="post" items="${postList}">
                <li class="sidebar-posts-item">
                    <s:url var="postUrl" value="/post/${post.id}"/>
                    <a href="${postUrl}">
                            ${post.title}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <%-- End of Posts list --%>

    <%-- Tag cloud --%>
    <div class="sidebar-tag-cloud">
        <div class="sidebar-item-label">
            Posts by tag
        </div>
        <div id="tags-cloud-container">
            <canvas id="tags-cloud">
                <p>Anything in here will be replaced on browsers that support the canvas element</p>
            </canvas>
        </div>
        <div id="tags">
            <ul>

                <s:eval var="maxFrequency" expression="T(com.my.tiles.SidebarPreparer).getMaxFrequency(tagList)"/>
                <c:forEach var="tag" items="${tagList}">
                    <s:url var="tagUrl" value="/posts/tag/${tag.name}"/>
                    <s:eval var="tagWeight"
                            expression="T(com.my.tiles.SidebarPreparer).getRelativeTagFrequency(tag, maxFrequency)"/>
                    <li>
                        <a href="${tagUrl}" data-weight="${tagWeight * 30}">${tag.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <%-- End of Tag cloud --%>

    <%-- Archive --%>
    <div class="sidebar-archive">
        <div class="sidebar-item-label">
            Archive
        </div>
        <div id="archive-tree">
            <ul class="archive-tree-list">
                <s:url var="monthIconUrl" value="/resources/images/archive/month_icon.png"/>
                <s:url var="postIconUrl" value="/resources/images/archive/post_icon.png"/>

                <s:eval var="yearSet" expression="postMap.keySet()"/>
                <c:forEach var="yearNumber" items="${yearSet}">

                    <li id="${yearNumber}" class="archive-year-item" data-jstree='{"opened":true}'>
                            ${yearNumber}
                        <ul class="archive-month-list">

                            <s:eval var="monthSet" expression="postMap.get(yearNumber).keySet()"/>
                            <c:forEach var="monthNumber" items="${monthSet}">
                                <s:eval var="monthName"
                                        expression="T(java.time.Month).of(monthNumber).getDisplayName(T(java.time.format.TextStyle).FULL, T(java.util.Locale).US)"/>
                                <li id="${monthNumber}" class="archive-month-item"
                                    data-jstree='{"icon": "${monthIconUrl}"}'>
                                        ${monthName}
                                    <ul class="archive-posts-list">

                                        <s:eval var="postList" expression="postMap.get(yearNumber).get(monthNumber)"/>
                                        <c:forEach var="post" items="${postList}">
                                            <li class="archive-posts-item" data-jstree='{"icon": "${postIconUrl}"}'>
                                                <s:url var="postUrl" value="/post/${post.id}"/>
                                                <a href="${postUrl}">
                                                        ${post.title}
                                                </a>
                                            </li>
                                        </c:forEach>

                                    </ul>
                                </li>
                            </c:forEach>

                        </ul>
                    </li>

                </c:forEach>

            </ul>
        </div>
    </div>
    <%-- End of Archive --%>

    <s:url var="tagCanvasUrl" value="/resources/js/tagcanvas.min.js"/>
    <script src="${tagCanvasUrl}" type="text/javascript"></script>
    <script type="text/javascript">
        window.onload = function () {
            try {
                TagCanvas.Start('tags-cloud', 'tags', {
                    weight: true,
                    weightFrom: 'data-weight',
                    textColour: '#111111',
                    outlineColour: '#115533',
                    reverse: true,
                    depth: 0.4,
                    maxSpeed: 0.1
                });
            } catch (e) {
                document.getElementById('tags-cloud-container').style.display = 'none';
            }
        };
    </script>

    <s:url var="jsTreeCss" value="/resources/css/jstree.min.css"/>
    <link href="${jsTreeCss}" rel="stylesheet" type="text/css">

    <s:url var="jsTree" value="/resources/js/jstree.min.js"/>
    <script src="${jsTree}" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            $.jstree.defaults.sort = function (node1, node2) {
                return (node1 <= node2 ? 1 : -1);
            };
            $('#archive-tree').jstree({
                "plugins": [ "sort" ]
            }).on("select_node.jstree", function (e, data) {
                        var href = data.node.a_attr.href;
                        if (href != '#') {
                            document.location.href = href;
                        }
                    })
        });
    </script>

</div>