package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminInsert;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Removing patient from DB.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandRemovePatient extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	
	/**
	 * First, it gets a list of doctors for the patient,
	 * then, decrementing the number of patients for all doctors
	 * in list.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		int patientId;
		try {
			patientId = Integer.parseInt(request.getParameter("patientId"));
		} catch (IllegalArgumentException | NullPointerException e) {
			log.error("Empty or incorrect patient id ");
			e.printStackTrace();
			throw new AppException();
		}
		DaoCommon dao = new DaoCommon();
		List<Doctor> patientDoctors = dao.getUserDoctors(patientId);
		DaoAdminInsert daoAdmin = new DaoAdminInsert();
		daoAdmin.removePatient(patientId, patientDoctors);
		request.setAttribute("sort", request.getParameter("sort"));
		return "/control?command=showAllPatients";
	}

}
