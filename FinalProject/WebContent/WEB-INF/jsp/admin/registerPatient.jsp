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
			<fmt:message key="registerPagePatient.title"/>
		</div>
		
		<form name="registerForm" action="control" method="post" accept-charset="UTF-8">
		<div class="register-page__form">
			<%--%Further sending of the parameters --%>
			<input type="hidden" name="command" value="registerPatient"> 
			
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
			
			<input type="submit" id="submitButton" disabled value="<fmt:message key="registerPage.submit"/>">
		</div>
	</form>
	
	</fmt:bundle>
	
	</div>
	</div>
	<%@ include file="../fragments/footer.jspf"%>
	
<script  type="text/javascript" charset="utf-8">

document.getElementById("errorMsgFirstName").style.display= "none";
document.getElementById("errorMsgLastName").style.display= "none";
document.getElementById("errorMsgDate").style.display= "none";


function trySubmit() {
	if (validateFirstName() 	&& validateLastName() && validateDate()) {
		document.getElementById("submitButton").disabled = false;
	} else {
		document.getElementById("submitButton").disabled = true;
	}
}



function validateFirstName() {
	var nameRegex = /^[A-zА-яёЁіІїЇєЄ]{1,20}$/;
	if (nameRegex.exec(document.forms["registerForm"]["firstName"].value)) {
		document.getElementById("errorMsgFirstName").style.display= "none";
		return true;
	}
	document.getElementById("errorMsgFirstName").style.display= "block";
	return false;
}
function validateLastName() {
	var nameRegex = /^[A-zА-яёЁіІїЇєЄ]{1,20}$/;
	if (nameRegex.exec(document.forms["registerForm"]["lastName"].value)) {
		document.getElementById("errorMsgLastName").style.display= "none";
		return true;
	}
	document.getElementById("errorMsgLastName").style.display= "block";
	return false;
}
function validateDate() {
	var dateRegex = /^\d{4}\-\d{2}\-\d{2}$/;
	if (dateRegex.exec(document.forms["registerForm"]["dateOfBirth"].value)) {
		document.getElementById("errorMsgDate").style.display= "none";
		return true;
	}
	document.getElementById("errorMsgDate").style.display= "block";
	return false;
}



	
	</script>
</body>

</html>

