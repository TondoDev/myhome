package org.tondo.myhome.thyme.pagemodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.ui.Model;
import org.tondo.myhome.dto.ExpenseSummaryDO;
import org.tondo.myhome.dto.ExpenseYearSummaryDO;

public class YearSummaryPageModel {

	private int year;
	private Integer previousYear;
	private Integer nextYear;
	private ExpenseYearSummaryDO data;
	private LocalDate today;
	
	public YearSummaryPageModel(int year, LocalDate today) {
		this.year = year;
		this.today = today;
	}
	
	public YearSummaryPageModel data(ExpenseYearSummaryDO summary) {
		this.data = summary;
		return this;
	}
	
	public YearSummaryPageModel previousYear(Integer py) {
		this.previousYear = py;
		return this;
	}
	
	public YearSummaryPageModel nextYear(Integer ny) {
		this.nextYear = ny;
		return this;
	}
	
	public void apply(Model model) {
		model.addAttribute("previousYear", this.previousYear);
		model.addAttribute("nextYear", this.nextYear);
		model.addAttribute("yearSummary", this.data.getYearSummary());
		model.addAttribute("monthSummary", this.data.getMonthSummary());
		model.addAttribute("clickFlags", calculateClickableFlags(this.data.getMonthSummary()));
		model.addAttribute("year", this.year);
		// if displayed year is not current year, then current year value is passed
		model.addAttribute("currentYear", this.year == this.today.getYear() ? null : this.today.getYear());
	}
	
	
	private boolean[] calculateClickableFlags(List<List<ExpenseSummaryDO>> monthSummaries) {
		
		if (monthSummaries.size() != 12) {
			throw new IllegalArgumentException("Provided summaries must contain stats for 12 months!");
		}
		
		boolean[] retVal = new boolean[12];
		
		for (int i = 0; i < 12; i++) {
			// expects that records was passed through processing,
			// where TOTAL entry was added to list
			retVal[i] = BigDecimal.ONE.compareTo(findTotalValue(monthSummaries.get(i))) < 0;
		}
		
		return retVal;
	}
	
	private BigDecimal findTotalValue(List<ExpenseSummaryDO> monthEntries) {
		int size = monthEntries.size();
		ExpenseSummaryDO totalSum = monthEntries.get(size - 1);
		if (!"TOTAL".equals(totalSum.getExpenseType())) {
			throw new IllegalStateException("TOTAL is not last entry in list!");
		}
		
		return totalSum.getSum();
	}
	
	
}
