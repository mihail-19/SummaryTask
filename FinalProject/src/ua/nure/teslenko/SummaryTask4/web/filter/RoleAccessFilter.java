package ua.nure.teslenko.SummaryTask4.web.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.entity.User;


/**
 * Security filter. Disabled by default. Uncomment Security filter
 * section in web.xml to enable.
 * 
 * @author D.Kolesnikov
 * 
 */
public class RoleAccessFilter implements Filter {
	
	static Logger log = LogManager.getLogger();

	// commands access	
	private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private List<String> commons = new ArrayList<>();	
	private List<String> outOfControl = new ArrayList<>();
	
	public void destroy() {
		log.debug("Filter destruction starts");
		// do nothing
		log.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("access filter starts");
		
		if (accessAllowed(request)) {
			log.debug("Filter finished, access alowed");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";
			
			request.setAttribute("errorMessage", "noPremission");
			log.trace("Set the request attribute: errorMessage --> " + errorMessasge);
			
			request.getRequestDispatcher(Path.ERROR_PAGE)
					.forward(request, response);
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}
		
		if (outOfControl.contains(commandName)) {
			return true;
		}
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null) { 
			return false;
		}
		
		User user = (User)session.getAttribute("authorizedUser");
		
		if (user == null) {
			return false;
		} 
			
		return accessMap.get(user.getRole()).contains(commandName)
				|| commons.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		log.debug("Filter initialization starts");
		
		// roles
		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(Role.DOCTOR, asList(fConfig.getInitParameter("doctor")));
		accessMap.put(Role.NURSE, asList(fConfig.getInitParameter("nurse")));
		log.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		log.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		log.trace("Out of control commands --> " + outOfControl);
		
		log.debug("Filter initialization finished");
	}
	
	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		if(str != null) {
		
			StringTokenizer st = new StringTokenizer(str);
			while (st.hasMoreTokens()) {
				list.add(st.nextToken());
			}
		}
		return list;		
	}
	
}