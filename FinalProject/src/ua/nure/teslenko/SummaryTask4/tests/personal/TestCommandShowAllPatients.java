package ua.nure.teslenko.SummaryTask4.tests.personal;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.SortConstants;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.CommandShowAllPatients;

public class TestCommandShowAllPatients extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	
	private static CommandShowAllPatients showAllPatients = new CommandShowAllPatients();
	
	public static List<User> usersList = new ArrayList<>();
	public static User testUser;
	public static User testUser2;
	static {
		testUser = new User();
		testUser.setId(1);
		testUser.setLogin("testLogin");
		testUser.setFirstName("testName");
		testUser.setLastName("testSurname");
		testUser.setDateOfBirth(Date.valueOf("1999-10-10"));
		usersList.add(testUser);
		testUser2 = new User();
		testUser2.setId(2);
		testUser2.setLogin("testLogin2");
		testUser2.setFirstName("testName2");
		testUser2.setLastName("testSurname2");
		testUser2.setDateOfBirth(Date.valueOf("1999-12-10"));
		usersList.add(testUser2);
	}
	
	@Test
	public void chooseSortNull() throws AppException {
		when(request.getParameter("sort")).thenReturn(null);
		assertEquals(Sort.lastName, showAllPatients.chooseSort(request));
	}
	@Test
	public void chooseSortCorrect() throws AppException {
		when(request.getParameter("sort")).thenReturn("firstName");
		assertEquals(Sort.firstName, showAllPatients.chooseSort(request));
	}
	@Test
	public void chooseSortIncorrect() throws AppException {
		when(request.getParameter("sort")).thenReturn("fwefadgrtj");
		assertThrows(AppException.class, () -> showAllPatients.execute(request, response));
	}
	@Test
	public void limitNull() {
		when(request.getParameter("limit")).thenReturn(null);
		assertEquals(SortConstants.LIMIT, showAllPatients.setLimit(request));
	}
	@Test
	public void limitCorrect() {
		when(request.getParameter("limit")).thenReturn("400");
		assertEquals(400, showAllPatients.setLimit(request));
	}
	
	@Test
	public void offsetNegative() {
		when(request.getParameter("offset")).thenReturn("-1500");
		assertEquals(SortConstants.OFFSET, showAllPatients.setOffset(request));
	}
	@Test
	public void offsetCorrect() {
		when(request.getParameter("offset")).thenReturn("400");
		assertEquals(400, showAllPatients.setOffset(request));
	}

		
}	
