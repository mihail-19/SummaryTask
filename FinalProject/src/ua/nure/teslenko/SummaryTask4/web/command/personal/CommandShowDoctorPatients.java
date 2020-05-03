package ua.nure.teslenko.SummaryTask4.web.command.personal;

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
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.dao.personal.DaoPersonalSelect;
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Command to show doctor's patients.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandShowDoctorPatients extends Command{
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	
	/**
	 * Shows a list of doctor`s patients
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		
		Sort sort = chooseSort(request);
		int limit = setLimit(request);
		int offset = setOffset(request);
		HttpSession session = request.getSession();
		User doc = (User)session.getAttribute("authorizedUser");
		if(doc == null) {
			log.error("not authorized doctor");
			throw new AppException("command.notAuthorizedDoctor");
		}
		DaoPersonalSelect dao = new DaoPersonalSelect();
		List<Patient> patientsList = dao.getDoctorPatients(doc.getId(), sort, limit, offset);
		request.setAttribute("patientsList", patientsList);
		request.setAttribute("sort", sort);
		request.setAttribute("limit", limit);
		request.setAttribute("offset", offset);
		request.setAttribute("command", request.getParameter("command"));
		return Path.PATIENTS_LIST_DOCTOR;
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
