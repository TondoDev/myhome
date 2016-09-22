package org.tondo.myhome.experiment.domain;

import java.math.BigDecimal;
import java.util.Calendar;

public class Food {

	private String name;
	private BigDecimal aproximateCost;
	private Calendar cookDate;
	
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
