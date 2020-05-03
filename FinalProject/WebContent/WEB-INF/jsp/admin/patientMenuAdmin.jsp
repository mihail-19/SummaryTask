
<%@ include file="/WEB-INF/jsp/fragments/tags.jspf"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.Role"%>
<%@page import="ua.nure.teslenko.SummaryTask4.db.entity.HospitalCard"%>
<%@page
	import="ua.nure.teslenko.SummaryTask4.db.entity.PrescriptionType"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setLocale value="${locale }"/>
<fmt:bundle basename="resources.resource">
	<div class="user-menu-container">
		

		<h2 align="left">
			<fmt:message key="userMenu.pageName" /> <c:out value="${patient.lastName} ${patient.firstName }" />
		</h2>
		<div class="admin-action">
				<a
					href="control?command=removePatient&patientId=${patient.id }&sort=${sort}"
					 onclick="return confirm('<fmt:message key="userMenu.removeUser" /> ${patient.lastName }?')">
					<span class="admin-action__button  button">
						<fmt:message key="userMenu.removeUser" />
					</span>
				</a>
				<c:if test="${patient.isActive eq true}">
					<span class="admin-action__button button"
						onclick="selectDoctor('${patient.id}')">
							<fmt:message key="userMenu.addDoctor" />
					</span>
				</c:if>
				<c:if test="${patient.isActive eq false}">
							<a href="download?patientId=${patient.id }">
								<span class="admin-action__button  button">
									<fmt:message key="userMenu.downloadPatientInfo" />
								</span>
							</a>
				</c:if>
		</div>


		<div class="user-data">

			
				<div class="patient-data">
						<fmt:message key="userMenu.isActiveText"/>: <fmt:message key="userMenu.${patient.isActive }"/><br/>
						<fmt:message key="userMenu.diagnose"/>
						<div class="diagnose">
							<c:out value="${user.hospitalCard.diagnose }" />
						</div>
						
				</div>



				<h3>
					<fmt:message key="userMenu.userDoctors" />
					:
				</h3>
				<table class="user-doctors-table" >
					<tr class="user-doctors-table">
						<td class="user-doctors-table"><b><fmt:message key="userData.login" /></b></td>
						<td class="user-doctors-table"><b><fmt:message key="userData.firstName" /></b></td>
						<td class="user-doctors-table"><b><fmt:message key="userData.lastName" /></b></td>
						<td class="user-doctors-table"><b><fmt:message key="userData.category" /></b></td>
						<td class="user-doctors-table"><b><fmt:message key="userData.numberOfPatients" /></b></td>
					</tr>
					<c:forEach var="doc" items="${patientDoctors}">
						<tr class="user-doctors-table">
							<td class="user-doctors-table"><c:out value="${doc.login}" /></td>
							<td class="user-doctors-table"><c:out value="${doc.firstName}" /></td>
							<td class="user-doctors-table"><c:out value="${doc.lastName}" /></td>
							<td class="user-doctors-table"><fmt:message key="category.${doc.category}" /></td>
							<td class="user-doctors-table"><c:out value="${doc.numberOfPatients}" /></td>
							<td class="user-doctors-table__del"
								onclick="removeUserDoctor('${doc.id}', '${patient.id}')">
								<fmt:message key="userMenu.delete" />
							</td>
						</tr>
					</c:forEach>
				</table>
		</div>


		
	</div>
</fmt:bundle>