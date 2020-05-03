package ua.nure.teslenko.SummaryTask4.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;
import ua.nure.teslenko.SummaryTask4.web.command.CommandContainer;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/control")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = LogManager.getLogger(); 

	/**
	 * Executes command from CommandContainer. 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandName = request.getParameter("command");
		log.debug("command -> " + commandName);
		Map<String, String[]> params = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			log.trace("param -> " + entry.getKey() + ", value -> " + Arrays.toString(entry.getValue()));
		}
		if ("redirect".equals(commandName)) {
			log.debug("redirecting into -> " + request.getAttribute("redirectLocation"));
			log.trace("string redirect value -> " + String.valueOf(request.getAttribute("redirectLocation")));
			response.sendRedirect(String.valueOf(request.getAttribute("redirectLocation")));
		} else {
			Command command = CommandContainer.get(commandName);
			log.trace("trying execute command -> " + command);
			String forward = Path.ERROR_PAGE;
			try {
				forward = command.execute(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AppException e) {
				request.setAttribute("errorMessage", e.getMessage());
				e.printStackTrace();
			}
			request.getRequestDispatcher(forward).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("doPost started");
		doGet(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		log.info("Controller initialized");
		super.init(config);
		
		
	}

	
}
