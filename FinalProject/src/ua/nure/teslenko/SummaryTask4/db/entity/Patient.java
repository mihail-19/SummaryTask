package ua.nure.teslenko.SummaryTask4.db.entity;

import java.util.List;

/**
 * Patient, it doesn't have a login.
 * isActive indicates whether patient is not discharged. 
 * @author Mykhailo Teslenko
 *
 */
public class Patient extends Person{
	private static final long serialVersionUID = 1L;
	private HospitalCard hospitalCard;
	private boolean isActive;
	private List<Doctor> userDoctors;
	public Patient() {}
	public Patient(User user) {
		setFirstName(user.getFirstName());
		setLastName(user.getLastName());
		setDateOfBirth(user.getDateOfBirth());
		setId(user.getId());
	}
	public List<Doctor> getUserDoctors() {
		return userDoctors;
	}

	public void setUserDoctors(List<Doctor> userDoctors) {
		this.userDoctors = userDoctors;
	}

	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public HospitalCard getHospitalCard() {
		return hospitalCard;
	}

	public void setHospitalCard(HospitalCard hospitalCard) {
		this.hospitalCard = hospitalCard;
	}
	@Override
	public String toString() {
		return super.toString() + " Patient [hospitalCard=" + hospitalCard + ", userDoctors=" 
				+ userDoctors + ", activity " + isActive + "]";
	}
	
}
