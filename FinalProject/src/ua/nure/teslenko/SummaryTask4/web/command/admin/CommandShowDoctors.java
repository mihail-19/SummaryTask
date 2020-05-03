package ua.nure.teslenko.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.Path;
import ua.nure.teslenko.SummaryTask4.SortConstants;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.dao.admin.DaoAdminSelect;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
import ua.nure.teslenko.SummaryTask4.web.command.Command;

/**
 * Shows doctors in context of chosing doctor for patient.
 * @author Mykhailo Teslenko
 *	
 */
public class CommandShowDoctors extends Command {
	static Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		log.trace("command started");
		try {
			int patientId = Integer.parseInt(request.getParameter("patientId"));
			DaoAdminSelect adminSelect = new DaoAdminSelect();
			DaoCommon daoCommon = new DaoCommon();
			Sort sort = chooseSort(request);
			int limit = setLimit(request);
			int offset = setOffset(request);
			log.debug("Sorting parameters -> Sort by: " + sort + ", limit: " + limit + ", offset: " + offset);
			List<Doctor> doctorsList = adminSelect.getDoctorsList(limit, offset, sort);
			request.setAttribute("doctorsList", doctorsList);
			request.setAttribute("patientId", patientId);
			request.setAttribute("sort", sort);
			request.setAttribute("limit", limit);
			request.setAttribute("offset", offset);
			request.setAttribute("disabledDoctors", daoCommon.getUserDoctors(patientId));
			return Path.DOCTORS_LIST;
		} catch (IllegalArgumentException | NullPointerException e) {
			e.printStackTrace();
			throw new AppException("command.incorrectParameter", e);
		}
	}
	
	/**
	 * Building sort parameter, if null or incorrect - the default is used.
	 */
	public Sort chooseSort(HttpServletRequest request) {
		Sort sort = Sort.lastName;
		for (Sort s : Sort.values()) {
			if (s.toString().equals(request.getParameter("sort"))) {
				sort = Sort.valueOf(request.getParameter("sort"));
				log.trace("sort parameter for doctors list -> "  + sort);
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
