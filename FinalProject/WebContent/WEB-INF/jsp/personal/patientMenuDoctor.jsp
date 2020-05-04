
<%@ include file="/WEB-INF/jsp/fragments/tags.jspf"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Role"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.entity.HospitalCard"%>
<%@page
	import="ua.nure.teslenko.SummaryTask4.db.entity.PrescriptionType"%>
 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 <%@ include file="/WEB-INF/jsp/fragments/chooseLocale.jspf"%>
<fmt:bundle basename="resources.resource">
	
	<h1 align="center"><fmt:message key="card.card"/></h1>
	<h3 align="center"><c:out value="${patient.lastName } ${patient.firstName }"/></h3>
	
	<%-- If true, doctor can create a new prescription, diagnose and discharge patient --%>
	<c:set var="isMyPatient" value="false" />
	<c:forEach var="doc" items="${patientDoctors}">
		<c:if test="${doc.id == authorizedUser.id}">
			<c:set var="isMyPatient" value="true" />
		</c:if>
	</c:forEach>

	
	<c:if test="${patient.isActive eq false}">
							<a href="control?command=download&patientId=${patient.id }">
								<span class="admin-action__button  button">
									<fmt:message key="userMenu.downloadPatientInfo" />
								</span>
							</a>
	</c:if>
	
	<c:if test="${isMyPatient && patient.isActive}">
		<div class="doctor-menu__action">
			<div class=" button" onclick="showAddPrescription()">
				<fmt:message key="patientsList.addPresc"/></div>
			<div class=" button" onclick="showAddDiagnose()">
				<fmt:message key="patientsList.addDiagnose"/></div>
			<div class=" button" onclick="discharge('${patient.id}')">
				<fmt:message key="patientsList.discharge"/></div>
				
		</div>
		<div class="docto-menu__form-container">
			<form name="presc-form" id="presc-form" class="patient-menu-form">
				<input type="hidden" name="command" value="addPrescription">
				<input type="hidden" id="formPatientId" name="patientId"
					value="${patient.id}"> <input type="hidden"
					name="completionStatus" value="true">
				<table border="2">
					<tr>
						
						<td><select id="formPrescType" name="prescType">
								<c:forEach var="type" items="${PrescriptionType.values() }">
									<option value="${type}"><fmt:message key="prescType.${type}" /></option>
								</c:forEach>

						</select></td>
					</tr>
					<tr>
						
						<td><textarea id="form-description"
							name="description"> </textarea></td>
					</tr>
						<tr>
						<td><input type="submit" value="<fmt:message key="patientsList.addPresc"/>"></td>
						</tr>
				</table>
			</form>
			
			<form name="presc-form" id="diagnose-form" class="patient-menu-form">
				<input type="hidden" name="command" value="addDiagnose">
				<input type="hidden" id="formPatientIdDiagnose" name="patientId"
					value="${patient.id}"> 
				<table border="2">
					<tr>
							<td><textarea id="form-diagnose"
							name="diagnose"><c:out value="${patient.hospitalCard.diagnose }"/></textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="<fmt:message key="patientsList.addDiagnose"/>"></td>
						</tr>
				</table>
			</form>
			</div>
	</c:if>

	<div class="doctor-menu__data-container">
	<div class="doctor-menu__data">
		<fmt:message key="userMenu.isActiveText"/>: <fmt:message key="userMenu.${patient.isActive }"/>
	</div>
	<div class="doctor-menu__data">
		<fmt:message key="userMenu.diagnose"/>:
		<div class="diagnose">
			<c:out value="${patient.hospitalCard.diagnose }" />
		</div>
	</div>
	
	<table class="user-doctors-table">
		<tr class="user-doctors-table">
			<th class="user-doctors-table">#</th>
			<th class="user-doctors-table"><fmt:message key="card.type" /></th>
			<th class="user-doctors-table"><fmt:message key="card.description" /></th>
		</tr>
		<c:forEach var="presc" items="${patient.hospitalCard.prescriptions}"
			varStatus="i">
			<c:choose>
				<c:when test="${presc.completionStatus }">
					<c:set var="rowClass" value="user-doctors-table"/> 
				</c:when>
				<c:otherwise>
					<c:set var="rowClass" value="user-doctors-table presc-executed"/> 
				</c:otherwise>
			</c:choose>
				
			<tr class="${rowClass }">
				<td class="${rowClass }"><c:out value="${i.count}" /></td>
				<td class="${rowClass }"><fmt:message key="prescType.${presc.type	 }" /></td>
				<td class="${rowClass } presc-description" ><c:out value="${presc.description }" /></td>
				<c:if test="${patient.isActive}">
				<c:if test="${ authorizedUser.role eq Role.DOCTOR }">
				 <td class="resc-button">
				 	<span class="button" onclick="removePrescription('${presc.id}', '${patient.id }')">
				 		<fmt:message key="patientsList.removePresc"/>
				 	</span>
				 </td>
				 </c:if>
				<c:if
					test="${presc.completionStatus eq true
							&& ((authorizedUser.role eq Role.NURSE && presc.type != PrescriptionType.OPERATION)
				 			||	authorizedUser.role eq Role.DOCTOR)}">
				
					<td class="resc-button">
						<span class="button" onclick="executePrescription('${presc.id}', '${patient.id }')">
							<fmt:message key="patientsList.executePresc"/>
						</span>
					</td>
				</c:if>
				

				
				</c:if>
			</tr>
		</c:forEach>
	</table>
	</div>
</fmt:bundle>
