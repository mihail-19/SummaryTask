<%@ include file="/WEB-INF/jsp/fragments/tags.jspf" %>
<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="resources.resource">
<c:choose>
	<c:when test="${isUnique }"></c:when>
	<c:otherwise><fmt:message key="registerPage.busyLogin"/></c:otherwise>
</c:choose>
</fmt:bundle>