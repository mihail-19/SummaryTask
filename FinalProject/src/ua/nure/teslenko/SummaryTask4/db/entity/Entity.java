package ua.nure.teslenko.SummaryTask4.db.entity;

import java.io.Serializable;
/**
 * Abstract class of entity for project.
 * @author Mykhailo Teslenko
 *
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
