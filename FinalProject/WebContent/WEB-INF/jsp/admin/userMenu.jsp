
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
			<fmt:message key="userMenu.pageName" /> <c:out value="${user.login}" /> (<fmt:message key="role.${user.role}" />)
		</h2>
		<%-- Main info, user plus referenced users 
		for admins and doctorss	--%>
		<div class="admin-action">
			
				<a
					href="control?command=removeUser&userId=${user.id}"
					 onclick="return confirm('<fmt:message key="userMenu.removeUser" /> ${user.login }?')">
					<span class="admin-action__button  button">
						<fmt:message key="userMenu.removeUser" />
					</span>
				</a>
			<c:if test="${user.role eq Role.DOCTOR }">
				<div class="admin-action__button button">Add patient</div>
			</c:if>
		</div>


		<div class="user-data">

			<%-- Differences for patients and doctors. Patients has option
	to add a doctor, doctor  - to be added --%>

		</div>


		
	</div>
</fmt:bundle>