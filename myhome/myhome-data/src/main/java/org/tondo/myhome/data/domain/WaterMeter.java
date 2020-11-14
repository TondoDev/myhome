package org.tondo.myhome.data.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.tondo.myhome.data.LocalDateDbConverter;

@Entity
public class WaterMeter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String meterIdentifier;
	
	
	@Column(nullable = false)
	@Convert(converter = LocalDateDbConverter.class)
	private LocalDate validFrom;
	
	@Column
	@Convert(converter = LocalDateDbConverter.class)
	private LocalDate validTo;
	
	@Column(nullable = false)
	private Boolean active;

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

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
