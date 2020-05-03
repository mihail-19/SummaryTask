package ua.nure.teslenko.SummaryTask4.web.command;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Sets a locale for application.
 * @author admin
 *
 */
public class CommandSetLocale extends Command{
	private static final long serialVersionUID = 1L;
	static Logger log = LogManager.getLogger();
	/**
	 * If no locale, old one is used, method works idle.
	 * No exceptions in this case.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Command started");
		String locale = request.getParameter("locale");
		HttpSession session = request.getSession();
		log.debug("locale -> " + locale);
		
		if(locale != null) {
			session.setAttribute("locale", locale);
		} else {
			log.warn("locale parameter is empty. No attribute added");
		}
		request.setAttribute("redirectLocation", "/FinalProject");
		return "control?command=redirect";
	}

}
