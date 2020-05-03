package ua.nure.teslenko.SummaryTask4.tests.admin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import ua.nure.teslenko.SummaryTask4.SortConstants;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandShowDoctors;

public class TestCommandShowDoctors extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	
	private static CommandShowDoctors showDoctors = new CommandShowDoctors();
	
	public static User testUser;
	
	static {
		testUser = new User();
		testUser.setId(1);
		testUser.setLogin("testLogin");
		testUser.setFirstName("testName");
		testUser.setLastName("testSurname");
		testUser.setDateOfBirth(Date.valueOf("1999-10-10"));
	}
	
	@Test
	public void executeNullLogin() throws IOException, ServletException, AppException {
		when(request.getParameter("userLogin")).thenReturn(null);
		assertThrows(AppException.class, () -> showDoctors.execute(request, response));
	}
	
	@Test
	public void chooseSortNull() {
		when(request.getParameter("sort")).thenReturn(null);
		assertEquals(Sort.lastName, showDoctors.chooseSort(request));
	}
	@Test
	public void chooseSortCorrect() {
		when(request.getParameter("sort")).thenReturn("firstName");
		assertEquals(Sort.firstName, showDoctors.chooseSort(request));
	}
	
	@Test
	public void limitNull() {
		when(request.getParameter("limit")).thenReturn(null);
		assertEquals(SortConstants.LIMIT, showDoctors.setLimit(request));
	}
	@Test
	public void limitCorrect() {
		when(request.getParameter("limit")).thenReturn("400");
		assertEquals(400, showDoctors.setLimit(request));
	}
	
	@Test
	public void offsetNegative() {
		when(request.getParameter("offset")).thenReturn("-1500");
		assertEquals(SortConstants.OFFSET, showDoctors.setOffset(request));
	}
	@Test
	public void offsetCorrect() {
		when(request.getParameter("offset")).thenReturn("400");
		assertEquals(400, showDoctors.setOffset(request));
	}

		
}	
