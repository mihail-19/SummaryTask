package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminSelect;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Getting patient menu, used AJAX.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandPatientMenuAdmin extends Command {
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		int patientId;
		try {
			 patientId = Integer.parseInt(request.getParameter("patientId"));
		} catch (IllegalArgumentException | NullPointerException e) {
			log.error("empty patient id");
			throw new AppException("command.emptyUserLogin", e);
		}
		DaoCommon daoCommon = new DaoCommon();
		Patient patient = daoCommon.getPatient(patientId);
		if(patient == null) {
			log.error("User is null for login -> " + request.getParameter("userLogin"));
			throw new AppException("command.failedGetUser");
		}
		DaoAdminSelect daoAdmin = new DaoAdminSelect();
		List<Doctor> patientDoctors = daoAdmin.getPatientDoctors(patient.getId());
		request.setAttribute("patientDoctors", patientDoctors);
		request.setAttribute("patient", patient);
		return Path.PATIENT_MENU_ADMIN;
	}
	
}
