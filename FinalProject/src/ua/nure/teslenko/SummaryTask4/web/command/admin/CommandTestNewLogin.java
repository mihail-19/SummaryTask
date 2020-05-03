package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.util.Validator;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Test of login during registration.
 * Returns jsp page AJAX.
 * @author Mykhailo Teslenko
 *
 */
public class CommandTestNewLogin extends Command {

	private static final long serialVersionUID = 1L;
	static Logger log = LogManager.getLogger();


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		String newLogin = request.getParameter("login");
		try {
			Validator.isUniqueLogin(newLogin);
			request.setAttribute("isUnique", true);
		} catch (AppException e) {
			request.setAttribute("isUnique", false);
		}
		return Path.IS_UNIQUE_LOGIN;
	}
}
