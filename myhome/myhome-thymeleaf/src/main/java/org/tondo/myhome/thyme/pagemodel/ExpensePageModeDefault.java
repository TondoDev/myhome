package org.tondo.myhome.thyme.pagemodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.ui.Model;
import org.tondo.myhome.dto.ExpenseDO;
import org.tondo.myhome.dto.ExpenseSummaryDO;
import org.tondo.myhome.svc.data.PageResult;
import org.tondo.myhome.svc.enumsvc.EnumValue;
import org.tondo.myhome.thyme.presentation.dropdown.DropdownListCreator;
import org.tondo.myhome.thyme.presentation.dropdown.DropdownValue;

public class ExpensePageModeDefault {
	private Model model;
	private Supplier<List<EnumValue>> cbTypesSupplier;
	private Supplier<PageResult<ExpenseDO>> dataSupplier;
	private Supplier<List<ExpenseSummaryDO>> summarySupplier;;

	public ExpensePageModeDefault(Model model) {
		this.model = model;
	}
	
	public ExpensePageModeDefault dataSupplier(Supplier<PageResult<ExpenseDO>> data) {
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
		PageResult<ExpenseDO> pageRrsult = this.dataSupplier.get();
		PageSelect pagingButtons = new PageSelect(pageRrsult, 5);
		model.addAttribute("paging", pagingButtons);
		model.addAttribute("expenses", pageRrsult.getData());
		model.addAttribute("summary", this.summarySupplier.get());
		
		return this.model;
	}
	
	protected void validate() {
		
	}
	
	private ExpenseDO getDefaultFormContent() {
		ExpenseDO formDefault = new ExpenseDO();
		formDefault.setDate(LocalDate.now());
		formDefault.setAmount(BigDecimal.valueOf(20));
		return formDefault;
	}
}
