package ua.nure.teslenko.SummaryTask4.tests.admin;

import static org.junit.jupiter.api.Assertions.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandAddUserDoctor;

public class TestCommandAddUserDoctor extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
//	private static DBManager dbManager = mock(DBManager.class);
	
	private static CommandAddUserDoctor addUserDoctor = new CommandAddUserDoctor();
	@Test
	public void executeNullPatientLogin() {
		String patientId = null;
		when(request.getParameter("patientId")).thenReturn(patientId);
		assertThrows(AppException.class, () -> addUserDoctor.execute(request, response));
	}
	
	@Test
	public void executeNullDoctorLogin() {
		String doctorId = null;
		when(request.getParameter("doctorId")).thenReturn(doctorId);
		assertThrows(AppException.class, () -> addUserDoctor.execute(request, response));
	}
	
	
}
