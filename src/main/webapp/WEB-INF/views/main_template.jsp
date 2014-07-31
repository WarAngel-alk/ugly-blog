<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ugly blog</title>

    <s:url value="/" var="deployPath" scope="request"/>
    <script language="javascript" type="text/javascript">
        var deployPath = ${deployPath};
    </script>

    <link rel="stylesheet" href="<s:url value="/resources/css/bootstrap.min.css"/>">

    <link rel="stylesheet" href="<s:url value="/resources/css/bootstrap-theme.min.css"/>">

    <link rel="stylesheet" href="<s:url value="/resources/css/style.css"/>">
</head>

<body>
<div id="container">
    <t:insertAttribute name="header"/>
    <t:insertAttribute name="mailboxHeader" ignore="true"/>
    <t:insertAttribute name="side" ignore="true"/>
    <t:insertAttribute name="content"/>
</div>

<script src="<s:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<s:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>
