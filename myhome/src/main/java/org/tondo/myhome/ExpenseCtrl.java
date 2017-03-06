package org.tondo.myhome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.tondo.myhome.service.ExpenseSvc;

@Controller
public class ExpenseCtrl {

	@Autowired
	private ExpenseSvc expenseSvc;
	
}
