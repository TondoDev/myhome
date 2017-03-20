package org.tondo.myhome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		model.addAttribute("expense", new Expense());
		return "index";
	}
	
	@RequestMapping
	public String addExpense(@ModelAttribute("expense") Expense expense) {
		System.out.println(expense.getAmount());
		return "index";
	}
}
