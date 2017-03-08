package org.tondo.myhome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tondo.myhome.service.ExpenseSvc;

@Controller
@RequestMapping("/expense")
public class ExpenseCtrl {
	
	@Autowired
	private ExpenseSvc expenseService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(Model mode) {
		String note = expenseService.getExpenses().get(0).getNote();
		mode.addAttribute("name", note);
		return "index";
	}
}
