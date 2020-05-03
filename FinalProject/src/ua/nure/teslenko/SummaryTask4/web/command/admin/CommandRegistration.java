package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Executes when the button on page is activated.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandRegistration extends Command{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Returns a registration page with forms, 
	 * different pages for Doctor/Nurse and Patient registration.
	 * @throws AppException 
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		String userType = request.getParameter("userType");
		if(userType == null) {
			throw new AppException();
		}
		if(userType.equalsIgnoreCase("doctor")) {
			return Path.REGISTER_PAGE_DOCTOR;
		} else if (userType.equalsIgnoreCase("patient")) {
			return Path.REGISTER_PAGE_PATIENT;
		} else {
			throw new AppException();
		}
	}

}
