package org.tondo.myhome.service;

import java.util.ArrayList;
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
	
	public void save(ExpenseDO expenseDo) {
		Expense expense = this.fromDataObject(expenseDo);
		expenseRepo.save(expense);
	}
	
	
	public Expense fromDataObject(ExpenseDO data) {
		Expense retVal = new Expense();
		retVal.setId(data.getId());
		retVal.setAmount(data.getAmount());
		retVal.setDate(data.getDate());
		retVal.setExpenseType(data.getExpenseType());
		retVal.setNote(data.getNote());
		
		return retVal;
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
