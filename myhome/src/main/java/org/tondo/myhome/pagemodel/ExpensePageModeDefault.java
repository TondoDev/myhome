package org.tondo.myhome.pagemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.ui.Model;
import org.tondo.myhome.dto.ExpenseDO;
import org.tondo.myhome.dto.ExpenseSummaryDO;
import org.tondo.myhome.enumsvc.EnumValue;
import org.tondo.myhome.presentation.dropdown.DropdownListCreator;
import org.tondo.myhome.presentation.dropdown.DropdownValue;

public class ExpensePageModeDefault {
	private Model model;
	private Supplier<List<EnumValue>> cbTypesSupplier;
	private Supplier<List<ExpenseDO>> dataSupplier;
	private Supplier<List<ExpenseSummaryDO>> summarySupplier;;

	public ExpensePageModeDefault(Model model) {
		this.model = model;
	}
	
	public ExpensePageModeDefault dataSupplier(Supplier<List<ExpenseDO>> data) {
		this.dataSupplier = data;
		return this;
	}
	
	// for deffered call, because this list is not always required
	public ExpensePageModeDefault typesSupplier(Supplier<List<EnumValue>> typesSupplier) {
		this.cbTypesSupplier = typesSupplier;
		return this;
	}
	
	public ExpensePageModeDefault summarySupplier(Supplier<List<ExpenseSummaryDO>> summarySupplier) {
		this.summarySupplier = summarySupplier;
		return this;
	}
	
	
	public Model apply() {
		validate();
		
		DropdownListCreator<String> dpCreator = new DropdownListCreator<>(DropdownListCreator.STRING_KEY);
		List<DropdownValue<String>> cbExpenseTypeValues = dpCreator
				.addItems(this.cbTypesSupplier.get()).values();
		model.addAttribute("cbExpenseType", cbExpenseTypeValues);

		if (!model.containsAttribute("expenseForm")) {
			model.addAttribute("expenseForm", getDefaultFormContent());
		}
		
		model.addAttribute("inputEnabled", true);
		model.addAttribute("deleteEnabled", true);
		
		// populate list
		model.addAttribute("expenses", this.dataSupplier.get());
		model.addAttribute("summary", this.summarySupplier.get());
		
		return this.model;
	}
	
	protected void validate() {
		
	}
	
	private ExpenseDO getDefaultFormContent() {
		ExpenseDO formDefault = new ExpenseDO();
		formDefault.setDate(new Date());
		formDefault.setAmount(BigDecimal.valueOf(20));
		return formDefault;
	}
}
