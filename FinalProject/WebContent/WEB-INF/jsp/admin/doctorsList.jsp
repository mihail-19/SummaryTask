
<%@ include file="/WEB-INF/jsp/fragments/tags.jspf"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Role"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Sort"%>

<%-- Page for choosing doctor for current patient --%>
<fmt:setLocale value="${locale }"/>
<div class="user-menu-container">

	<fmt:bundle basename="resources.resource">
		<form action="control" id="doctor-sort-form">
			<input type="hidden" name="command" value="selectDoctor"> 
			<input	type="hidden" name="offset" value="0">
			<input type="hidden" name="patientId" value="${patientId }"> <fmt:message key="sort.sort" />
			<select name="sort" id="doctor-sort-param">
				<option selected value="${sort}"><fmt:message key="userData.${sort }"/></option>
				<option value="${Sort.lastName}"><fmt:message key="userData.lastName" /></option>
				<option value="${Sort.dateOfBirth}"><fmt:message key="userData.dateOfBirth" /></option>
				<option value="${Sort.category}"><fmt:message key="userData.category" /></option>
				<option value="${Sort.numberOfPatients}"><fmt:message key="userData.numberOfPatients" /></option>
			</select><fmt:message key="sort.limit" /> <select name="limit" id="doctor-sort-limit">
				<option selected value="${limit}"><c:out value="${limit}" /></option>
				<option value="3">3</option>
				<option value="5">5</option>
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="30">30</option>
			</select> <input type="submit" value="<fmt:message key="sort.submit" />">
		</form>

		<c:set var="menuText" value="User menu" />

		<h2>
			<fmt:message key="doctorsList.title"/> 
		</h2>

		<table class="user-doctors-table" border="2">
			<tr class="user-doctors-table">
				<td class="user-doctors-table"><b><fmt:message key="userData.login" /></b></td>
				<td class="user-doctors-table"><b><fmt:message key="userData.firstName" /></b></td>
				<td class="user-doctors-table"><b><fmt:message key="userData.lastName" /></b></td>
				<td class="user-doctors-table"><b><fmt:message key="userData.role" /></b></td>
				<td class="user-doctors-table"><b><fmt:message key="userData.category" /></b></td>
				<td class="user-doctors-table"><b><fmt:message key="userData.numberOfPatients" /></b></td>
			</tr>
			<c:forEach var="doc" items="${doctorsList }">
				<tr class="user-doctors-table">
					<td class="user-doctors-table"><c:out value="${doc.login }" /></td>
					<td class="user-doctors-table"><c:out value="${doc.firstName }" /></td>
					<td class="user-doctors-table"><c:out value="${doc.lastName }" /></td>
					<td class="user-doctors-table"><c:out value="${doc.role }" /></td>
					<td class="user-doctors-table"><fmt:message key="category.${doc.category}" /></td>
					<td class="user-doctors-table"><c:out value="${doc.numberOfPatients}" /></td>
					<c:if test="${!disabledDoctors.contains(doc) }" >
						<td class="user-doctors-table__del" onclick="addDoctor('${patientId}', '${doc.id}')">
								<fmt:message
										key="doctorsList.appointDoctor" />
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${offset - limit >= 0}">
			<span onclick="docListPrev( '${patientId}')" class="button"><fmt:message key="usersList.prev" /></span>
		</c:if>
		<c:if test="${doctorsList.size()>=limit}">
			<span onclick="docListNext('${patientId}')" class="button"><fmt:message key="usersList.next" /></span>
		</c:if>
	</fmt:bundle>
</div>