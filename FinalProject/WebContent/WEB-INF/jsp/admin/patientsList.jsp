
<%@ include file="/WEB-INF/jsp/fragments/tags.jspf"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Role"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Sort"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.entity.Doctor"%>
<!DOCTYPE html>
<html>

<%@ include file="../fragments/head.jspf"%>

<body>
	<div class ="content">
	<%@ include file="../fragments/header.jspf"%>

	<%--Filters and options --%>
	<div class="users-list">


		<div class="users-list__data">
			
				<form action="control">
					<input type="hidden" name="command" value="showAllPatients">
					<div class="sorting-form">
<fmt:bundle basename="resources.resource">
	<fmt:message key="sort.sort" />
	<select name="sort">
		<option selected value="${sort}"><fmt:message
				key="userData.${sort}" /></option>
		<option value="${Sort.lastName}"><fmt:message
				key="userData.lastName" /></option>
		<option value="${Sort.dateOfBirth}"><fmt:message
				key="userData.dateOfBirth" /></option>
	</select>
	<fmt:message key="sort.limit" />
	<select name="limit">
		<option selected value="${limit}"><c:out value="${limit}" /></option>
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
</fmt:bundle>
</div>
				</form>
				<fmt:bundle
				basename="resources.resource">
				<table class="users-list-table" id="users-table">
					<tr class="users-list-table">
						<th class="users-list-table"><fmt:message key="userData.firstName" /></th>
						<th class="users-list-table"><fmt:message key="userData.lastName" /></th>
						<th class="users-list-table"><fmt:message key="userData.dateOfBirth" /></th>
						<th class="users-list-table"><fmt:message key="userData.isActive" /></th>
					</tr>
					<c:forEach var="patient" items="${patientsList }">
						<tr class="users-list-table" onclick="showUserMenu('${patient.id}')" id="${patient.id }">
							<td class="users-list-table"><c:out value="${patient.firstName }" /></td>
							<td class="users-list-table"><c:out value="${patient.lastName }" /></td>
							<td class="users-list-table"><c:out value="${patient.dateOfBirth }" /></td>
							<td class="users-list-table"><c:out value="${patient.isActive }" /></td>
						</tr>

					</c:forEach>
				</table>


				<div class="page-navigation">
					<div>
						<c:if test="${offset - limit >= 0}">
							<a
								href="control?command=showAllPatients&limit=${limit}&offset=${offset - limit}&role=${role}&sort=${sort}&showDischarged=${showDischarged }">
								<fmt:message key="usersList.prev" /> ${limit }</a>
						</c:if>
					</div>
					<div>
						<c:if test="${patientsList.size()>=limit}">
							<a
								href="control?command=showAllPatients&limit=${limit}&offset=${offset + limit}&role=${role}&sort=${sort}&showDischarged=${showDischarged }">
								<fmt:message key="usersList.next" /> ${limit }</a>
						</c:if>
					</div>
				</div>
			</fmt:bundle>
		</div>

		<div id="user-menu" class="user-menu">
		
		</div>
	</div>
	</div>
	<%@ include file="../fragments/footer.jspf"%>
</body>
<script>
	var previousId;
	var docListOffset=0;
	showUserMenu(document.getElementById("users-table").rows[1].id);
	function showUserMenu(patientId) {
		if(previousId != null && previousId != patientId){
			document.getElementById(previousId).style.background='transparent';
		}
		previousId=patientId;
		
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=patientMenuAdmin&patientId='
				+ patientId, false);
		xhr.send();
		document.getElementById(patientId).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;
	}
	function removeUserDoctor(doctorId, patientId){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=removeUserDoctor&patientId='+patientId
				+'&doctorId=' + doctorId, false);
		xhr.send();
		//document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;

	}
	function selectDoctor(patientId){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=selectDoctor&patientId='+ patientId, false);
		xhr.send();
		//document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;
		prevent(patientId);
	}
	function selectDoctorSorted(patientId){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=selectDoctor&patientId='+patientId
				+'&sort=' + document.	getElementById("doctor-sort-param").value 
				+'&limit=' + document.getElementById("doctor-sort-limit").value	
				+'&offset=' + docListOffset, false);
		xhr.send();
		//document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;
		prevent(patientId);
	}
function prevent(patientId){
		
		let form = document.getElementById( "doctor-sort-form" );
		
		if(form != null){
			
			form.addEventListener( "submit", function ( event ) {
			    event.preventDefault();

			    selectDoctorSorted(patientId);
		  } );
		}
	}
	function addDoctor(patientId, doctorId){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=addUserDoctor&patientId='+patientId
				+'&doctorId=' + doctorId, false);
		xhr.send();
		document.getElementById("user-menu").innerHTML = xhr.responseText;
	}
	//Paging on doctor's list
	function docListNext( patientId){
		
		docListOffset = docListOffset + parseInt(document.getElementById("doctor-sort-limit").value, 10);
		selectDoctorSorted(patientId)
	}
	function docListPrev(patientId){
			docListOffset = docListOffset - parseInt(document.getElementById("doctor-sort-limit").value, 10);
		selectDoctorSorted(patientId)
	}
	
</script>
</html>