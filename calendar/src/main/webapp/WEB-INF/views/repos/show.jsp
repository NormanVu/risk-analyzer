<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" scope="request">
	<c:out value="Repo - ${repo.name}" />
</c:set>

<jsp:include page="../includes/header.jsp" />
<table class="table table-bordered table-striped table-condensed">
	<thead>
		<tr>
			<th>Property</th>
			<th>Value</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Name</td>
			<td>${repo.name}</td>
		</tr>
		<tr>
			<td>Description</td>
			<td>${repo.description}</td>
		</tr>
		<tr><td>gitUrl</td><td>${repo.gitUrl}</td></tr>
		<tr><td>owner.email</td><td>${repo.owner.email}</td></tr>
	</tbody>
</table>
        <input id="submit" class="btn" name="submit" type="submit" value="Release"/>
        <div>release status: ${releaseStatus}</div>


<jsp:include page="../includes/footer.jsp" />
