package org.torraca.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DailyGaragePayment extends GaragePayment implements Serializable {

//	@Id 
//	@GeneratedValue(strategy = GenerationType.AUTO) 
//	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Calendar date;
	
	@Basic
	private Long qtyByHour;
	
	@Basic
	private Double amountByHour;
	
	@Basic
	private Long qtyBy8Hours;
	@Basic
	private Double amountBy8Hours;
	
	@Basic
	private Long qtyBy24Hours;
	@Basic
	private Double amountBy24Hours;
	
	public DailyGaragePayment() {
		super();
	}
	
	public DailyGaragePayment (Calendar date) {
		super();
		this.date = date;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Long getQtyByHour() {
		return qtyByHour;
	}

	public void setQtyByHour(Long qtyByHour) {
		this.qtyByHour = qtyByHour;
	}

	public Double getAmountByHour() {
		return amountByHour;
	}

	public void setAmountByHour(Double amountByHour) {
		this.amountByHour = amountByHour;
	}

	public Long getQtyBy8Hours() {
		return qtyBy8Hours;
	}

	public void setQtyBy8Hours(Long qtyBy8Hours) {
		this.qtyBy8Hours = qtyBy8Hours;
	}

	public Double getAmountBy8Hours() {
		return amountBy8Hours;
	}

	public void setAmountBy8Hours(Double amountBy8Hours) {
		this.amountBy8Hours = amountBy8Hours;
	}

	public Long getQtyBy24Hours() {
		return qtyBy24Hours;
	}

	public void setQtyBy24Hours(Long qtyBy24Hous) {
		this.qtyBy24Hours = qtyBy24Hous;
	}

	public Double getAmountBy24Hours() {
		return amountBy24Hours;
	}

	public void setAmountBy24Hours(Double amountBy24Hours) {
		this.amountBy24Hours = amountBy24Hours;
	}
	
}
