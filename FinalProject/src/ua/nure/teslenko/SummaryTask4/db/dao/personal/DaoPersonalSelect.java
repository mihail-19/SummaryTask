package ua.nure.teslenko.SummaryTask4.db.dao.personal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.teslenko.SummaryTask4.db.DBManager;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.dao.DaoCommon;
import ua.nure.teslenko.SummaryTask4.db.entity.HospitalCard;
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
/**
 * Doctor methods for DB, select.
 * @author  Mykhailo Teslenko
 *
 */
public class DaoPersonalSelect {
	static Logger log = LogManager.getLogger();
	
	public static final String GET_ALL_PATIENTS = "SELECT id, login, roleID, firstName, lastName, dateOfBirth"
			+ " FROM users WHERE roleID=" + Role.PATIENT.ordinal() + " ORDER BY ^ LIMIT ? OFFSET ?";
	public static final String GET_DOCTOR_PATIENTS = "SELECT patients.id, firstName, lastName, dateOfBirth, isActive FROM patients"
			+ " INNER JOIN patients_doctors ON patient_id=patients.id WHERE doctor_id=? ORDER BY ^ LIMIT ? OFFSET ?";
	public static final String GET_PATIENT_DOCTORS_ID = "SELECT doctor_id FROM patients_doctors "
			+ "WHERE patient_id=(SELECT id FROM users WHERE login=?)";
	
	/**
	 * Select all patients as users (no hospital card).
	 * @param sort
	 * @param limit
	 * @param offset
	 * @return
	 * @throws AppException
	 */
	public List<User> getAllPatients(Sort sort, int limit, int offset) throws AppException {
		List<User> patientsList = new ArrayList<>();
		Connection conn  = null;
		try {
			conn = DBManager.getInstance().getConnection();
			String st = GET_ALL_PATIENTS.replaceAll("\\^", sort.toString());
			PreparedStatement statement = conn.prepareStatement(st);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				patientsList.add(DaoCommon.fillUser(new User(), rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getAllPatients", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return patientsList;
	}
	
	/**
	 * Select doctor patients only as users (no hospital card).
	 * @param docId
	 * @param sort
	 * @param limit
	 * @param offset
	 * @return
	 * @throws AppException
	 */
	public List<Patient> getDoctorPatients(int docId, Sort sort, int limit, int offset) throws AppException {
		List<Patient> patientsList = new ArrayList<>();
		DaoCommon dao = new DaoCommon();
		Connection conn  = null;
		try {
			conn = DBManager.getInstance().getConnection();
			String st = GET_DOCTOR_PATIENTS.replaceAll("\\^", sort.toString());
			PreparedStatement statement = conn.prepareStatement(st);
			statement.setInt(1, docId);
			statement.setInt(2, limit);
			statement.setInt(3, offset);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Patient patient = new Patient();
				patient.setId(rs.getInt(1));
				patient.setFirstName(rs.getString(2));
				patient.setLastName(rs.getString(3));
				patient.setDateOfBirth(rs.getDate(4));
				patient.setIsActive(rs.getBoolean(5));
				HospitalCard card =  dao.getHospitalCard(patient.getId());
				patient.setHospitalCard(card);
				patientsList.add(patient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getDoctorPatients", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return patientsList;
	}
}

	
