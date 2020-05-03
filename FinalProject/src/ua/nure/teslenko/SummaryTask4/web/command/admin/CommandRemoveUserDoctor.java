package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminInsert;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Removing connection between doctor and patient.
 * @author Mykhailo Teslenko 
 */
public class CommandRemoveUserDoctor extends Command {
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	/**
	 * returns a registration page with forms
	 * @throws AppException 
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		int patientId;
		int doctorId;
		try {
			patientId = Integer.parseInt(request.getParameter("patientId"));
			doctorId = Integer.parseInt(request.getParameter("doctorId"));
		} catch (IllegalArgumentException | NullPointerException e) {
			log.error("Empty or incorrect patient id or doctor id");
			e.printStackTrace();
			throw new AppException();
		}
		DaoAdminInsert dao = new DaoAdminInsert();
		dao.removeUserDoctor(patientId, doctorId);
		request.setAttribute("redirectLocation", "control?command=patientMenuAdmin&patientId=" + patientId );
		return "control?command=redirect";
	}

}
