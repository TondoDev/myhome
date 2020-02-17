package org.tondo.myhome.dto.invest;

import java.time.LocalDate;

public class FondPaymentDO {
	
	private Long id;
	
	private LocalDate dateOfPruchase;
	
	private Double buyPrice;
	
	private Double unitPrice;
	
	private Double fee;
	
	private Long fondId;
	
	private Double purchasedUnits;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateOfPruchase() {
		return dateOfPruchase;
	}

	public void setDateOfPruchase(LocalDate dateOfPruchase) {
		this.dateOfPruchase = dateOfPruchase;
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

	public Double getFeeAmount() {
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
