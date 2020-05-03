package ua.nure.teslenko.SummaryTask4.db.entity;
/**
 * Doctor's category and number of patients.
 * @author Mykhailo Teslenko
 *
 */
public class DoctorParams extends Entity{
	
	private static final long serialVersionUID = 1L;
	
	private Category category;
	private int numberOfPatients;
	private int doctorId;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getNumberOfPatients() {
		return numberOfPatients;
	}
	public void setNumberOfPatients(int numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	@Override
	public String toString() {
		return "[DoctorId = " + doctorId + ". Nuber of patients = " + numberOfPatients + "]"; 
	}
	
}
