package ua.nure.teslenko.SummaryTask4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.teslenko.SummaryTask4.db.DBManager;
import ua.nure.teslenko.SummaryTask4.db.Role;
import ua.nure.teslenko.SummaryTask4.db.Sort;
import ua.nure.teslenko.SummaryTask4.db.entity.Category;
import ua.nure.teslenko.SummaryTask4.db.entity.Doctor;
import ua.nure.teslenko.SummaryTask4.db.entity.DoctorParams;
import ua.nure.teslenko.SummaryTask4.db.entity.HospitalCard;
import ua.nure.teslenko.SummaryTask4.db.entity.Patient;
import ua.nure.teslenko.SummaryTask4.db.entity.PrescriptionType;
import ua.nure.teslenko.SummaryTask4.db.entity.User;
import ua.nure.teslenko.SummaryTask4.exception.AppException;
/**
 * Common methods for working with DB
 * @author Mykhailo Teslenko
 *
 */
public class DaoCommon {
	private DBManager dbManager;
	public DaoCommon() throws AppException {
		dbManager = DBManager.getInstance();
	}
	public static final String GET_DOCTOR_PARAMS = "SELECT category, numberOfPatients FROM doctors_params WHERE doctor_id=?";
	public static final String AUTHORIZE_USER = "SELECT id, login, roleID, firstName, lastName, dateOfBirth, password FROM users "
			+ "INNER JOIN users_autorization ON user_id=id WHERE login=?";
	public static final String GET_USER = "SELECT id, login, roleID, firstName, lastName, dateOfBirth FROM users WHERE login=?";
	public static final String GET_USER_DOCTORS = " SELECT users.id, login, firstName, lastName, dateOfBirth, category, numberOfPatients FROM users" + 
			" INNER JOIN doctors_params ON doctor_id=users.id" +
			" INNER JOIN patients_doctors" + 
			" ON patients_doctors.doctor_id=users.id" + 
			" WHERE patients_doctors.patient_id=?";
	public static final String GET_USER_BY_ROLE = "SELECT id, login, roleID, firstName, lastName, dateOfBirth"
			+ " FROM users WHERE roleID = ? ORDER BY ^ LIMIT ?  OFFSET ?";
	public static final String GET_DOCTORS_PARAMS_LIST = "SELECT doctor_id, category, numberOfPatients FROM doctors_params";
	
	public static final String GET_ALL_PATIENTS = "SELECT id, firstName, lastName, dateOfBirth, isActive"
			+ " FROM patients ORDER BY ^ LIMIT ? OFFSET ?";
	public static final String GET_CARD = "SELECT diagnose, prescType, description, completionStatus, prescription.id"
			+ " FROM hospitalCards" 
			+ " INNER JOIN prescription ON hospitalCard_id=hospitalCards.id WHERE hospitalCards.patient_id=?";
	public static final String GET_PATIENT = "SELECT firstName, lastName, dateOfBirth, isActive"
			+ " FROM patients WHERE id=?";
	
