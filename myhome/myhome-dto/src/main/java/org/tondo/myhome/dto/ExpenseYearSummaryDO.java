package org.tondo.myhome.dto;

import java.util.List;

public class ExpenseYearSummaryDO {

	private List<ExpenseSummaryDO> yearSummary;
	private List<List<ExpenseSummaryDO>> monthSummary;
	
	
	public List<ExpenseSummaryDO> getYearSummary() {
		return yearSummary;
	}
	
	public List<List<ExpenseSummaryDO>> getMonthSummary() {
		return monthSummary;
	}
	
	public void setYearSummary(List<ExpenseSummaryDO> yearSummary) {
		this.yearSummary = yearSummary;
	}
	
	public void setMonthSummary(List<List<ExpenseSummaryDO>> monthSummary) {
		this.monthSummary = monthSummary;
	}
}
