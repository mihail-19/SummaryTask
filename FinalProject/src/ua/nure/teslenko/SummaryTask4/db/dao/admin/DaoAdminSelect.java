package ua.nure.teslenko.SummaryTask4.db.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.DBManager;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.entity.Category;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
/**
 * Admin methods for DB, select.
 * @author Mykhailo Teslenko
 *
 */
public class DaoAdminSelect {
	static Logger log = LogManager.getLogger();
	private DBManager dbManager;
	public DaoAdminSelect() throws AppException {
		dbManager = DBManager.getInstance();
	}
	
	public static final String GET_USERS_LIST = "SELECT id, login, roleID, firstName, lastName, dateOfBirth FROM users"
			+ " ORDER BY ^ LIMIT ?  OFFSET ?";
	
	public static final String GET_DOCTORS_LIST = "SELECT users.id, login, roleID, firstName, lastName, dateOfBirth, category, numberOfPatients FROM users"
			+ " INNER JOIN doctors_params ON doctor_id=users.id WHERE roleID=? ORDER BY ^ LIMIT ?  OFFSET ?";
	public static final String GET_LOGINS_LIST = "SELECT login FROM users";
	public static final String GET_PATS_ACTVITY = "SELECT user_id, isActive FROM hospitalcards";
	
	public static final String GET_USER_DOCTORS_ID = "SELECT doctor_id FROM patients_doctors WHERE patient_id=?";
	public static final String GET_USER_DOCTORS = "SELECT users.id, login, roleID, firstName, lastName, dateOfBirth, category, numberOfPatients"
			+ " FROM users INNER JOIN doctors_params ON doctor_id=users.id WHERE users.id=?";
	
	/**
	 * Select all doctor for patient
	 * @param patientId patient's id
	 * @return
	 * @throws AppException
	 */
	public List<Doctor> getPatientDoctors(int patientId) throws AppException {
		log.debug("getting user doctors from DB");
		List<Doctor> doctorsList = new ArrayList<>();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(GET_USER_DOCTORS_ID);
			statement.setInt(1, patientId);
			ResultSet rsId = statement.executeQuery();
			statement = conn.prepareStatement(GET_USER_DOCTORS);
			
			while(rsId.next()) {
				statement.setInt(1, rsId.getInt(1));
				ResultSet rs = statement.executeQuery();
				if(rs.next()) {
					Doctor doctor = (Doctor) DaoCommon.fillUser(new Doctor(), rs);
					doctor.setCategory(Category.values()[rs.getInt(7)]);
					doctor.setNumberOfPatients(rs.getInt(8));
					doctorsList.add(doctor);
				}
			}
			log.trace("received doctors list for patient -> " + doctorsList);	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getDoctorsList", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return doctorsList;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Select all doctors
	 * @param limit
	 * @param offset
	 * @param sort
	 * @return List of Doctor
	 * @throws AppException
	 */
	public List<Doctor> getDoctorsList(int limit, int offset, Sort sort) throws AppException {
		log.debug("getting doctors list from DB");
		List<Doctor> doctorsList = new ArrayList<>();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			String temp = GET_DOCTORS_LIST.replaceAll("\\^", sort.toString());
			PreparedStatement statement = conn.prepareStatement(temp);
			statement.setInt(1, Role.DOCTOR.ordinal());
			statement.setInt(2, limit);
			statement.setInt(3, offset);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Doctor doctor = (Doctor) DaoCommon.fillUser(new Doctor(), rs);
				doctor.setCategory(Category.values()[rs.getInt(7)]);
				doctor.setNumberOfPatients(rs.getInt(8));
				doctorsList.add(doctor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getDoctorsList", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return doctorsList;
	}

	/**
	 * Select all users. By default, discharged patients are hidden.
	 * @param limit
	 * @param offset
	 * @param sort
	 * @param showDischarged
	 * @return all users
	 * @throws AppException
	 */
	public List<User> getUsersList(int limit, int offset, Sort sort) throws AppException {
		List<User> usersList = new ArrayList<>();
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			String temp = GET_USERS_LIST.replaceAll("\\^", sort.toString());
			PreparedStatement statement = conn.prepareStatement(temp);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
					usersList.add(DaoCommon.fillUser(new User(), rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getUsersList", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return usersList;
	}

	/**
	 * Select list of all logins to test new login
	 * @return
	 * @throws AppException
	 */
	public List<String> getLoginsList() throws AppException {
		log.trace("getting logins list");
		List<String> loginsList = new ArrayList<>();
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(GET_LOGINS_LIST);
			while(rs.next()) {
				loginsList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getLoginsList", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return loginsList;
	}
	
	
}
