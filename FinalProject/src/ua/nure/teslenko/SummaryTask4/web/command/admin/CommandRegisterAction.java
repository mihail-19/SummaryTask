package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminInsert;
import ua.nure.teslenko.SummaryTask4.db.entity.Category;
import ua.nure.teslenko.SummaryTask4.db.entity.DoctorParams;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.util.Validator;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Class for user registration
 * @author Mykhailo Teslenko
 *
 */
public class CommandRegisterAction extends Command {

	private static final long serialVersionUID = 1L;
	static Logger log = LogManager.getLogger();
	private String redirectLocation =  "control?command=showAllUsers&role=ALL";
	/**
	 * Uses the userType parameter to distinct different roles. Different users has
	 * different dataset. addUser is common for all roles. Doctor has additional
	 * parameters, category and numberOfPatients.
	 * @throws AppException 
	 */

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started, request charset -> " + request.getCharacterEncoding());
		// If param is null or illegal - error
		Role role;
		try {
			role = Role.valueOf(request.getParameter("userType").toUpperCase());
		} catch(IllegalArgumentException | NullPointerException e) {
			log.error("Illegal user role in request, can't register");
			throw new AppException("incorrectRole", e);
		}
		
		try {
			DaoAdminInsert adminInsert = new DaoAdminInsert();
			// Date parsing. Null if incorrect
			int id;
			
			User user = makeUser(request, role);
			Validator.isUniqueLogin(user.getLogin());
			id = adminInsert.addUser(user, request.getParameter("password"));
			user.setId(id);
			log.debug("User added :" + user);
			if (role.equals(Role.DOCTOR)) {
				log.trace("User is a doctor");
				DoctorParams doctorParams = makeDocParams(request, user);
				adminInsert.addDoctorParams(doctorParams);
			}
			request.setAttribute("redirectLocation", redirectLocation);
			return "control?command=redirect";
		} catch (AppException e) {
			log.warn("Incorrect user data, return into -> " + Path.REGISTER_PAGE_DOCTOR);
			log.warn(e.getMessage());
			return Path.REGISTER_PAGE_DOCTOR;
		}
	}
	
	/**
	 * Method for creating a User from request parameters. 
	 * All data is validated.
	 * @param request
	 * @param role
	 * @return
	 * @throws AppException
	 */
	public User makeUser(HttpServletRequest request, Role role) throws AppException {
		log.debug("building user");
		User user = new User();
		try {
			user.setDateOfBirth(Date.valueOf(request.getParameter("dateOfBirth")));
		}  catch (IllegalArgumentException e) {
			log.warn("Wrong date format -> " + request.getParameter("dateOfBirth"));
			request.setAttribute("errorType", "date");
			throw new AppException("command.incorrectDate" ,e);
		}
		log.trace("user data validation");
		Validator.validateLogin(request.getParameter("login"));
		Validator.validateName(request.getParameter("firstName"));
		Validator.validateName(request.getParameter("lastName"));
		Validator.validatePassword(request.getParameter("password"));
		log.trace("user data is valid");
		
		user.setLogin(request.getParameter("login"));
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		
		
		user.setRole(role);
		return user;
	}
	
	/**
	 * Creating doctor parameters, that extends User class.
	 * @param request
	 * @param user
	 * @return
	 * @throws AppException
	 */
	public DoctorParams makeDocParams(HttpServletRequest request, User user) throws AppException {
		log.debug("Adding doctor parameters");
		DoctorParams doctorParams = new DoctorParams();
		try {
			doctorParams.setCategory(Category.valueOf(request.getParameter("category")));
		} catch(IllegalArgumentException | NullPointerException e) {
			log.warn("Empty or unexisting category");
			throw new AppException("command.incorrectCategory", e);
		}
		if(user == null || user.getId()<=0) {
			throw new AppException("command.cantBuildDocParams");
		}
		doctorParams.setDoctorId(user.getId());
		return doctorParams;
	}

}
