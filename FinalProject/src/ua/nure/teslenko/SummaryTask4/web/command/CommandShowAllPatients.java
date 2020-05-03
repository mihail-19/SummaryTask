package ua.nure.teslenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Show all patients
 * @author Mykhailo Teslenko
 *	
 */
public class CommandShowAllPatients extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	/**
	 * Returned path depends on role, Admin or Doctor.
	 * 
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		boolean showDischarged = false;
		if(request.getParameter("showDischarged") != null) {
			try {
				showDischarged = Boolean.valueOf(request.getParameter("showDischarged"));
			} catch (IllegalArgumentException e) {
				throw new AppException("command.incorrectParameter", e);
			}
		}
		
		Sort sort =chooseSort(request);
		int limit = setLimit(request);
		int offset = setOffset(request);
		
		DaoCommon dao = new DaoCommon();
		List<Patient> patientsList = dao.getAllPatients(sort, limit, offset, showDischarged);
		
		log.trace("patients list -> " + patientsList);
		request.setAttribute("showDischarged", request.getParameter("showDischarged"));
		request.setAttribute("patientsList", patientsList);
		request.setAttribute("sort", sort);
		request.setAttribute("limit", limit);
		request.setAttribute("offset", offset);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("authorizedUser");
		if(user.getRole().equals(Role.ADMIN)) {
			return Path.PATIENTS_LIST_ADMIN;
		} else {
			return Path.PATIENTS_LIST_DOCTOR;
		}
		
	}
	
	/**
	 * Building sort parameter, if null or incorrect - the default is used.
	 * @param request
	 * @return
	 * @throws AppException
	 */
	public Sort chooseSort(HttpServletRequest request) throws AppException {
		String requestSort = request.getParameter("sort");
		Set<String> patientsSort = new HashSet<>();
		patientsSort.add(Sort.lastName.toString());
		patientsSort.add(Sort.firstName.toString());
		patientsSort.add(Sort.dateOfBirth.toString());
		Sort sort = Sort.lastName;

		if (requestSort != null && patientsSort.contains(requestSort)) {
			try {
				sort = Sort.valueOf(requestSort);
			} catch (IllegalArgumentException e) {
				log.trace("Illegal sort parameter");
				throw new AppException("command.incorrectParameter");
			}
		}

		return sort;
	}
	
	/**
	 * Sets a limit of rows from DB to read.
	 * @param request
	 * @return
	 */
	public int setLimit(HttpServletRequest request) {
		if (request.getParameter("limit") == null || Integer.parseInt(request.getParameter("limit")) < 0) {
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
	public int setOffset(HttpServletRequest request) {
		if (request.getParameter("offset") == null || Integer.parseInt(request.getParameter("offset")) < 0) {
			return SortConstants.OFFSET;
		} else {
			return Integer.parseInt(request.getParameter("offset"));
		}
	}
}
