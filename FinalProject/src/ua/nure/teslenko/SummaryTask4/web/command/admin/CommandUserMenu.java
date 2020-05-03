package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.db.entity.DoctorParams;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Selecting parameters for user menu (Doctor and Nurse).
 * 
 * @author Mykhailo Teslenko
 *	
 */
public class CommandUserMenu extends Command {
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		String userLogin = request.getParameter("userLogin");
		if (userLogin == null) {
			log.error("empty patient login");
			throw new AppException("command.emptyUserLogin");
		}
		DaoCommon daoCommon = new DaoCommon();
		User user = daoCommon.getUser(userLogin);
		if(user == null) {
			log.error("User is null for login -> " + request.getParameter("userLogin"));
			throw new AppException("command.failedGetUser");
		}
		Role role = user.getRole();
		if(role.equals(Role.DOCTOR)) {
			DoctorParams doctorParams = daoCommon.getDoctorParams(user.getId());
			Doctor doctor = buildDoctor(user, doctorParams);
			request.setAttribute("user", doctor);
		} else {
			request.setAttribute("user", user);
		}
		return Path.USER_MENU;
	}
	
	/**
	 * Method for Doctor building from User class and DoctorParams.
	 * @param user
	 * @param doctorParams
	 * @return
	 * @throws AppException
	 */
	public Doctor buildDoctor(User user, DoctorParams doctorParams) throws AppException {
		if(doctorParams == null || doctorParams.getCategory() == null) {
			throw new AppException("command.failedGetUser");
		}
		Doctor doctor = new Doctor(user);
		doctor.setCategory(doctorParams.getCategory());
		doctor.setNumberOfPatients(doctorParams.getNumberOfPatients());
		return doctor;
	}
}
