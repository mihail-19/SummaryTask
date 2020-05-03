package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminInsert;
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.util.Validator;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Registration of th new patient.
 * @author Mykhailo Teslenko
 *
 */
public class CommandRegisterActionPatient extends Command {

	private static final long serialVersionUID = 1L;
	static Logger log = LogManager.getLogger();
	private String redirectLocation =  "control?command=showAllPatients";
	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started, request charset -> " + request.getCharacterEncoding());
		
		try {
			DaoAdminInsert adminInsert = new DaoAdminInsert();
			Patient patient = makePatient(request);
			adminInsert.addPatient(patient);
			log.debug("Patient added :" + patient);
			request.setAttribute("redirectLocation", redirectLocation);
			return "control?command=redirect";
		} catch (AppException e) {
			log.warn("Incorrect user data, return into -> " + Path.REGISTER_PAGE_PATIENT);
			log.warn(e.getMessage());
			return Path.REGISTER_PAGE_PATIENT;
		}
	}
	
	/**
	 * Method for creating a Patient from request parameters. 
	 * All data is validated.
	 * @param request
	 * @param role
	 * @return
	 * @throws AppException
	 */
	public Patient makePatient(HttpServletRequest request) throws AppException {
		log.debug("building user");
		Patient patient = new Patient();
		try {
			patient.setDateOfBirth(Date.valueOf(request.getParameter("dateOfBirth")));
		}  catch (IllegalArgumentException e) {
			log.warn("Wrong date format -> " + request.getParameter("dateOfBirth"));
			request.setAttribute("errorType", "date");
			throw new AppException("command.incorrectDate" ,e);
		}
		log.trace("user data validation");
		Validator.validateName(request.getParameter("firstName"));
		Validator.validateName(request.getParameter("lastName"));
		log.trace("user data is valid");
		
		patient.setFirstName(request.getParameter("firstName"));
		patient.setLastName(request.getParameter("lastName"));
		patient.setIsActive(true);
		return patient;
	}

}
