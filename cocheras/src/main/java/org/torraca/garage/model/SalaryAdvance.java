package org.torraca.garage.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SalaryAdvance extends PersistentObject implements Serializable {

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Calendar date;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Employee.class)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@Basic
	@Column(nullable = false)
	private Double amount;

	public SalaryAdvance() {

	}

	public SalaryAdvance(Calendar date, Employee employee, Double amount) {
		super();
		this.date = date;
		this.employee = employee;
		this.amount = amount;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
