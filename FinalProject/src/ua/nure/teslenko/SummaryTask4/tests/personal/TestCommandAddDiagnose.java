package ua.nure.teslenko.SummaryTask4.tests.personal;

import static org.junit.jupiter.api.Assertions.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandAddDiagnose;

public class TestCommandAddDiagnose extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	
	private static CommandAddDiagnose addDiagnose = new CommandAddDiagnose();
	
	
	@Test
	public void executeNullLogin() {
		when(request.getParameter("patientId")).thenReturn(null);
		assertThrows(AppException.class, () -> addDiagnose.execute(request, response));
	}

		
}	
