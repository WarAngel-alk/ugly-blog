<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spitter</title>
</head>

<body>
<div id="container">
        <t:insertAttribute name="header" />
        <t:insertAttribute name="side" />
        <t:insertAttribute name="content" />
</div>
</body>
</html>
