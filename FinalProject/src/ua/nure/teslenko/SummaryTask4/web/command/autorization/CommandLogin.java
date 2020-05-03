package ua.nure.teslenko.SummaryTask4.web.command.autorization;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.web.command.Command;
/**
 * Command for redirect into login page.
 * @author admin
 *
 */
public class CommandLogin extends Command{

	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		if(session.getAttribute("locale") == null) {
			session.setAttribute("locale", Locale.getDefault());
		}
		return Path.LOGIN_PAGE;
	}

}
