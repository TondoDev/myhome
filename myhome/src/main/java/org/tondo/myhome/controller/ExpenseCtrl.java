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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tondo.myhome.domain.Expense;
import org.tondo.myhome.service.ExpenseSvc;

@Controller
@RequestMapping("/expense")
public class ExpenseCtrl {
	
	@Autowired
	private ExpenseSvc expenseService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(Model model) {
		List<Expense> expenses = expenseService.getExpenses();
		String note = expenses.get(0).getNote();
		model.addAttribute("expenses", expenses);
		model.addAttribute("name", note);
		// for store form input
		Expense formDefault = new Expense();
		formDefault.setDate(new Date());
		formDefault.setAmount(BigDecimal.valueOf(15));
		model.addAttribute("expense", formDefault);
		return "index";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String addExpense(@ModelAttribute("expense") Expense expense, BindingResult bindingResult) {
		expenseService.save(expense);
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
