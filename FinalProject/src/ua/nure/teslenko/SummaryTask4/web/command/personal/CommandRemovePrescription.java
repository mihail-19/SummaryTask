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
 * Command for removing prescription
 * @author Mykhailo Teslenko
 *	
 */
public class CommandRemovePrescription extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	
	/**
	 * Removing prescription, only doctor`s action.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		int prescId;
		try {
			prescId = Integer.parseInt(request.getParameter("prescId"));
		}catch (NumberFormatException | NullPointerException e) {
			log.error("incorrect prescription id -> " + request.getParameter("prescId"));
			throw new AppException("command.incorrectParameter", e);
		}
		if(prescId<0) {
			log.error("incorrect prescription id -> " + request.getParameter("prescId"));
			throw new AppException("command.incorrectParameter");
		}
		DaoPersonalInsert dao = new DaoPersonalInsert();
		dao.removePrescription(prescId);
		return "control?command=patientMenuDoctor&patientId=" + request.getParameter("patientId") ;
	}

}
