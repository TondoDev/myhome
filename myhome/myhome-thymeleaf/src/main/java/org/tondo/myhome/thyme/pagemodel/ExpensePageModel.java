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
	private int month;
	private int year;
	private Supplier<List<EnumValue>> cbTypesSupplier;
	private Supplier<List<ExpenseDO>> dataSupplier;
	private Supplier<List<ExpenseSummaryDO>> summarySupplier;;
	private String target;
	
	
	public ExpensePageModel(Model model, int month, int year) {
		this.model = model;
		this.inputEnabled = true;
		this.month = month;
		this.year = year;
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
	
		// populate list
		model.addAttribute("expenses", this.dataSupplier.get());
		model.addAttribute("summary", this.summarySupplier.get());
		// properties
		model.addAttribute("inputEnabled", this.inputEnabled);
		model.addAttribute("target", this.target);
		model.addAttribute("deleteEnabled", this.inputEnabled);
		return this.model;
	}
	
	protected void validate() {
		
	}
	
	private List<DropdownValue<Integer>> createDaysCombo() {
		LocalDate current = LocalDate.now();
		YearMonth examinedMonth = YearMonth.of(this.year, this.month);
		int minDay = 1;
		int maxDay;
		if(examinedMonth.isBefore(YearMonth.from(current))) {
			maxDay = examinedMonth.lengthOfMonth();
		} else {
			maxDay = current.getDayOfMonth();
		}
		
		DropdownListCreator<Integer> cbDays = new DropdownListCreator<>(DropdownListCreator.INTEGER_KEY);
		cbDays.addItems(IntStream.rangeClosed(minDay, maxDay).boxed().toArray(size -> new Integer[size]));
		return cbDays.values();
	}
	
	private ExpenseInDayDO getDefaultFormContent() {
		ExpenseInDayDO formDefault = new ExpenseInDayDO();
		LocalDate now = LocalDate.now();
		formDefault.setDate(now);
		formDefault.setDay(now.getDayOfMonth());
		// TODO configurable
		formDefault.setAmount(BigDecimal.valueOf(20));
		return formDefault;
	}
}
