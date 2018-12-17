<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="ui" uri="http://www.jahia.org/tags/uiComponentsLib" %>
<%@ taglib prefix="functions" uri="http://www.jahia.org/tags/functions" %>
<%@ taglib prefix="query" uri="http://www.jahia.org/tags/queryLib" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<%@ taglib prefix="s" uri="http://www.jahia.org/tags/search" %>
<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>



<template:addResources type="css" resources="dx-statistics.css, bootstrap.min.css" />
<template:addResources type="javascript" resources="boostrap.min.js" />

<br />

<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <div class="hero-widget well well-sm">
                <div class="icon">
                    <i class="glyphicon glyphicon-list-alt"></i>
                </div>
                <div class="text">
                    <var>${pageViewCount}</var>
                    <label class="text-muted">page views</label>
                    <br />
                    <strong>Instant traffic:</strong> ${pagePerMinute}/minute
                </div>
                <div class="options">
                    <a href="javascript:;" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-list-alt"></i> Page view statistics</a>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="hero-widget well well-sm">
                <div class="icon">
                    <i class="glyphicon glyphicon-cog"></i>
                </div>
                <div class="text">
                    <var>${apiCallCount}</var>
                    <label class="text-muted">API calls</label>
                    <br />
                    <strong>Instant traffic:</strong> ${apiPerMinute}/minute
                </div>
                <div class="options">
                    <a href="javascript:;" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-cog"></i> API call statistics</a>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="hero-widget well well-sm">
                <div class="icon">
                    <i class="glyphicon glyphicon-file"></i>
                </div>
                <div class="text">
                    <var>${staticFileCount}</var>
                    <label class="text-muted">File access</label>
                    <br />
                    <strong>Instant traffic:</strong> ${filePerMinute}/minute
                </div>
                <div class="options">
                    <a href="javascript:;" class="btn btn-default btn-lg"><i class="glyphicon glyphicon-file"></i> File access statistics</a>
                </div>
            </div>
        </div>
    </div>
</div>