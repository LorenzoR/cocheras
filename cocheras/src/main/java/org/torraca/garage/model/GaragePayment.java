package org.torraca.garage.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GaragePayment extends PersistentObject implements
		Serializable {

	@Basic
	private Boolean withBill;

	public GaragePayment() {
	}

	public Boolean getWithBill() {
		return withBill;
	}

	public void setWithBill(Boolean withBill) {
		this.withBill = withBill;
	}

}
