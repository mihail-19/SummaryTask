package ua.nure.teslenko.SummaryTask4.db.dao.admin;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.DBManager;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.db.entity.DoctorParams;
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
/**
 * Admin methods for DB, insert and update
 * @author Mykhailo Teslenko
 *
 */
public class DaoAdminInsert {
	private DBManager dbManager;
	static Logger log = LogManager.getLogger();
	public DaoAdminInsert() throws AppException {
		dbManager = DBManager.getInstance();
	}
	
	public static final String ADD_USER = "INSERT INTO users (login, firstName, lastName, dateOfBirth, roleID) "
			+ "VALUES (?, ?, ?, ?, ?)";
	public static final String ADD_USER_PWD = "INSERT INTO users_autorization (user_id, password) VALUES (?, ?)";
	public static final String ADD_CARD = "INSERT INTO hospitalCards (patient_id) VALUES (?)";
	public static final String ADD_DOCTOR_PARAMS = "INSERT INTO doctors_params (doctor_id, category, numberOfPatients)"
			+ " VALUES (?, ?, ?)";
	public static final String ADD_USER_DOCTOR = "INSERT INTO patients_doctors (patient_id, doctor_id)"
			+ " VALUES (?, ?)";
	public static final String REMOVE_USER_DOCTOR = "DELETE FROM patients_doctors WHERE"
			+ " patient_id=? AND doctor_id=?";
	public static final String REMOVE_USER = "DELETE FROM users WHERE id=?";
	public static final String INCR_PAT_NUM = "UPDATE doctors_params SET numberOfPatients=numberOfPatients+1"
			+ " WHERE doctor_id=?";
	public static final String DECR_PAT_NUM = "UPDATE doctors_params SET numberOfPatients=numberOfPatients-1"
			+ " WHERE doctor_id=?";
	public static final String ADD_PATIENT = "INSERT INTO patients (firstName, lastName, dateOfBirth, isActive)"
			+ " VALUES (?, ?, ?, ?)";
	public static final String REMOVE_PATIENT = "DELETE FROM patients WHERE id=?";
	
	
	/**
	 * Insert new patient into DB
	 * @param patient
	 * @throws AppException
	 */
	public void addPatient(Patient patient) throws AppException {
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(ADD_PATIENT, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, patient.getFirstName());
			ps.setString(2, patient.getLastName());
			ps.setDate(3, (Date)patient.getDateOfBirth());
			ps.setBoolean(4, true);
			ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			patient.setId(keys.getInt(1));
			
			ps = conn.prepareStatement(ADD_CARD);
			ps.setInt(1, patient.getId());
			ps.executeUpdate();
			conn.commit();
			
		}catch (SQLException e) {
			DBManager.rollback(conn);
			e.printStackTrace();
			throw new AppException("db.addUser", e);
		} finally {
				DBManager.closeConn(conn);
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Insert new user into users table and password into users_autorization.
	 * Two transactions are performed.
	 * @param user
	 * @param password
	 * @return user`s id
	 * @throws AppException
	 */
	public int addUser(User user, String password) throws AppException {
		Connection conn  = null;
		int id = -1;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			ps.setDate(4, (Date)user.getDateOfBirth());
			ps.setInt(5, user.getRole().ordinal());
			ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);
			PreparedStatement psSetPwd = conn.prepareStatement(ADD_USER_PWD);
			psSetPwd.setInt(1, id);
			psSetPwd.setString(2, password);
			psSetPwd.executeUpdate();
			
			conn.commit();
			
			//add patient`s card
			
			if(user.getRole().equals(Role.PATIENT)) {
				addHospitalCard(keys.getInt(1));
			}
			
		}catch (SQLException e) {
			DBManager.rollback(conn);
			throw new AppException("db.addUser", e);
		} finally {
				DBManager.closeConn(conn);
		}
		return id;
	}
	
	
	/**
	 * Insert new hospital card for user id.
	 * @param userId
	 * @throws AppException
	 */
	public void addHospitalCard(int userId) throws AppException {
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement ps = conn.prepareStatement(ADD_CARD);
			ps.setBoolean(1, true);
			ps.setInt(2, userId);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.addHospitalCard", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * Adding doctor parameters
	 * initial nuberOfPatients is 0.
	 * @param id
	 * @param category
	 * @throws AppException 
	 */
	public void addDoctorParams(DoctorParams doctorParams) throws AppException {
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement ps = conn.prepareStatement(ADD_DOCTOR_PARAMS);
			ps.setInt(1, doctorParams.getDoctorId());
			ps.setInt(2, doctorParams.getCategory().ordinal());
			ps.setInt(3, 0);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.addDoctorParams", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * Appoint a doctor for user.
	 * After that, increment nuber of patients for the doctor.
	 * @param patientLogin
	 * @param doctorLogin
	 * @throws AppException
	 */
	public void addUserDoctor(int patientId, int doctorId) throws AppException {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(ADD_USER_DOCTOR);
			statement.setInt(1, patientId);
			statement.setInt(2, doctorId);
			statement.executeUpdate();
			PreparedStatement incremPatNum = conn.prepareStatement(INCR_PAT_NUM);
			incremPatNum.setInt(1, doctorId);
			incremPatNum.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			log.trace("add user doctor failed, trying to rollback");
			DBManager.rollback(conn);
			throw new AppException("db.addUserDoctor", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}

	/**
	 * Removing the attachment of doctor for the user.
	 * After that, decrementing the number of patients.
	 * @param userLogin
	 * @param doctorLogin
	 * @throws AppException
	 */
	public void removeUserDoctor(int patientId, int doctorId) throws AppException {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(REMOVE_USER_DOCTOR);
			statement.setInt(1, patientId);
			statement.setInt(2, doctorId);
			statement.executeUpdate();
			PreparedStatement decrPatNum = conn.prepareStatement(DECR_PAT_NUM);
			decrPatNum.setInt(1, doctorId);
			decrPatNum.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			log.trace("remove user doctor failed, trying to rollback");
			DBManager.rollback(conn);
			throw new AppException("db.removeUserDoctor", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}

	/**
	 * Removing user by login.
	 * Decrementing the number of patients for all attached doctors.
	 * @param userLogin
	 * @param doctors - user`s doctors
	 * @throws AppException
	 */
	public void removeUser(int userId) throws AppException {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement statement = conn.prepareStatement(REMOVE_USER);
			statement.setInt(1, userId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.removeUser", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * Removing patient from DB
	 * @param patientId
	 * @param patientDoctors
	 * @throws AppException
	 */
	public void removePatient(int patientId, List<Doctor> patientDoctors) throws AppException {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(DECR_PAT_NUM);
			for(Doctor doc : patientDoctors) {
				statement.setInt(1, doc.getId());
				statement.executeUpdate();
			}
			statement = conn.prepareStatement(REMOVE_PATIENT);
			statement.setInt(1, patientId);
			statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			log.trace("Failed to remove patient");
			DBManager.rollback(conn);
			e.printStackTrace();
			throw new AppException("db.removeUser", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}

	
}
