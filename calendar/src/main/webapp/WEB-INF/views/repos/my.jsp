<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="My Repositories" scope="request"/>
<jsp:include page="../includes/header.jsp"/>

<p>This shows all events for all users. Once security is applied it will only be viewable to administrators.</p>
<c:url var="createUrl" value="/repos/form"/>
<div id="create" class="pull-right"><a href="${createUrl}">Create Repo</a></div>
<table class="table table-bordered table-striped table-condensed">
    <thead>
        <tr>
            <th>Name</th>
            <th>Clone URL</th>
            <th>&nbsp;</th>
        </tr>
    </thead>
    <tbody>
        <c:if test="${empty repos}">
            <tr>
                <td colspan="2" class="msg">No repos.</td>
            </tr>
        </c:if>
         <c:forEach items="${repos}" var="repo">
            <tr>
                <c:url var="repoUrl" value="${repo.name}"/>
                <td><a href="${repoUrl}"><c:out value="${repo.name}" /></a></td>
                <td>${repo.cloneUrl}</td>
                <c:url var="releaseUrl" value="/repos/release/${repo.name}" />
                <td><a href="${releaseUrl}">Release</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="../includes/footer.jsp"/>
