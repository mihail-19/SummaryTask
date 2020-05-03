
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

	<%--Filters and options --%>
	<div class="users-list">


		<div class="users-list__data">
			
				<form action="control">
					<input type="hidden" name="command" value="showAllUsers">
					<%@ include file="../fragments/sorting_form.jspf"%>
				</form>
				<fmt:bundle
				basename="resources.resource">
				<table class="users-list-table" border="2" id="users-table">
					<tr class="users-list-table">
						<th class="users-list-table"><fmt:message key="userData.login" /></th>
						<th class="users-list-table"><fmt:message key="userData.firstName" /></th>
						<th class="users-list-table"><fmt:message key="userData.lastName" /></th>
						<th class="users-list-table"><fmt:message key="userData.roleID" /></th>
						<th class="users-list-table"><fmt:message key="userData.dateOfBirth" /></th>
						<th class="users-list-table"><fmt:message key="userData.category" /></th>
						<th class="users-list-table"><fmt:message key="userData.numberOfPatients" /></th>
					</tr>
					<c:forEach var="user" items="${usersList }">
						<tr class="users-list-table" onclick="showUserMenu('${user.login}')" id="${user.login }">
							<td class="users-list-table"><c:out value="${user.login }" /></td>
							<td class="users-list-table"><c:out value="${user.firstName }" /></td>
							<td class="users-list-table"><c:out value="${user.lastName }" /></td>
							<td class="users-list-table"><fmt:message key="role.${user.role}" /></td>
							<td class="users-list-table"><c:out value="${user.dateOfBirth }" /></td>
							<c:choose>
								<c:when test="${user.role eq Role.DOCTOR }">
									<td class="users-list-table"><fmt:message key="category.${doctorParamsMap.get(user.id).category}"/></td>
									<td class="users-list-table"><c:out
											value="${doctorParamsMap.get(user.id).numberOfPatients }" />
									</td>
								</c:when>
								<c:otherwise>
									<td class="users-list-table">-</td>
									<td class="users-list-table">-</td>
								</c:otherwise>
								
							</c:choose>
						</tr>

					</c:forEach>
				</table>


			<div class="page-navigation">
				<div>
				<c:if test="${offset - limit >= 0}">
						<a
							href="control?command=showAllUsers&limit=${limit}&offset=${offset - limit}&role=${role}&sort=${sort}&showDischarged=${showDischarged }">
							<fmt:message key="usersList.prev" /> ${limit }
						</a>
					</c:if>
					
				</div>
				<div>
					<c:if test="${usersList.size()>=limit}">
						<a
							href="control?command=showAllUsers&limit=${limit}&offset=${offset + limit}&role=${role}&sort=${sort}&showDischarged=${showDischarged }">
							<fmt:message key="usersList.next" /> ${limit }
						</a>
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
	var previousUserLogin;
	var docListOffset=0;
	showUserMenu(document.getElementById("users-table").rows[1].cells[0].innerHTML);
	function showUserMenu(userLogin) {
			if(previousUserLogin != null && previousUserLogin != userLogin){
				document.getElementById(previousUserLogin).style.background='transparent';
			}

		previousUserLogin=userLogin;
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=userMenu&userLogin='
				+ encodeURIComponent(userLogin), false);
		xhr.send();
		document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;

	}
	function removeUserDoctor(doctorLogin, userLogin, userRole){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=removeUserDoctor&userLogin='+encodeURIComponent(userLogin)
				+'&doctorLogin=' + encodeURIComponent(doctorLogin), false);
		xhr.send();
		//document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;

	}
	function selectDoctor(userLogin, userRole){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=selectDoctor&userLogin='+encodeURIComponent(userLogin), false);
		xhr.send();
		//document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;
		prevent(userLogin, userRole);
	}
	function selectDoctorSorted(userLogin, userRole){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=selectDoctor&userLogin='+encodeURIComponent(userLogin)
				+'&sort=' + document.getElementById("doctor-sort-param").value 
				+'&limit=' + document.getElementById("doctor-sort-limit").value	
				+'&offset=' + docListOffset, false);
		xhr.send();
		//document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;
		prevent(userLogin, userRole);
	}
function prevent(userLogin, userRole){
		
		let form = document.getElementById( "doctor-sort-form" );
		
		if(form != null){
			
			form.addEventListener( "submit", function ( event ) {
			    event.preventDefault();

			    selectDoctorSorted(userLogin, userRole);
		  } );
		}
	}
	function addDoctor(userLogin, doctorLogin){
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'control?command=addUserDoctor&userLogin='+encodeURIComponent(userLogin)
				+'&doctorLogin=' + encodeURIComponent(doctorLogin), false);
		xhr.send();
		//document.getElementById(userLogin).style.background='#A5DDE8';
		document.getElementById("user-menu").innerHTML = xhr.responseText;
	}
	//Paging on doctor's list
	function docListNext( userLogin, userRole){
		
		docListOffset = docListOffset + parseInt(document.getElementById("doctor-sort-limit").value, 10);
		selectDoctorSorted(userLogin, userRole)
	}
	function docListPrev( userLogin, userRole){
			docListOffset = docListOffset - parseInt(document.getElementById("doctor-sort-limit").value, 10);
		selectDoctorSorted(userLogin, userRole)
	}
	
</script>
</html>