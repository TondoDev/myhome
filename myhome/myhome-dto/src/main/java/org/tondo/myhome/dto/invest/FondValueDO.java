package org.tondo.myhome.dto.invest;

public class FondValueDO {

	private Double totalFees;
	private Double totalBuyPrice;
	private Double ownedUnits;
	private Double totalFondValue;
	// clean profit with fees
	private Double profit;
	// fees not considered, only fond buy price
	private Double actualFondProfit;
	private Double unitPrice;
	
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
}
