package org.tondo.myhome.data.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.tondo.myhome.data.LocalDateDbConverter;

@Entity
public class WaterUsage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, columnDefinition="DATE")
	@Convert(converter = LocalDateDbConverter.class)
	private LocalDate measured;
	
	// in m3
	@Column(nullable = false)
	private Double coldUsage;
	// in m3
	@Column(nullable = false)
	private Double warmUsage;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private WaterMeter waterMeter;
	
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
	
	public WaterMeter getWaterMeter() {
		return waterMeter;
	}
	
	public void setWaterMeter(WaterMeter waterMeter) {
		this.waterMeter = waterMeter;
	}
}
