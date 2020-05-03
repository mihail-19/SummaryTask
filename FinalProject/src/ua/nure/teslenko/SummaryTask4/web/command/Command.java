package ua.nure.teslenko.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import ua.nure.teslenko.SummaryTask4.exception.AppException;



/**
 * @author Mykhailo Teslenko
 *
 */
public abstract class Command implements Serializable {

	private static final long serialVersionUID = 1L;

/**
 * Main public method for Command classes
 * @param request Servlet request
 * @param response Servlet response
 * @return should return a path for Dispatcher.forward()
 * @throws IOException
 * @throws ServletException
 */
	public abstract String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}