package org.torraca.garage.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SalaryAdvance extends PersistentObject implements Serializable {

	@Temporal(TemporalType.DATE)
	private Calendar date;
	
	@Basic
	private Employee employee;
	
	@Basic
	private Double amount;
}
