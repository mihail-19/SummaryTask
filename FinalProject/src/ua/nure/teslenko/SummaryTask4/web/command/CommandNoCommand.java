package ua.nure.teslenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
/**
 * Executes when no command was found in CommandContainer
 * @author Mykhailo Teslenko
 *
 */
public class CommandNoCommand extends Command{

	private static final long serialVersionUID = 1L;
	static Logger log = LogManager.getLogger();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Command started");
		String errorMessage = "No such command";
		request.setAttribute(("command.noSuchCommand"), errorMessage);
		return Path.ERROR_PAGE;
	}

}
