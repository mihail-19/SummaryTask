package ua.nure.teslenko.SummaryTask4.db.entity;


import ua.nure.teslenko.SummaryTask4.db.Role;

public class User extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private Role role;
	public String getLogin() {
		return login;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String toString() {
		return super.toString() + "[User " + " login:" + ", role " + role +"]" ;
	}
}
