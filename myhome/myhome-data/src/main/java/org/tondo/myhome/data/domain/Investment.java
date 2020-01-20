package org.tondo.myhome.data.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.tondo.myhome.data.LocalDateDbConverter;

@Entity
@Inheritance(strategy =  InheritanceType.JOINED)
//@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Investment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Convert(converter = LocalDateDbConverter.class)
	private LocalDate establishingDate;
	
	@Column
	@Convert(converter = LocalDateDbConverter.class)
	private LocalDate endDate;
	
	@Column
	private String dayOfPay;
	
	@Column
	private Double amountOfPay;
	
	@Column
	private Integer paymentRecurrence;
	
	//private String type;
	
	// percent 0 - 1
	@Column(nullable = false)
	private Double fee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getEstablishingDate() {
		return establishingDate;
	}

	public void setEstablishingDate(LocalDate establishingDate) {
		this.establishingDate = establishingDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getDayOfPay() {
		return dayOfPay;
	}

	public void setDayOfPay(String dayOfPay) {
		this.dayOfPay = dayOfPay;
	}

	public Double getAmountOfPay() {
		return amountOfPay;
	}

	public void setAmountOfPay(Double amountOfPay) {
		this.amountOfPay = amountOfPay;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	
	public Integer getPaymentRecurrence() {
		return paymentRecurrence;
	}
	
	public void setPaymentRecurrence(Integer paymentRecurrence) {
		this.paymentRecurrence = paymentRecurrence;
	}
}
