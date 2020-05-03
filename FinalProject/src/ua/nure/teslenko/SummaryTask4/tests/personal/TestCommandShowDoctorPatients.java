package ua.nure.teslenko.SummaryTask4.tests.personal;

import static org.junit.jupiter.api.Assertions.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import ua.nure.teslenko.SummaryTask4.SortConstants;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.personal.CommandShowDoctorPatients;

public class TestCommandShowDoctorPatients extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	private static HttpSession session = mock(HttpSession.class);
	private static CommandShowDoctorPatients showDoctorPatients = new CommandShowDoctorPatients();
	
	@Test
	public void executeNullDoc() throws AppException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("authorizedUser")).thenReturn(null);
		assertThrows(AppException.class, () -> showDoctorPatients.execute(request, response));
	}
	
	@Test
	public void chooseSortNull() throws AppException {
		when(request.getParameter("sort")).thenReturn(null);
		assertEquals(Sort.lastName, showDoctorPatients.chooseSort(request));
	}
	@Test
	public void chooseSortCorrect() throws AppException {
		when(request.getParameter("sort")).thenReturn("firstName");
		assertEquals(Sort.firstName, showDoctorPatients.chooseSort(request));
	}
	@Test
	public void chooseSortIncorrect() throws AppException {
		when(request.getParameter("sort")).thenReturn("fwefadgrtj");
		assertThrows(AppException.class, () -> showDoctorPatients.execute(request, response));
	}
	@Test
	public void limitNull() {
		when(request.getParameter("limit")).thenReturn(null);
		assertEquals(SortConstants.LIMIT, showDoctorPatients.setLimit(request));
	}
	@Test
	public void limitCorrect() {
		when(request.getParameter("limit")).thenReturn("400");
		assertEquals(400, showDoctorPatients.setLimit(request));
	}
	
	@Test
	public void offsetNegative() {
		when(request.getParameter("offset")).thenReturn("-1500");
		assertEquals(SortConstants.OFFSET, showDoctorPatients.setOffset(request));
	}
	@Test
	public void offsetCorrect() {
		when(request.getParameter("offset")).thenReturn("400");
		assertEquals(400, showDoctorPatients.setOffset(request));
	}

		
}	
