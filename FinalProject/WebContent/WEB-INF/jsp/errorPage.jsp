<%@ page isErrorPage="true" %>
 <%@ include file="fragments/tags.jspf" %>

 <%@ page import="java.io.PrintWriter" %>
  <c:set var="title" value="Error" scope="page" />
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
				<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
				<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
				
				<c:if test="${not empty code}">
					<h3>Error code: ${code}</h3>
				</c:if>			
				
				<c:if test="${not empty message}">
					<h3>${message} message</h3>
				</c:if>
				
				<c:if test="${not empty exception}">
					<% exception.printStackTrace(new PrintWriter(out)); %>
				</c:if>
				
				<%-- if we get this page using forward --%>
				<c:if test="${not empty requestScope.errorMessage}">
					<fmt:bundle basename="resources.resource">
					<h3><fmt:message key="exception.${requestScope.errorMessage}"/> </h3>
					</fmt:bundle>
				</c:if>
</body>
</html>