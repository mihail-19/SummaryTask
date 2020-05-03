package ua.nure.teslenko.SummaryTask4.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.web.command.CommandSetLocale;

class TestCommandSetLocale extends Mockito{
	  
	private static final String RESULT_EXPECTED = "control?command=redirect";
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	private static HttpSession session = mock(HttpSession.class);
	private static CommandSetLocale setLocale = new CommandSetLocale();
	@Test
	void executeNull() {
		String testLocale = null;
		when(request.getParameter("locale")).thenReturn(testLocale);
		when(request.getSession()).thenReturn(session);
		
		try {
			String actual = setLocale.execute(request, response);
			assertEquals(RESULT_EXPECTED, actual);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void executeEn() {
		String testLocale = "en";
		when(request.getParameter("locale")).thenReturn(testLocale);
		when(request.getSession()).thenReturn(session);
		
		try {
			String actual = setLocale.execute(request, response);
			assertEquals(RESULT_EXPECTED, actual);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void executeUnexisted() {
		String testLocale = "hdtrhxbwr";
		when(request.getParameter("locale")).thenReturn(testLocale);
		when(request.getSession()).thenReturn(session);
		
		try {
			String actual = setLocale.execute(request, response);
			assertEquals(RESULT_EXPECTED, actual);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
}
