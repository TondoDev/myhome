package org.tondo.myhome.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class WaterMeterDO {
	private Long id;
	
	
	@NotNull
	private String meterIdentifier;
	
	@NotNull
	private LocalDate validFrom;
	
	private LocalDate validTo;
	
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeterIdentifier() {
		return meterIdentifier;
	}

	public void setMeterIdentifier(String meterIdentifier) {
		this.meterIdentifier = meterIdentifier;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
