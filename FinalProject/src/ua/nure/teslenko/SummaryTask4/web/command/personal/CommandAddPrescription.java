package ua.nure.teslenko.SummaryTask4.web.command.personal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.dao.personal.DaoPersonalInsert;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Command for prescription adding.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandAddPrescription extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * Adding a new prescription. Status is actual for new prescription.
	 */
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
		log.debug("presc description -> " + request.getParameter("description"));
		DaoPersonalInsert dao = new DaoPersonalInsert();
		dao.addPrescription(patientId, request.getParameter("prescType"),
				request.getParameter("description"));
		request.setAttribute("redirectLocation", "control?command=patientMenuDoctor&patientId=" + patientId);
		return "control?command=redirect";
	}

}
