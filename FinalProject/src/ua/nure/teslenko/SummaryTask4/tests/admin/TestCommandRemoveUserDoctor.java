package ua.nure.teslenko.SummaryTask4.tests.admin;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRemoveUserDoctor;

public class TestCommandRemoveUserDoctor extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	
	private static CommandRemoveUserDoctor removeDoctor = new CommandRemoveUserDoctor();
	@Test
	public void executeNullUserId() {
		when(request.getParameter("patientId")).thenReturn(null);
		assertThrows(AppException.class, () -> removeDoctor.execute(request, response));
	}
	
	@Test
	public void executeNullDoctorId() {
		when(request.getParameter("doctorId")).thenReturn(null);
		assertThrows(AppException.class, () -> removeDoctor.execute(request, response));
	}
	
	
}
