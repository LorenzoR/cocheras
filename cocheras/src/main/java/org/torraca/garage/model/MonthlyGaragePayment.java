package org.torraca.garage.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MonthlyGaragePayment extends GaragePayment implements Serializable {

	public static enum PaymentType {
		BACKWARD, FORWARD, PRESENT
	};

	public static enum PaymentMethod {
		CASH, CHECK
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Calendar date;

	@Basic
	@Column(nullable = false)
	private Double money;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Garage.class)
	@JoinColumn(name = "garage_id", nullable = false)
	private Garage garage;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Client.class)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentType paymentType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethod paymentMethod;

	@Basic
	@Column(nullable = false)
	private Integer qtyMonths;

	public MonthlyGaragePayment() {

	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Garage getGarage() {
		return garage;
	}

	public void setGarage(Garage garage) {
		this.garage = garage;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getQtyMonths() {
		return qtyMonths;
	}

	public void setQtyMonths(Integer qtyMonths) {
		this.qtyMonths = qtyMonths;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String toString() {
		return "Pago cochera " + garage + " del " + date.getTime();
	}

}
