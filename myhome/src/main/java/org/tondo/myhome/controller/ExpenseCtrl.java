package org.tondo.myhome.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.tondo.myhome.enumsvc.EnumNames;
import org.tondo.myhome.enumsvc.EnumSvc;
import org.tondo.myhome.presentation.dropdown.DropdownListCreator;
import org.tondo.myhome.presentation.dropdown.DropdownValue;
import org.tondo.myhome.service.ExpenseDO;
import org.tondo.myhome.service.ExpenseSvc;

@Controller
@RequestMapping("/expense")
@SessionAttributes("expenseForm")
public class ExpenseCtrl {
	
	@Autowired
	private ExpenseSvc expenseService;
	
	@Autowired
	private EnumSvc enumService;
	
	@ModelAttribute("expenseForm")
	public ExpenseDO getDefaultFormContent() {
		ExpenseDO formDefault = new ExpenseDO();
		formDefault.setDate(new Date());
		formDefault.setAmount(BigDecimal.valueOf(200));
		System.out.println("forme default called ==========");
		return formDefault;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(Model model) {
		
		// values to combobox for expense type
		DropdownListCreator<String> dpCreator = new DropdownListCreator<>(DropdownListCreator.STRING_KEY);
		List<DropdownValue<String>> cbExpenseTypeValues = dpCreator
			.addItems(enumService.getEnumValues(EnumNames.EXPENSES))
			.values();
		model.addAttribute("cbExpenseType", cbExpenseTypeValues);
		
		// populate list
		List<ExpenseDO> expenses = expenseService.getExpenses();
		model.addAttribute("expenses", expenses);

		return "expense";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String addExpense(@ModelAttribute("expenseForm") ExpenseDO expense, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			expenseService.save(expense);
			expense.setNote(null);
		}
		return "redirect:/expense/";
	}
	
	@RequestMapping(value="/{expenseId}", method = RequestMethod.DELETE)
	public String deleteExpense(@PathVariable Long expenseId) {
		this.expenseService.delete(expenseId);
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
}
