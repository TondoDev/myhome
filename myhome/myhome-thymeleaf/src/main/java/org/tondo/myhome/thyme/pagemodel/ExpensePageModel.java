package org.tondo.myhome.thyme.pagemodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.springframework.ui.Model;
import org.tondo.myhome.dto.ExpenseDO;
import org.tondo.myhome.dto.ExpenseInDayDO;
import org.tondo.myhome.dto.ExpenseSummaryDO;
import org.tondo.myhome.svc.enumsvc.EnumValue;
import org.tondo.myhome.thyme.presentation.dropdown.DropdownListCreator;
import org.tondo.myhome.thyme.presentation.dropdown.DropdownValue;

public class ExpensePageModel {

	private Model model;
	private boolean inputEnabled;
	private LocalDate today;
	private YearMonth examinedMonth;
	private Supplier<List<EnumValue>> cbTypesSupplier;
	private Supplier<List<ExpenseDO>> dataSupplier;
	private Supplier<List<ExpenseSummaryDO>> summarySupplier;;
	private String target;
	private YearMonth nextMonth;
	private YearMonth previousMonth;
	
	
	public ExpensePageModel(Model model, YearMonth examinedDate, LocalDate today) {
		this.model = model;
		this.inputEnabled = true;
		this.examinedMonth = examinedDate;
		this.today = today;
	}
	
	public ExpensePageModel dataSupplier(Supplier<List<ExpenseDO>> data) {
		this.dataSupplier = data;
		return this;
	}
	
	// for deffered call, because this list is not always required
	public ExpensePageModel typesSupplier(Supplier<List<EnumValue>> typesSupplier) {
		this.cbTypesSupplier = typesSupplier;
		return this;
	}
	
	public ExpensePageModel summarySupplier(Supplier<List<ExpenseSummaryDO>> summarySupplier) {
		this.summarySupplier = summarySupplier;
		return this;
	}
	
	public ExpensePageModel inputEnabled(boolean enabled) {
		this.inputEnabled = enabled;
		return this;
	}
	
	public ExpensePageModel target(String target) {
		this.target = target;
		return this;
	}
	
	public ExpensePageModel nextMonth(YearMonth next) {
		this.nextMonth = next;
		return this;
	}
	
	public ExpensePageModel previousMonth(YearMonth prev) {
		this.previousMonth = prev;
		return this;
	}
	
	public Model apply() {
 		validate();
		
		if (this.inputEnabled) {
			model.addAttribute("cbDays", createDaysCombo());
			
			DropdownListCreator<String> dpCreator = new DropdownListCreator<>(DropdownListCreator.STRING_KEY);
			List<DropdownValue<String>> cbExpenseTypeValues = dpCreator
					.addItems(this.cbTypesSupplier.get()).values();
			model.addAttribute("cbExpenseType", cbExpenseTypeValues);

			if (!model.containsAttribute("expenseForm")) {
				model.addAttribute("expenseForm", getDefaultFormContent());
			}
		}
		
		model.addAttribute("displayDate", LocalDate.of(this.examinedMonth.getYear(), this.examinedMonth.getMonthValue(), 1/* day doesn't matter*/));
		
		// navigation
		model.addAttribute("prevMonth", this.previousMonth == null ? null
				: LocalDate.of(this.previousMonth.getYear(), this.previousMonth.getMonth(), 1));
		model.addAttribute("nextMonth", this.nextMonth == null ? null 
				: LocalDate.of(this.nextMonth.getYear(), this.nextMonth.getMonth(), 1));
		
		// populate list
		model.addAttribute("expenses", this.dataSupplier.get());
		model.addAttribute("summary", this.summarySupplier.get());
		// properties
		model.addAttribute("inputEnabled", this.inputEnabled);
		model.addAttribute("isCurrent", YearMonth.from(this.today).equals(this.examinedMonth));
		model.addAttribute("target", this.target);
		model.addAttribute("deleteEnabled", this.inputEnabled);
		return this.model;
	}
	
	protected void validate() {
		// TODO
	}
	
	private List<DropdownValue<Integer>> createDaysCombo() {
		int minDay = 1;
		int maxDay;
		if(this.examinedMonth.isBefore(YearMonth.from(this.today))) {
			maxDay = this.examinedMonth.lengthOfMonth();
		} else {
			maxDay = this.today.getDayOfMonth();
		}
		
		DropdownListCreator<Integer> cbDays = new DropdownListCreator<>(DropdownListCreator.INTEGER_KEY);
		cbDays.addItems(IntStream.rangeClosed(minDay, maxDay).boxed().toArray(size -> new Integer[size]));
		return cbDays.values();
	}
	
	private ExpenseInDayDO getDefaultFormContent() {
		ExpenseInDayDO formDefault = new ExpenseInDayDO();
		formDefault.setDate(this.today);
		formDefault.setDay(this.today.getDayOfMonth());
		// TODO configurable
		formDefault.setAmount(BigDecimal.valueOf(20));
		return formDefault;
	}
}
