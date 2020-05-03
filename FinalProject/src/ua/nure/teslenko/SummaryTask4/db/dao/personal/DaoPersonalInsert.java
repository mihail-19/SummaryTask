package ua.nure.teslenko.SummaryTask4.db.dao.personal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.DBManager;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
/**
 * Doctor methods for DB, insert and update.
 * @author Mykhailo Teslenko
 *
 */
public class DaoPersonalInsert {
	private DBManager dbManager;
	static Logger log = LogManager.getLogger();
	public DaoPersonalInsert() throws AppException {
		dbManager = DBManager.getInstance();
	}
	
	public static final String GET_ALL_PATIENTS = "SELECT id, login, roleID, firstName, lastName, dateOfBirth"
			+ " FROM users WHERE roleID=" + Role.PATIENT.ordinal() + " ORDER BY ^ LIMIT ? OFFSET ?";
	public static final String ADD_PRESCRIPTION = "INSERT INTO prescription (prescType, description, completionStatus, hospitalCard_id)"
			+ " VALUES (?, ?, ?, (SELECT id FROM hospitalCards WHERE ?=patient_id))";
	public static final String ADD_DIAGNOSE = "UPDATE hospitalCards SET diagnose=? WHERE patient_id=?";
	public static final String DISCHARGE = "UPDATE patients SET isActive=false WHERE id=?";
	public static final String DELETE_PATIENT_DOCTORS = "DELETE FROM patients_doctors WHERE patient_id=?";
	public static final String GET_PATIENT_DOCTORS_ID = "SELECT doctor_id FROM patients_doctors WHERE patient_id=?";
	public static final String DECR_PAT_NUM_BY_ID = "UPDATE doctors_params SET numberOfPatients=numberOfPatients-1 WHERE doctor_id=?";
	public static final String EXECUTE_PRESCRIPTION = "UPDATE prescription SET completionStatus=false WHERE id=?";
	public static final String REMOVE_PRESCRIPTION = "DELETE FROM prescription WHERE id=?";
	
	/**
	 * Removing prescription by id
	 * @param prescId
	 * @throws AppException
	 */
	public void removePrescription(int prescId) throws AppException {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement statement = conn.prepareStatement(REMOVE_PRESCRIPTION);
			statement.setInt(1, prescId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.removePrescription", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * Execute prescription by id
	 * @param prescId
	 * @throws AppException
	 */
	public void executePrescription(int prescId) throws AppException {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement statement = conn.prepareStatement(EXECUTE_PRESCRIPTION);
			statement.setInt(1, prescId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.removePrescription", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * Add prescription for patient by login.
	 * Completion status for new prescription is true.
	 * @param userLogin
	 * @param prescType
	 * @param desription
	 * @param completionStatus true for new prescription (it is active).
	 * @throws AppException
	 */
	public void addPrescription(int patientId, String prescType, String desription) throws AppException {
		log.debug("adding a prescription");
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement statement = conn.prepareStatement(ADD_PRESCRIPTION);
			statement.setString(1, prescType);
			statement.setString(2, desription);
			statement.setBoolean(3, true);
			statement.setInt(4, patientId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.addPrescription", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * Update diagnose for user
	 * @param userLogin
	 * @param diagnose
	 * @throws AppException
	 */
	public void addDiagnose(int patientId, String diagnose) throws AppException {
		log.debug("adding giagnose");
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement statement = conn.prepareStatement(ADD_DIAGNOSE);
			statement.setString(1, diagnose);
			statement.setInt(2, patientId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.addDiagnose", e);
		} finally {
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * Discharge user. Remove all doctors for the user.
	 * @param userLogin
	 * @throws AppException
	 */
	public void discharge(int patientId) throws AppException {
		log.debug("discharging patient");
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement(GET_PATIENT_DOCTORS_ID);
			statement.setInt(1, patientId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				log.trace("decrementing number of patients");
				statement = conn.prepareStatement(DECR_PAT_NUM_BY_ID);
				statement.setInt(1, rs.getInt(1));
				statement.execute();
			}
			statement = conn.prepareStatement(DELETE_PATIENT_DOCTORS);
			statement.setInt(1, patientId);
			statement.executeUpdate();
			statement = conn.prepareStatement(DISCHARGE);
			statement.setInt(1, patientId);
			statement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
				log.trace("roolback from discharging");
			DBManager.rollback(conn);
			e.printStackTrace();
			throw new AppException("db.discharge", e);
		} finally {
			DBManager.closeConn(conn);
			
		}
	}
}
