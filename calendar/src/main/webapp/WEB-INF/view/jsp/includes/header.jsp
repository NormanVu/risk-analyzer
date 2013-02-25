<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>
<div id="nav-account" class="nav-collapse pull-right">
	<ul class="nav">
		<sec:authorize access="authenticated" var="authenticated" />
		<c:choose>
			<c:when test="${authenticated}">
				<li id="greeting">
					<div>
						Welcome
						<sec:authentication property="name" />
					</div>
				</li>
				<c:url var="logoutUrl" value="/logout" />
				<li><a id="navLogoutLink" href="${logoutUrl}">Logout</a></li>
			</c:when>
			<c:otherwise>
				<c:url var="loginUrl" value="/login/form" />
				<li><a id="navLoginLink" href="${loginUrl}">Login</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>