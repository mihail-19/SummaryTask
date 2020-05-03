package ua.nure.teslenko.SummaryTask4.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.web.command.CommandNoCommand;

class TestCommandNoCommand extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	private static HttpSession session = mock(HttpSession.class);
	private static CommandNoCommand setLocale = new CommandNoCommand();
	@Test
	void executeNullLocale() {
		String testLocale = null;
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("locale")).thenReturn(testLocale);
		try {
			String actual = setLocale.execute(request, response);
			assertEquals(Path.ERROR_PAGE, actual);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void executeEnLocale() {
		String testLocale = "en";
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("locale")).thenReturn(testLocale);
		try {
			String actual = setLocale.execute(request, response);
			assertEquals(Path.ERROR_PAGE, actual);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void executeUnexistedLocale() {
		String testLocale = "sgergegearn";
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("locale")).thenReturn(testLocale);
		try {
			String actual = setLocale.execute(request, response);
			assertEquals(Path.ERROR_PAGE, actual);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
}