	/**
	 * Select all patients 
	 * @param sort
	 * @param limit
	 * @param offset
	 * @param showDischarged
	 * @return
	 * @throws AppException
	 */
	public List<Patient> getAllPatients(Sort sort, int limit, int offset, boolean showDischarged) throws AppException {
		List<Patient> patientsList = new ArrayList<>();
		Connection conn  = null;
		try {
			conn = DBManager.getInstance().getConnection();
			conn.setAutoCommit(true);
			String st = GET_ALL_PATIENTS.replaceAll("\\^", sort.toString());
			PreparedStatement statement = conn.prepareStatement(st);
			statement.setInt(1, limit);
			statement.setInt(2, offset);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (showDischarged || rs.getBoolean(5)) {
					Patient patient = new Patient();
					patient.setId(rs.getInt(1));
					patient.setFirstName(rs.getString(2));
					patient.setLastName(rs.getString(3));
					patient.setDateOfBirth(rs.getDate(4));
					patient.setIsActive(rs.getBoolean(5));
					HospitalCard card =  getHospitalCard(patient.getId());
					patient.setHospitalCard(card);
					patientsList.add(patient);
				}
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
	 * Select hospital card from DB
	 * @param patientId
	 * @return
	 * @throws AppException
	 */
	public HospitalCard getHospitalCard(int patientId) throws AppException {
		HospitalCard card = new HospitalCard();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement statement = conn.prepareStatement(GET_CARD);
			statement.setInt(1, patientId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				card.setDiagnose(rs.getString(1));
				if(rs.getString(2) != null) {
					card.addPrescription(PrescriptionType.valueOf(rs.getString(2)),
							rs.getString(3), rs.getBoolean(4), rs.getInt(5));
				}
			}
			while(rs.next()) {
				card.addPrescription(PrescriptionType.valueOf(rs.getString(2)), 
						rs.getString(3), rs.getBoolean(4), rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getHospitalCard", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return card;
	}
	
	/**
	 * Select patient by id
	 * @param patientId
	 * @return
	 * @throws AppException
	 */
	public Patient getPatient(int patientId) throws AppException {
		Patient patient = null;
		Connection conn  = null;
		try {
			conn = DBManager.getInstance().getConnection();
			conn.setAutoCommit(true);
			PreparedStatement statement = conn.prepareStatement(GET_PATIENT);
			statement.setInt(1, patientId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
					patient = new Patient();
					patient.setId(patientId);
					patient.setFirstName(rs.getString(1));
					patient.setLastName(rs.getString(2));
					patient.setDateOfBirth(rs.getDate(3));
					patient.setIsActive(rs.getBoolean(4));
					HospitalCard card =  getHospitalCard(patient.getId());
					patient.setHospitalCard(card);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getAllPatients", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return patient;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Returns user from DB according to request parameters.
	 * Finds a user login in users table, then  
	 * selects a password from users_autorization table on user`s id.
	 * If user was not found, return null.
	 * @param login
	 * @param password
	 * @return
	 * @throws AppException
	 */
	public User authorizeUser(String login, String password) throws AppException {
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(AUTHORIZE_USER);
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			User user = new User();
			if (rs.next() && password.equals(rs.getString(7))) {
				user.setId(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setRole(Role.values()[rs.getInt(3)]);
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setDateOfBirth(rs.getDate(6));

				// If user was found and created

				return user;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("db.authorize", e);
		} finally {
			DBManager.closeConn(conn);
		}

		// return null if there are no user or password is incorrect

		return null;
	}
	
	
	
	
	/**
	 * Returns user from DB by login
	 * @param userLogin
	 * @return User
	 * @throws AppException
	 */
	public User getUser(String userLogin) throws AppException {
		User user = new User();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(GET_USER);
			statement.setString(1, userLogin);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				user = fillUser(new User(), rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getUser", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return user;
	}
	
	/**
	 * Method to get doctor`s category and number of patients.
	 * 
	 * @param doctorId
	 * @return
	 * @throws AppException
	 */
	public DoctorParams getDoctorParams(int doctorId) throws AppException {
		DoctorParams doctorParams = new DoctorParams();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(GET_DOCTOR_PARAMS);
			statement.setInt(1, doctorId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				doctorParams.setCategory(Category.values()[rs.getInt(1)]);
				doctorParams.setNumberOfPatients(rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getDoctorParams", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return doctorParams;
	}
	
	/**
	 * Method for getting the current user doctors by id.
	 * @param id user`s id
	 * @return doctor`s list attached to the user.
	 * @throws AppException
	 */
	public List<Doctor> getUserDoctors(int id) throws AppException {
		List<Doctor> doctorsList = new ArrayList<>();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(GET_USER_DOCTORS);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Doctor doctor = new Doctor();
				doctor.setId( rs.getInt(1));
				doctor.setLogin(rs.getString(2));
				doctor.setRole(Role.DOCTOR);
				doctor.setFirstName(rs.getString(3));
				doctor.setLastName(rs.getString(4));
				doctor.setDateOfBirth(rs.getDate(5));
				doctor.setCategory(Category.values()[rs.getInt(6)]);
				doctor.setNumberOfPatients(rs.getInt(7));
				doctorsList.add(doctor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getUserDoctors", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return doctorsList;
	}
	
	
	/**
	 * Method to get all users with chosen role.
	 * @param role
	 * @param limit
	 * @param offset
	 * @param sort
	 * @param showDischarged - if true, discharged patients will be shown
	 * @return list of users with same role
	 * @throws AppException
	 */
	public List<User> getUserByRole(Role role, int limit, int offset, Sort sort, boolean showDischarged) throws AppException{
		List<User> usersList = new ArrayList<>();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			String temp = GET_USER_BY_ROLE.replaceAll("\\^", sort.toString());
			PreparedStatement statement = conn.prepareStatement(temp);
			statement.setInt(1, role.ordinal());
			statement.setInt(2, limit);
			statement.setInt(3, offset);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
					usersList.add(fillUser(new User(), rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getUser", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return usersList;
		
	}
	
	/**
	 * Doctor parameters, category and number of patients
	 * for all doctors. Doctor id from users table is the key
	 * @return map, key - doctor`s id, value - doctor parameters [category, number of patients]
	 * @throws AppException
	 */
	public Map<Integer, DoctorParams> getDoctorsParamsMap() throws AppException {
		Map<Integer, DoctorParams> paramsMap = new HashMap<>();
		Connection conn  = null;
		try {
			conn = dbManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(GET_DOCTORS_PARAMS_LIST);
			while(rs.next()) {
				DoctorParams param = new DoctorParams();
				param.setDoctorId(rs.getInt(1));
				param.setCategory(Category.values()[rs.getInt(2)]);
				param.setNumberOfPatients(rs.getInt(3));
				paramsMap.put(param.getDoctorId(), param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("db.getDoctorsParamsMap", e);
		} finally {
			DBManager.closeConn(conn);
		}
		return paramsMap;
	}
	
	/**
	 * static method to create user using existing ResultSet
	 * @param user User to fill
	 * @param rs ResultSet for that user
	 * @return User
	 * @throws SQLException when parameters are empty
	 */
	public static User fillUser(User user, ResultSet rs) throws SQLException {
		user.setId(rs.getInt(1));
		user.setLogin(rs.getString(2));
		user.setRole(Role.values()[rs.getInt(3)]);
		user.setFirstName(rs.getString(4));
		user.setLastName(rs.getString(5));
		user.setDateOfBirth(rs.getDate(6));
		return user;
	}
	
}
