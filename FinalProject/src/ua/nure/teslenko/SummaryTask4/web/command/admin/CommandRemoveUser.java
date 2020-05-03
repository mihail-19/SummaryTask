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
 * Removing Doctor or Nurse.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandRemoveUser extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		int userId;
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (IllegalArgumentException | NullPointerException e) {
			log.error("Empty or incorrect user id ");
			e.printStackTrace();
			throw new AppException();
		}
		DaoAdminInsert daoAdmin = new DaoAdminInsert();
		daoAdmin.removeUser(userId);
		return "/control?command=showAllUsers";
	}

}
