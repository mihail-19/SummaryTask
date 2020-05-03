package ua.nure.teslenko.SummaryTask4.web.command.autorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Command for logout. Destroying session.
 * @author admin
 *
 */
public class CommandLogout extends Command{

	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.invalidate();
		request.setAttribute("redirectLocation", "/FinalProject");
		return "control?command=redirect";
		
	}

}
