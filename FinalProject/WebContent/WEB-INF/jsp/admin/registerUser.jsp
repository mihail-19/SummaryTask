 <%@ include file="../fragments/tags.jspf" %>
 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
  <%@page import="ua.nure.teslenko.SummaryTask4.db.entity.Category" %>
<!DOCTYPE html>
<html>

<%@ include file="../fragments/head.jspf"%>


<body>
<div class="content">
<%@ include file="../fragments/header.jspf"%>

	<div class="register-page__container">
	
		<fmt:bundle basename="resources.resource">
		
		<div class="register-page__title">
			<fmt:message key="registerPage.title"/>
		</div>
		
		<form name="registerForm" action="control" method="post" accept-charset="UTF-8">
		<div class="register-page__form">
			<%--%Further sending of the parameters --%>
			<input type="hidden" name="command" value="registerUser"> 
			
			<div class="register-page__field">
				<div class="register-page__field title">
					<fmt:message key="userData.login"/>
				</div>
				<div id="errorMsgLogin" class="register-page__error-msg">
					A-Z, А-Я, 0-9, -, _
				</div>
				<div class="register-page__input-text">
					<input type="text"  name="login" onkeyup="trySubmit()">
					<span id="login-status"></span>
				</div>
			</div>
			
			<div class="register-page__field">
				<div class="register-page__field title">
					<fmt:message key="userData.role"/>
				</div>
				
				<div id="errorMsgType" class="register-page__error-msg">
						<fmt:message key="registerPage.chooseRole"/>
				</div>
				<div class="register-page__input-text">
					<select name = "userType" onchange="checkType()" required>
						<option disabled selected><fmt:message key="registerPage.chooseRole"/></option>
						<option value="${Role.DOCTOR }">
							<fmt:message key="role.DOCTOR"/>
						</option>
						<option value="${Role.NURSE }">
							<fmt:message key="role.NURSE"/>
						</option>
					</select>
				</div>
			</div>
			
			<div id="category" class="register-page__field">
				<div class="register-page__field title">
					<fmt:message key="userData.category"/>
				</div>
				<div id="errorMsgCategory" class="register-page__error-msg">
					Choose category
				</div>
				<div class="register-page__input-text">
					<select name="category" disabled onchange="trySubmit()">
						<option disabled selected value="default"><fmt:message key="registerPage.chooseCategory"/></option>
						<c:forEach var="c" items="${Category.values()}">
							<option value="${c}"><fmt:message key="category.${c}"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="register-page__field">
				<div class="register-page__field title">
					<fmt:message key="userData.firstName"/>
				</div>
				<div id="errorMsgFirstName" class="register-page__error-msg">
					<fmt:message key="registerPage.errorFirstName"/>
				</div>
				<div class="register-page__input-text">
					<input type="text" name="firstName" onkeyup="trySubmit()">
				</div>
			</div>
			
			<div class="register-page__field">
				<div class="register-page__field title">
					<fmt:message key="userData.lastName"/>
				</div>
				<div id="errorMsgLastName" class="register-page__error-msg">
					<fmt:message key="registerPage.errorLastName"/>
				</div>
				<div class="register-page__input-text">
					<input type="text" name="lastName" onkeyup="trySubmit()">
				</div>
			</div>
			
			<div class="register-page__field">
				<div class="register-page__field title">
					<fmt:message key="userData.dateOfBirth"/>
				</div>
				<div id="errorMsgDate" class="register-page__error-msg">
					<fmt:message key="registerPage.errorDate"/>
				</div>
				<div class="register-page__input-text">
					<input	type="text" name="dateOfBirth" onkeyup="trySubmit()" placeholder="1999-01-25" >
				</div>
			</div>
			
			<div class="register-page__field">
				<div class="register-page__field title">
					<fmt:message key="registerPage.password"/>
				</div>
				<div id="errorMsgPassword" class="register-page__error-msg">
					<fmt:message key="registerPage.errorPassword"/>
				</div>
				<div class="register-page__input-text">
					<input type="password" name="password" onkeyup="trySubmit()" ><br>
				</div>
			</div>

			<input type="submit" id="submitButton" disabled value="<fmt:message key="registerPage.submit"/>">
		</div>
	</form>
	<c:choose>
		<c:when test="${errorType eq  'category'}">
			<p><font color="red"><i><fmt:message key="registerPage.incorrectCategory"/></i></font></p>
		</c:when>
		<c:when test="${errorType eq  'date'}">
			<p><font color="red"><i><fmt:message key="registerPage.incorrectDate"/></i></font></p>
		</c:when>
		<c:when test="${errorType eq  'notUniqueLogin'}">
			<p><font color="red"><i><fmt:message key="registerPage.notUniqueLogin"/></i></font></p>
		</c:when>
	</c:choose>
	</fmt:bundle>
	
	</div>
		<script  type="text/javascript" charset="utf-8">
		<%@include file="/WEB-INF/script/register.js"%>
	
	</script>
	</div>
	<%@ include file="../fragments/footer.jspf"%>
</body>

</html>

