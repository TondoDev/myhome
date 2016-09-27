package org.tondo.myhome.experiment.domain;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private BigDecimal aproximateCost;
	private Calendar cookDate;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getAproximateCost() {
		return aproximateCost;
	}
	
	public void setAproximateCost(BigDecimal aproximateCost) {
		this.aproximateCost = aproximateCost;
	}
	
	public Calendar getCookDate() {
		return cookDate;
	}
	
	public void setCookDate(Calendar cookDate) {
		this.cookDate = cookDate;
	}
}
