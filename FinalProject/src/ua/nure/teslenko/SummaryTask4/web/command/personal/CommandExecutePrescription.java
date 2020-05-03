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
 * Command for prescription execution.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandExecutePrescription extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	
	/**
	 * Executes a prescription if patient is 
	 * not discharged.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		int prescId;
		try {
			prescId = Integer.parseInt(request.getParameter("prescId"));
		}catch (NumberFormatException | NullPointerException e) {
			throw new AppException("command.incorrectParameter", e);
		}
		
		DaoPersonalInsert dao = new DaoPersonalInsert();
		dao.executePrescription(prescId);
		return "control?command=patientMenuDoctor&patientId=" + request.getParameter("patientId") ;
	}

}
