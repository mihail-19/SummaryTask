package ua.nure.teslenko.SummaryTask4.tests.admin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.entity.Category;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandRegisterAction;

public class TestCommandRegisterAction extends Mockito{
	  
	private static HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
//	private static DBManager dbManager = mock(DBManager.class);
	
	private static CommandRegisterAction registerAction = new CommandRegisterAction();
	
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
	public void executeNullRole() {
		when(request.getParameter("userType")).thenReturn(null);
		assertThrows(AppException.class, () -> registerAction.execute(request, response));
	}
	@Test
	public void executeCorrectRole() throws IOException, ServletException, AppException {
		when(request.getParameter("userType")).thenReturn("doctor");
		assertNotNull( registerAction.execute(request, response));
	}
	
	@Test
	public void makeDocParamsNullCategory() {
		when(request.getParameter("category")).thenReturn(null);
		assertThrows(AppException.class, () -> registerAction.makeDocParams(request, testUser));
	}
	@Test
	public void makeDocParamsIncorrectCategory() {
		when(request.getParameter("category")).thenReturn("sdferg");
		assertThrows(AppException.class, () -> registerAction.makeDocParams(request, testUser));
	}
	@Test
	public void makeDocWrongParams() {
		when(request.getParameter("category")).thenReturn(Category.OPHTHALMOLOGIST.toString());
		testUser.setId(-1);
		assertThrows(AppException.class, () -> registerAction.makeDocParams(request, testUser));
		testUser.setId(1);
	}
	@Test
	public void makeDocCorrect() throws AppException {
		when(request.getParameter("category")).thenReturn(Category.OPHTHALMOLOGIST.toString());
		assertNotNull(registerAction.makeDocParams(request, testUser));
	}
	
	@Test
	public void makeUserNullLogin()  {
		when(request.getParameter("dateOfBirth")).thenReturn("1999-05-05");
		when(request.getParameter("login")).thenReturn(null);
		when(request.getParameter("firstName")).thenReturn(null);
		assertThrows(AppException.class, () -> registerAction.makeUser(request, Role.NURSE));
	}
	@Test
	public void makeUserIncorrectDate()  {
		when(request.getParameter("dateOfBirth")).thenReturn("1999-05-45");
		
		assertThrows(AppException.class, () -> registerAction.makeUser(request, Role.NURSE));
	}
	@Test
	public void makeUserCorrect() throws AppException  {
		when(request.getParameter("dateOfBirth")).thenReturn(testUser.getDateOfBirth().toString());
		when(request.getParameter("login")).thenReturn(testUser.getLogin());
		when(request.getParameter("firstName")).thenReturn(testUser.getFirstName());
		when(request.getParameter("lastName")).thenReturn(testUser.getLastName());
		when(request.getParameter("password")).thenReturn("123456");
		testUser.setRole(Role.NURSE);
		assertEquals(testUser, registerAction.makeUser(request, Role.NURSE));
	}
}
