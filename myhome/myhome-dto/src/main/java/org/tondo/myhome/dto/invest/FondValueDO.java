package org.tondo.myhome.dto.invest;

import java.time.LocalDate;

public class FondValueDO {
	
	// technical fond identificator
	private Long fondId;
	// human readable fond identification
	private String fondName;
	// sum of all fees
	private Double totalFees;
	// sum of all money used to buy fond units
	private Double totalBuyPrice;
	// sum of all money which investor used for investing into this fond
	private Double totalInvest;
	// units owned - for each payment determined by current unit price and buyPrices
	private Double ownedUnits;
	// given unit price * owned units
	private Double totalFondValue;
	// clean profit with fees
	// fondValue - sum of money payed by investor
	private Double profit;
	// fees not considered, only fond buy price
	// fondValue - sum of money used to buy fond units
	private Double actualFondProfit;
	// current unit price against are these numbers calculated
	private Double unitPrice;
	// date to which is unitPrice valid
	private LocalDate valueDate;
	
	
	public Long getFondId() {
		return fondId;
	}
	
	public void setFondId(Long fondId) {
		this.fondId = fondId;
	}
	
	public String getFondName() {
		return fondName;
	}
	
	public void setFondName(String fondName) {
		this.fondName = fondName;
	}
	
	public Double getTotalFees() {
		return totalFees;
	}
	
	public void setTotalFees(Double totalFees) {
		this.totalFees = totalFees;
	}
	
	public Double getTotalBuyPrice() {
		return totalBuyPrice;
	}
	
	public void setTotalBuyPrice(Double totalBuyPrice) {
		this.totalBuyPrice = totalBuyPrice;
	}
	
	public Double getOwnedUnits() {
		return ownedUnits;
	}
	public void setOwnedUnits(Double ownedUnits) {
		this.ownedUnits = ownedUnits;
	}
	
	public Double getTotalFondValue() {
		return totalFondValue;
	}
	
	public void setTotalFondValue(Double totalFondValue) {
		this.totalFondValue = totalFondValue;
	}
	
	public Double getProfit() {
		return profit;
	}
	
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
	public Double getActualFondProfit() {
		return actualFondProfit;
	}
	
	public void setActualFondProfit(Double actualFondProfit) {
		this.actualFondProfit = actualFondProfit;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getTotalInvest() {
		return totalInvest;
	}
	
	public void setTotalInvest(Double totalInvest) {
		this.totalInvest = totalInvest;
	}
	
	public LocalDate getValueDate() {
		return valueDate;
	}
	
	public void setValueDate(LocalDate valueDate) {
		this.valueDate = valueDate;
	}
}
