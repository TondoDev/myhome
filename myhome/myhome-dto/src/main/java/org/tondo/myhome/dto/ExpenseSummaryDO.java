package org.tondo.myhome.dto;

import java.math.BigDecimal;

public class ExpenseSummaryDO {

	private String expenseType;
	private String expenseTypeLabel;
	private BigDecimal sum;
	
	public String getExpenseType() {
		return expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	
	public BigDecimal getSum() {
		return sum;
	}
	
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	
	
	public String getExpenseTypeLabel() {
		return expenseTypeLabel;
	}
	
	public void setExpenseTypeLabel(String expenseTypeLabel) {
		this.expenseTypeLabel = expenseTypeLabel;
	}
}
