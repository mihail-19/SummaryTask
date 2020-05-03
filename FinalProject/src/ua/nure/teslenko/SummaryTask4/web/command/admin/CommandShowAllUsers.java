package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.SortConstants;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminSelect;
import ua.nure.teslenko.SummaryTask4.db.entity.DoctorParams;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Shows a table with doctors and nurses.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandShowAllUsers extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		DaoAdminSelect adminSelect = new DaoAdminSelect();
		DaoCommon commonDao = new DaoCommon();
		String role = request.getParameter("role");
		try {
			HttpSession session = request.getSession();
			Sort sort = chooseSort(request, session);
			int offset = setOffset(request);
			int limit = setLimit(request); 
			List<? extends User> usersList;
			Map<Integer, DoctorParams> paramsMap;
			paramsMap = commonDao.getDoctorsParamsMap();
			boolean showDischarged = false;
			if(request.getParameter("showDischarged") != null) {
				showDischarged = Boolean.valueOf(request.getParameter("showDischarged"));
			}
			log.trace("discharged -> " + showDischarged);	
			if (role == null || "All".equalsIgnoreCase(role)) {
				usersList = adminSelect.getUsersList(limit, offset, sort);
			} else if (role.equals(Role.DOCTOR.toString())
					&& (sort.equals(Sort.category) || sort.equals(Sort.numberOfPatients))) {
				usersList = adminSelect.getDoctorsList(limit, offset, sort);
				
			} else {
				usersList = commonDao.getUserByRole(Role.valueOf(role), limit, offset, sort, showDischarged);
			}
			session.setAttribute("role", role);
			request.setAttribute("showDischarged", showDischarged);
			request.setAttribute("doctorParamsMap", paramsMap);
			request.setAttribute("usersList", usersList);
			request.setAttribute("offset", offset);
			request.setAttribute("limit", limit);
			request.setAttribute("sort", sort);
			
			
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			throw new AppException("command.incorrectParameter", e);
		}
		return Path.USERS_LIST;
	}
	
	/**
	 * Session is used to prevent exceptions switching from DOCTOR's list to other, when 'category' sort type is chosen.
	 * Here, we comparing the parameter 'role' with session attribute, if it has been changed
	 * then, we using the default value.
	 * @param request
	 * @return right sort to be used in getting the user's list
	 */
	public  Sort chooseSort(HttpServletRequest request, HttpSession session) {
		Sort sort = Sort.lastName;
		for(Sort s : Sort.values()) {
			if(s.toString().equalsIgnoreCase(request.getParameter("sort"))){
				sort = Sort.valueOf(request.getParameter("sort"));
			}
		}
		if(session.getAttribute("role") == null || !session.getAttribute("role").equals(request.getParameter("role"))){
			sort = Sort.lastName;
		}
		return sort;
	}
	
	/**
	 * Sets a limit of rows from DB to read.
	 * @param request
	 * @return
	 */
	public  int setLimit(HttpServletRequest request) {
		if (request.getParameter("limit") == null || Integer.parseInt(request.getParameter("limit"))<0) {
			return SortConstants.LIMIT;
		} else {
			return Integer.parseInt(request.getParameter("limit"));
		}
	}

	/**
	 * Sets a number of rows from DB to read.
	 * @param request
	 * @return
	 */
	public  int setOffset(HttpServletRequest request) {
		if (request.getParameter("offset") == null || Integer.parseInt(request.getParameter("offset"))<0) {
			return SortConstants.OFFSET;
		} else {
			return Integer.parseInt(request.getParameter("offset"));
		}
	}

}
