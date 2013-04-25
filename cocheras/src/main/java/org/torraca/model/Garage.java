package org.torraca.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Garage extends PersistentObject implements Serializable {

	@Basic
	private Long number;
	
	@Basic
	private Double price;
	
	@Basic
	private Integer floor;

	public Garage() {
	}
	
	public Garage(Long number, Double price, Integer floor) {
		super();
		this.number = number;
		this.price = price;
		this.floor = floor;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String toString() {
		return "Cochera " + this.number;
	}
	
	
}
