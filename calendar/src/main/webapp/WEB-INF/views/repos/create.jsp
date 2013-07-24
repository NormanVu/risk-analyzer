<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Create Repo" scope="request"/>
<jsp:include page="../includes/header.jsp"/>
<form:form action="./new" method="post" modelAttribute="createRepoForm" cssClass="form-horizontal">
    <form:errors path="*" element="div" cssClass="alert alert-error"/>
    <fieldset>
        <legend>Repo Information</legend>
        <div class="control-group">
            <label class="control-label" for="repoName">Name</label>
            <div class="controls">
                <form:input class="input-xlarge" path="name" id="repoName"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="repoDescription">Description</label>
            <div class="controls">
                <form:input class="input-xlarge" path="description" id="repoDescription"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <input id="submit" type="submit" value="Create"/>
            </div>
        </div>
    </fieldset>
</form:form>
<jsp:include page="../includes/footer.jsp"/>
