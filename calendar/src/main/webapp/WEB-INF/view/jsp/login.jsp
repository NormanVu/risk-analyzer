<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="Please Login" scope="request" />
<html>
<body>
	<c:url value="/login" var="loginUrl" />
	<form action="${loginUrl}" method="post">
		<c:if test="${param.error != null}">
			<div class="alert alert-error">
				Failed to login.
				<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
              Reason: <c:out
						value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				</c:if>
			</div>
		</c:if>
		<c:if test="${param.logout != null}">
			<div class="alert alert-success">You have been logged out.</div>
		</c:if>
		<label for="username">Username</label> <input type="text"
			id="username" name="username" /> <label for="password">Password</label>
		<input type="password" id="password" name="password" />
		<div class="form-actions">
			<input id="submit" class="btn" name="submit" type="submit"
				value="Login" />
		</div>
	</form>
</body>
</html>