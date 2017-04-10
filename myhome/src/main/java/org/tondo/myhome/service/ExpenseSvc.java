package org.tondo.myhome.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.domain.Expense;
import org.tondo.myhome.repo.ExpenseRepository;

@Service
public class ExpenseSvc {

	@Autowired
	private ExpenseRepository expenseRepo;
	
	public List<Expense> getExpenses() {
		return iterableToList(expenseRepo.findAll());
	}
	
	public void save(Expense expense) {
		if (expense.getDate() == null) {
			expense.setDate(new Date());
		}
		expenseRepo.save(expense);
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> retval = new ArrayList<>();
		Iterator<T> iter = iterable.iterator();
		while(iter.hasNext()) {
			retval.add(iter.next());
		}
		return retval;
	}
}
