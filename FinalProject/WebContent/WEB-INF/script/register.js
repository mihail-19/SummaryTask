document.getElementById("category").style.display= "none";
document.getElementById("errorMsgLogin").style.display= "none";
document.getElementById("errorMsgType").style.display= "none";
document.getElementById("errorMsgCategory").style.display= "none";
document.getElementById("errorMsgFirstName").style.display= "none";
document.getElementById("errorMsgLastName").style.display= "none";
document.getElementById("errorMsgDate").style.display= "none";
document.getElementById("errorMsgPassword").style.display= "none";

//Save the i18n error message and replace it with "busy login" if login is no unique
var errorMsgLogin = document.getElementById("errorMsgLogin").innerHTML;

function trySubmit() {
	if (validateLogin() && validateType() && validateCategory() &&  validateFirstName()
			&& validateLastName() && validateDate() && validatePassword() ) {
		document.getElementById("submitButton").disabled = false;
	} else {
		document.getElementById("submitButton").disabled = true;
	}
}
function validateType() {
	var type = document.forms["registerForm"]["userType"].value;
	if (type == "DOCTOR" || type == "PATIENT" || type == "NURSE") {
		document.getElementById("errorMsgType").style.display= "none";
		return true;
	}
	document.getElementById("errorMsgType").style.display= "block";
	return false;
}

function validateCategory() {
	document.getElementById("errorMsgCategory").style.display= "none";
	if(document.forms["registerForm"]["category"].disabled){
		return true;
	}
	var category = document.forms["registerForm"]["category"].value;
	if (category != "default") {
		return true;
	}
	document.getElementById("errorMsgCategory").style.display= "block";
	return false;
}

function validateLogin() {
	document.getElementById("errorMsgLogin").innerHTML = errorMsgLogin;
	var loginRegex = /^[A-zА-яёЁіІїЇєЄ][A-zА-яёЁіІїЇєЄ0-9_\-]{3,15}$/;
	if (loginRegex.exec(document.forms["registerForm"]["login"].value)) {
		if(isUniqueLogin()){
			
			document.getElementById("errorMsgLogin").style.display= "none";
			return true;
		} else{
			document.getElementById("errorMsgLogin").style.display= "block";
			return false;
		}
	}
	document.getElementById("errorMsgLogin").style.display= "block";
	return false;
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
function validatePassword() {
	var pwdRegex = /^[A-zА-яёЁіІїЇєЄ0-9]{3,25}$/;
	if (pwdRegex.exec(document.forms["registerForm"]["password"].value)) {
		document.getElementById("errorMsgPassword").style.display= "none";
		return true;
	}
	document.getElementById("errorMsgPassword").style.display= "block";
	return false;
}


function checkType() {
	var type = document.forms["registerForm"]["userType"].value;
	if (type == "DOCTOR") {
		document.forms["registerForm"]["category"].disabled = false;
		document.getElementById("category").style.display= "block";
	} else {
		document.forms["registerForm"]["category"].disabled = true;
		document.getElementById("category").style.display= "none";
	}
	trySubmit();
}
function isUniqueLogin(){
	var newLogin = document.forms["registerForm"]["login"].value;
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'control?command=testNewLogin&login='+encodeURIComponent(newLogin), false);
	xhr.send();
	if(xhr.responseText.trim().length>0){
		document.getElementById("errorMsgLogin").innerHTML = xhr.responseText;
		return false;
	}
	return true;
}

