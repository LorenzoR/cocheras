package org.torraca.garage.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Garage extends PersistentObject implements Serializable {

	@Basic
	@Column(nullable = false) 
	private Long number;
	
	@Basic
	@Column(nullable = false) 
	private Double price;
	
	@Basic
	@Column(nullable = false) 
	private Integer floor;
	
	@Basic
	@Column(nullable = false) 
	private Character side;

	public Garage() {
	}
	
	public Garage(Long number, Double price, Integer floor, Character side) {
		super();
		this.number = number;
		this.price = price;
		this.floor = floor;
		this.side = side;
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

	public Character getSide() {
		return side;
	}

	public void setSide(Character side) {
		this.side = side;
	}

	public String toString() {
		return "Cochera " + this.number;
	}
	
	
}
