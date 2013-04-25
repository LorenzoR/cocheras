package org.torraca.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class MonthlyGaragePayment extends GaragePayment implements Serializable {

	public static enum PAYMENT_TYPE { BACKWARD, FORWARD, PRESENT };
	public static enum PAYMENT_TYPE2 { CASH, CHECK }
	
	private Integer month;
	private Double money;
	private Garage garage;
	
	public MonthlyGaragePayment() {
		
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
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
	
}
