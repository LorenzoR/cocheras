package org.torraca.garage.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistentObject implements Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;

	public PersistentObject() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
