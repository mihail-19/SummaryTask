
 <%@ include file="fragments/tags.jspf" %>
<!DOCTYPE html>
<html>
<%@ include file="fragments/head.jspf" %>

<body>
<div class="content">
<%@ include file="fragments/header.jspf" %>
	<fmt:bundle basename="resources.resource">
	<div class="register-page__container">
	<div class="register-page__form login-page__form">
	<form name="loginForm" action="control" method="post">
		<input type="hidden" name="command" value="authorize">
	
		<c:if test="${not empty incorrectAuthorization}">
			<div class="register-page__error-msg">
				<fmt:message key="loginPage.error"/>
			</div>
		</c:if>
		
		<div class="register-page__field">
			<div class="register-page__field title">
				<fmt:message key="userData.login" />
			</div>
			<div class="register-page__input-text">
				<input type="text" name="login" onkeyup="validate()">
			</div>
		</div>

		<div class="register-page__field">
			<div class="register-page__field title">
				<fmt:message key="userData.password" />
			</div>
			<div class="register-page__input-text">
				<input type="password" name="password" onkeyup="validate()">
			</div>
		</div>
	<input id="submitButton" type="submit" value="send">
	
	</form>
	
	
	</div>
	</div>
	</fmt:bundle>
	</div>
		<%@ include file="fragments/footer.jspf"%>
</body>
<script>
	document.getElementById("submitButton").disabled = true;
	function validate(){
		if(document.forms["loginForm"]["login"].value.length > 0 && document.forms["loginForm"]["password"].value.length > 0){
			document.getElementById("submitButton").disabled = false;
		} else{
			document.getElementById("submitButton").disabled = true;
		}
	}
</script>
</html>