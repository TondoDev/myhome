package org.tondo.myhome.service;

import java.math.BigDecimal;

public class ExpenseSummaryDO {

	private String expenseType;
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
}
