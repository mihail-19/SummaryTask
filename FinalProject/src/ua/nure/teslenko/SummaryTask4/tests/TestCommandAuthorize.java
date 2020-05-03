package ua.nure.teslenko.SummaryTask4.tests;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.autorization.CommandAuthorize;

public class TestCommandAuthorize extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	
	private static CommandAuthorize authorize = new CommandAuthorize();
	
	
	@Test
	public void executeNullId() {
		when(request.getParameter("login")).thenReturn(null);
		assertThrows(AppException.class, () -> authorize.execute(request, response));
	}

		
}	
