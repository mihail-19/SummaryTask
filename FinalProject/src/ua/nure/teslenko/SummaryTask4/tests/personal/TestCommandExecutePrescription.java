package ua.nure.teslenko.SummaryTask4.tests.personal;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandExecutePrescription;

public class TestCommandExecutePrescription extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	
	private static CommandExecutePrescription executePresc = new CommandExecutePrescription();
	
	@Test
	public void executeNotIntPrescId() {
		when(request.getParameter("prescId")).thenReturn("0.234");
		assertThrows(AppException.class, () -> executePresc.execute(request, response));
	}
	
}	
