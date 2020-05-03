package ua.nure.teslenko.SummaryTask4.db.entity;


import ua.nure.teslenko.SummaryTask4.db.Role;

/**
 * Doctor entity. Has a category and nuber of patients.
 * @author Mykhailo Teslenko
 *
 */
public class Doctor extends User {
	
	private static final long serialVersionUID = 1L;
	private Category category;
	private int numberOfPatients;
	public Doctor() {}
	public Doctor(User user) {
			setLogin(user.getLogin());
			setFirstName(user.getFirstName());
			setLastName(user.getLastName());
			setDateOfBirth(user.getDateOfBirth());
			setId(user.getId());
			setRole(Role.DOCTOR);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + numberOfPatients;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		if (category != other.category)
			return false;
		if (numberOfPatients != other.numberOfPatients)
			return false;
		return true;
	}
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
	
	@Override
	public String toString() {
		return super.toString() + "Doctor [category=" + category + ", numberOfPatients=" + numberOfPatients + "]";
	}
	


}
