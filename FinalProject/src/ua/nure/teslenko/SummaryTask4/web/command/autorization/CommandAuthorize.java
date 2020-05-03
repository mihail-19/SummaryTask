package ua.nure.teslenko.SummaryTask4.web.command.autorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;
/**
 * Authorization for Admin, Doctor and Nurse roles.
 * @author admin
 *
 */
public class CommandAuthorize extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		
		String userLogin = request.getParameter("login");
		if (userLogin == null) {
			log.error("empty user login");
			throw new AppException("command.emptyUserLogin");
		}
		DaoCommon dao = new DaoCommon();
		User user = dao.authorizeUser(userLogin, request.getParameter("password")); 
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("authorizedUser", user);
			request.setAttribute("redirectLocation", "/FinalProject");
			return "control?command=redirect";
		} else {
			request.setAttribute("incorrectAuthorization", "true");
			return Path.LOGIN_PAGE;
		}
		
	}

}
