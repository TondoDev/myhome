package org.tondo.myhome.dto.invest;

import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class FondPaymentDO {
	
	private Long id;
	
	@NotNull
	private LocalDate dateOfPurchase;
	
	// amount of money used to buy units
	@NotNull
	@DecimalMin("0.001")
	private Double buyPrice;
	
	// jsut for display purposes
	private Double unitPrice;
	
	// amount of fees
	@DecimalMin("0.0")
	private Double fee;
	
	private Long fondId;
	
	// number of purchased units from buy price
	@NotNull
	@DecimalMin("0.0001")
	private Double purchasedUnits;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(LocalDate dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Long getFondId() {
		return fondId;
	}

	public void setFondId(Long fondId) {
		this.fondId = fondId;
	}

	public Double getPurchasedUnits() {
		return purchasedUnits;
	}

	public void setPurchasedUnits(Double purchasedUnits) {
		this.purchasedUnits = purchasedUnits;
	}
}
