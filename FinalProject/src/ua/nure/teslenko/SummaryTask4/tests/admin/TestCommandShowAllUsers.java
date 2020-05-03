package ua.nure.teslenko.SummaryTask4.tests.admin;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.nure.teslenko.SummaryTask4.SortConstants;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandShowAllUsers;

public class TestCommandShowAllUsers extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpSession session = mock(HttpSession.class);
	
	private static CommandShowAllUsers showAllUsers = new CommandShowAllUsers();
	
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
	public void chooseSortNullSort() {
		when(request.getParameter("sort")).thenReturn(null);
		assertEquals(Sort.lastName, showAllUsers.chooseSort(request, session));
	}
	@Test
	public void chooseSortChangeSort() {
		when(request.getParameter("sort")).thenReturn("category");
		when(request.getParameter("role")).thenReturn("doctor");
		when(session.getAttribute("role")).thenReturn("doctor");
		assertEquals(Sort.category, showAllUsers.chooseSort(request, session));
	}
	@Test
	public void chooseSortChangeRole() {
		when(request.getParameter("sort")).thenReturn("category");
		when(request.getParameter("role")).thenReturn("doctor");
		when(session.getAttribute("role")).thenReturn("nurse");
		assertEquals(Sort.lastName, showAllUsers.chooseSort(request, session));
	}
	
	@Test
	public void limitNull() {
		when(request.getParameter("limit")).thenReturn(null);
		assertEquals(SortConstants.LIMIT, showAllUsers.setLimit(request));
	}
	@Test
	public void limitCorrect() {
		when(request.getParameter("limit")).thenReturn("400");
		assertEquals(400, showAllUsers.setLimit(request));
	}
	
	@Test
	public void offsetNegative() {
		when(request.getParameter("offset")).thenReturn("-1500");
		assertEquals(SortConstants.OFFSET, showAllUsers.setOffset(request));
	}
	@Test
	public void offsetCorrect() {
		when(request.getParameter("offset")).thenReturn("400");
		assertEquals(400, showAllUsers.setOffset(request));
	}

		
}	
