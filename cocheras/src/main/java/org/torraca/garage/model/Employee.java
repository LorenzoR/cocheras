package org.torraca.garage.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Employee extends PersistentObject implements Serializable {
	
	@Basic
	@Column(nullable = false) 
	private String firstname;
	
	@Basic
	@Column(nullable = false) 
	private String lastname;
	
	public Employee() {
	}

	public Employee(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String toString() {
		return lastname + ", " + firstname;
	}
	
}
