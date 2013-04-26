package org.torraca.garage.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Expense extends PersistentObject implements Serializable {
	
	@Temporal(TemporalType.DATE)
	private Calendar date;
	
	@Basic
	private Double amount;
	
	@Basic
	private String comment;
	
	public Expense() {
	}

	public Expense(Calendar date, Double amount, String comment) {
		super();
		this.date = date;
		this.amount = amount;
		this.comment = comment;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
