<%@ include file="/WEB-INF/jsp/fragments/tags.jspf"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Role"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Sort"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.entity.Doctor"%>
<!DOCTYPE html>
<html>

<%@ include file="../fragments/head.jspf"%>

<body>
	<div class="content">

	<%@ include file="../fragments/header.jspf"%>


	<div class="patient-list-container">
		<div class="patient-list">
			<fmt:bundle basename="resources.resource">
				<div class="sort">

					<form action="control">
						<input type="hidden" name="command" value="showAllPatients">
						<fmt:message key="sort.sort" />
						<select name="sort">
							<c:out value="${role }" />
								<option selected value="${sort}"><fmt:message
										key="userData.${sort}" /></option>
								<option value="${Sort.lastName}"><fmt:message
										key="userData.lastName" /></option>
								<option value="${Sort.dateOfBirth}"><fmt:message
										key="userData.dateOfBirth" /></option>
						</select> <fmt:message key="sort.limit" />
							<select name="limit">
								<option selected value="${limit}"><c:out
										value="${limit}" /></option>
								<option value="3">3</option>
								<option value="5">5</option>
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="30">30</option>
						</select>
							<fmt:message key="sort.showDischarged" />
							<input type="checkbox" name="showDischarged" value="true"
							<c:if test="${showDischarged}">checked</c:if>>
							<input type="submit" value="<fmt:message key="sort.submit"/>">
					</form>
				</div>




				<table class="users-list-table" id="table" border="2">
					<tr class="users-list-table">
						<th class="users-list-table"><fmt:message key="userData.firstName"/></th>
						<th class="users-list-table"><fmt:message key="userData.lastName"/></th>
						<th class="users-list-table"><fmt:message key="userData.dateOfBirth"/></th>
					</tr>
					<c:forEach var="pat" items="${patientsList }">
						<tr class="users-list-table" id="${pat.id }"
							onclick="patientMenu('${pat.id}')">
							<td class="users-list-table"><c:out value="${pat.firstName }" /></td>
							<td class="users-list-table"><c:out value="${pat.lastName }" /></td>
							<td class="users-list-table"><c:out value="${pat.dateOfBirth }" /></td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${offset - limit >= 0}">
					<a
						href="control?command=showAllPatients&limit=${limit}&offset=${offset - limit}&sort=${sort}">prev
						${limit }</a>
				</c:if>
				<c:if test="${patientsList.size()>=limit}">
					<a
						href="control?command=showAllPatients&limit=${limit}&offset=${offset + limit}&sort=${sort}">next
						${limit }</a>
				</c:if>
			</fmt:bundle>
		</div>
		<div id="doctor-menu" class="doctor-menu"></div>





	</div>
	</div>
	<%@ include file="../fragments/footer.jspf"%>
</body>
<script>
	var previousId;
	patientMenu(document.getElementById("table").rows[1].id);
	
	function patientMenu(patientId) {
		if (previousId != null && previousId != patientId) {
			document.getElementById(previousId).style.background = 'transparent';
		}
		previousId = patientId;
		
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=patientMenuDoctor&patientId='
				+ patientId, false);
		xhr.send();
		document.getElementById(patientId).style.background = '#A5DDE8';
		document.getElementById("doctor-menu").innerHTML = xhr.responseText;
	}
	function removePrescription(prescId, patientId) {
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=removePrescription&patientId='
				+ patientId + '&prescId=' + prescId, false);
		xhr.send();
		document.getElementById("doctor-menu").innerHTML = xhr.responseText;
	}
	function executePrescription(prescId, patientId) {
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=executePrescription&patientId='
				+ patientId + '&prescId=' + prescId, false);
		xhr.send();
		document.getElementById("doctor-menu").innerHTML = xhr.responseText;
	}
	function addPrescription() {
		var xhr = new XMLHttpRequest();
		var patientId = document.getElementById("formPatientId").value;
		var description = document.getElementById("form-description").value;
		var params = "command=addPrescription" + "&patientId="
				+ patientId + "&prescType="
				+ document.getElementById("formPrescType").value
				+ "&description=" + encodeURIComponent(description);
		if (description.trim().length > 0) {
			xhr.open("POST", "control", false);
			xhr.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded");
			xhr.send(params);
			document.getElementById("doctor-menu").innerHTML = xhr.responseText;
		}
		prevent();
	}
	function addDiagnose() {
		var xhr = new XMLHttpRequest();
		var patientId = document.getElementById("formPatientIdDiagnose").value;
		var diagnose = document.getElementById("form-diagnose").value;
		var params = "command=addDiagnose" + "&patientId="
				+ patientId + "&diagnose="
				+ encodeURIComponent(diagnose);
		if (diagnose.trim().length > 0) {
			xhr.open("POST", "control", false);
			xhr.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded");
			xhr.send(params);
			document.getElementById("doctor-menu").innerHTML = xhr.responseText;
		}
		prevent();
	}
	function discharge(patientId) {
		if (confirm('discharge?')) {
			var xhr = new XMLHttpRequest();
			xhr.open('GET', 'control?command=discharge&patientId='
					+ patientId, false);
			xhr.send();
			document.getElementById("doctor-menu").innerHTML = xhr.responseText;
		}
	}
	function showAddPrescription() {
		hideAddDiagnose();
		document.getElementById("presc-form").style.display = "block";
		prevent();
	}
	function showAddDiagnose() {
		hideAddPrescription();
		document.getElementById("diagnose-form").style.display = "block";
		prevent();
	}

	function hideAddPrescription() {
		document.getElementById("presc-form").style.display = "none";
	}
	function hideAddDiagnose() {
		document.getElementById("diagnose-form").style.display = "none";
	}

	function prevent() {

		let formDescription = document.getElementById("presc-form");

		if (formDescription != null) {
			formDescription.addEventListener("submit", function(event) {
				event.preventDefault();

				addPrescription();
			});
		}
		let formDiagnose = document.getElementById("diagnose-form");

		if (formDiagnose != null) {
			formDiagnose.addEventListener("submit", function(event) {
				event.preventDefault();

				addDiagnose();
			});
		}
	}
</script>
</html>