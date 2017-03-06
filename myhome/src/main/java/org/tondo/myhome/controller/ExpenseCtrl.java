package org.tondo.myhome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tondo.myhome.domain.Expense;
import org.tondo.myhome.service.ExpenseSvc;

@Controller
@RequestMapping("/exepnse")
public class ExpenseCtrl {
	
	@Autowired
	private ExpenseSvc expenseService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Expense> findAll() {
		return expenseService.getExpenses();
	}
}
