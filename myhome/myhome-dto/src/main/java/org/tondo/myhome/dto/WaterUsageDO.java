package org.tondo.myhome.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class WaterUsageDO {

	private Long id;
	
	@NotNull(message = "is.not.filled")
	private LocalDate measured;
	
	@NotNull
	private Double coldUsage;
	
	@NotNull
	private Double warmUsage;
	
	private int numberOfDays;
	
	private double diffCold;
	
	private double diffWarm;
	
	private double warmPerDay;
	
	private double coldPerDay;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getMeasured() {
		return measured;
	}

	public void setMeasured(LocalDate measured) {
		this.measured = measured;
	}

	public Double getColdUsage() {
		return coldUsage;
	}

	public void setColdUsage(Double coldUsage) {
		this.coldUsage = coldUsage;
	}

	public Double getWarmUsage() {
		return warmUsage;
	}

	public void setWarmUsage(Double warmUsage) {
		this.warmUsage = warmUsage;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public double getDiffCold() {
		return diffCold;
	}

	public void setDiffCold(double diffCold) {
		this.diffCold = diffCold;
	}

	public double getDiffWarm() {
		return diffWarm;
	}

	public void setDiffWarm(double diffWarm) {
		this.diffWarm = diffWarm;
	}

	public double getWarmPerDay() {
		return warmPerDay;
	}

	public void setWarmPerDay(double warmPerDay) {
		this.warmPerDay = warmPerDay;
	}

	public double getColdPerDay() {
		return coldPerDay;
	}

	public void setColdPerDay(double coldPerDay) {
		this.coldPerDay = coldPerDay;
	}
}
