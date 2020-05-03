package ua.nure.teslenko.SummaryTask4.tests.personal;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandRemovePrescription;

public class TestCommandRemovePrescription extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	
	private static CommandRemovePrescription removePrescription = new CommandRemovePrescription();
	
	
	@Test
	public void executeNullId() {
		when(request.getParameter("prescId")).thenReturn(null);
		assertThrows(AppException.class, () -> removePrescription.execute(request, response));
	}
	@Test
	public void executeIncorrectId() {
		when(request.getParameter("prescId")).thenReturn("sdfwe");
		assertThrows(AppException.class, () -> removePrescription.execute(request, response));
	}
	@Test
	public void executeNotIntId() {
		when(request.getParameter("prescId")).thenReturn("0.3245");
		assertThrows(AppException.class, () -> removePrescription.execute(request, response));
	}
	@Test
	public void executeNegativeId() {
		when(request.getParameter("prescId")).thenReturn("-10");
		assertThrows(AppException.class, () -> removePrescription.execute(request, response));
	}
		
}	
