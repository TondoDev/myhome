package org.tondo.myhome.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tondo.myhome.domain.Expense;
import org.tondo.myhome.enumsvc.EnumNames;
import org.tondo.myhome.enumsvc.EnumSvc;
import org.tondo.myhome.presentation.ViewDataObject;
import org.tondo.myhome.presentation.dropdown.DropdownListCreator;
import org.tondo.myhome.presentation.dropdown.DropdownValue;
import org.tondo.myhome.service.ExpenseDO;
import org.tondo.myhome.service.ExpenseSvc;

@Controller
@RequestMapping("/expense")
public class ExpenseCtrl {
	
	@Autowired
	private ExpenseSvc expenseService;
	
	@Autowired
	private EnumSvc enumService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(Model model) {
		
		// values to combobox for expense type
		DropdownListCreator<String> dpCreator = new DropdownListCreator<>(DropdownListCreator.STRING_KEY);
		List<DropdownValue<String>> cbExpenseTypeValues = dpCreator
			.addItems(enumService.getEnumValues(EnumNames.EXPENSES))
			.values();
		model.addAttribute("cbExpenseType", cbExpenseTypeValues);
		
		// populate list
		// List<ExpenseDO> expenses = expenseService.getExpenses();
		// model.addAttribute("expenses", resolveExpenses(expenses));

		// defaults to form
		ExpenseDO formDefault = new ExpenseDO();
		formDefault.setDate(new Date());
		formDefault.setAmount(BigDecimal.valueOf(15));
		model.addAttribute("expense", formDefault);
		return "expense";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String addExpense(@ModelAttribute("expense") ExpenseDO expense, BindingResult bindingResult, Model model) {
		expenseService.save(expense);
		model.addAttribute("expense", expense);
		return "redirect:/expense/";
	}
	
	// is calling 
	@InitBinder
	public void paramBinding(WebDataBinder binder) {
		System.out.println("-- init binder");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(dateFormat, true));
	}
	
	private List<ViewDataObject<Expense>> resolveExpenses(List<Expense> expenses) {
		List<ViewDataObject<Expense>> retVal = new ArrayList<>();
		
		for (Expense exp : expenses) {
			Map<String, String> labels = new HashMap<>();
			labels.put(exp.getExpenseType(), enumService.resolve(exp.getExpenseType(), EnumNames.EXPENSES));
			retVal.add(new ViewDataObject<Expense>(exp, labels));
		}
		
		return retVal;
	}
	
}
