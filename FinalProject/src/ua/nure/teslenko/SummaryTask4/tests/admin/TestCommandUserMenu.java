package ua.nure.teslenko.SummaryTask4.tests.admin;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.entity.Category;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.db.entity.DoctorParams;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.admin.CommandUserMenu;

public class TestCommandUserMenu extends Mockito{
	  
	private static	HttpServletRequest request = mock(HttpServletRequest.class);
	private static HttpServletResponse response = mock(HttpServletResponse.class);
	private static CommandUserMenu userMenu = new CommandUserMenu();
	
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
	public void executeNullUserLogin() {
		String userLogin = null;
		when(request.getParameter("userLogin")).thenReturn(userLogin);
		assertThrows(AppException.class, () -> userMenu.execute(request, response));
	}
	
	
	@Test
	public void buildDoctorNullParams() {
		testUser.setRole(Role.DOCTOR);
		DoctorParams doctorParams = null;
		assertThrows(AppException.class, () -> userMenu.buildDoctor(testUser, doctorParams));
	}
	@Test
	public void buildDoctorEmptyParams() {
		testUser.setRole(Role.DOCTOR);
		DoctorParams doctorParams = new DoctorParams();
		assertThrows(AppException.class, () -> userMenu.buildDoctor(testUser, doctorParams));
	}
	@Test
	public void buildDoctorRealParams() throws AppException {
		testUser.setRole(Role.DOCTOR);
		DoctorParams doctorParams = new DoctorParams();
		doctorParams.setCategory(Category.OPHTHALMOLOGIST);
		doctorParams.setNumberOfPatients(-1);
		Doctor expected = new Doctor(testUser);
		expected.setCategory(Category.OPHTHALMOLOGIST);
		expected.setNumberOfPatients(doctorParams.getNumberOfPatients());
		assertEquals(expected, userMenu.buildDoctor(testUser, doctorParams));
	}
		
}	
