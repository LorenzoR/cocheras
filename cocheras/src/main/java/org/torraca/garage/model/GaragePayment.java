package org.torraca.garage.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GaragePayment extends PersistentObject implements
		Serializable {

	@Basic
	@Column(nullable = false)
	private Boolean withBill;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Employee.class)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee registeredBy;

	public GaragePayment() {
	}

	public Boolean getWithBill() {
		return withBill;
	}

	public void setWithBill(Boolean withBill) {
		this.withBill = withBill;
	}

	public Employee getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(Employee registeredBy) {
		this.registeredBy = registeredBy;
	}

}
