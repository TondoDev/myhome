package org.tondo.myhome.dto.invest;

import java.time.LocalDate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public abstract class InvestmentBaseDO {

	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String dayOfPay;
	
	@NotNull
	@DecimalMin("0.001")
	
	private Double amountOfPay;
	
	private Integer paymentRecurrence;
	
	@NotNull
	@DecimalMax("1.0")
	@DecimalMin("0.0")
	private Double feePct;

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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
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

	public Integer getPaymentRecurrence() {
		return paymentRecurrence;
	}

	public void setPaymentRecurrence(Integer paymentRecurrence) {
		this.paymentRecurrence = paymentRecurrence;
	}
	
	public Double getFeePct() {
		return feePct;
	}
	
	public void setFeePct(Double fee) {
		this.feePct = fee;
	}
}
