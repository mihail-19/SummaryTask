package ua.nure.teslenko.SummaryTask4.db.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * isActive field shows whether patient is active now or 
 * the diagnose is final and patient is discharged.
 * @author Mykhailo Teslenko
 *
 */
public class HospitalCard extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Prescription> prescriptions = new ArrayList<>(); 
	private String diagnose;
	private int patientId;
	public int getUserId() {
		return patientId;
	}

	public void setUserId(int userId) {
		this.patientId = userId;
	}
	/**
	 * completionStatus indicates whether prescription is completed (true) or not (false)
	 * @author Mykhailo Teslenko
	 *
	 */
	
	public static class Prescription extends Entity{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private PrescriptionType type;
		private String description;
		private boolean completionStatus;
		public int getExecutorId() {
			return executorId;
		}

		public void setExecutorId(int executorId) {
			this.executorId = executorId;
		}

		private int executorId;

		private Prescription(PrescriptionType type, String description, boolean completionStatus, int id) {
			this.type = type;
			this.description = description;
			this.completionStatus = completionStatus;
			setId(id);
		}
		
		public void complete() {
			this.completionStatus = true;
		}

		public PrescriptionType getType() {
			return type;
		}

		public void setType(PrescriptionType type) {
			this.type = type;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public boolean isCompletionStatus() {
			return completionStatus;
		}

		public void setCompletionStatus(boolean completionStatus) {
			this.completionStatus = completionStatus;
		}
		
		
	} 
	
	public String getDiagnose() {
		return diagnose;
	}

	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	
	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}
	public void addPrescription(PrescriptionType type, String description, boolean completionStatus, int id) {
		prescriptions.add(new Prescription(type, description, completionStatus, id));
		
	}

	@Override
	public String toString() {
		return "HospitalCard [prescriptions=" + prescriptions + ", diagnose=" + diagnose + ", " 
				+ ", patientId=" + patientId + "]";
	}
	
	

	
	

}
