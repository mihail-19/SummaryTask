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
 * Command for adding diagnose.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandAddDiagnose extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	
	/**
	 * Adds diagnose? value in DB is updated.
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
		log.debug("adding diagnose -> " + request.getParameter("diagnose"));
		
		DaoPersonalInsert dao = new DaoPersonalInsert();
		dao.addDiagnose(patientId, 
				request.getParameter("diagnose"));
		request.setAttribute("redirectLocation", "control?command=patientMenuDoctor&patientId=" + patientId);
		return "control?command=redirect";
	}

}
