package org.tondo.myhome.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.domain.Expense;
import org.tondo.myhome.enumsvc.EnumNames;
import org.tondo.myhome.enumsvc.EnumSvc;
import org.tondo.myhome.repo.ExpenseRepository;

@Service
public class ExpenseSvc {

	@Autowired
	private ExpenseRepository expenseRepo;
	
	@Autowired
	private EnumSvc enumSvc;
	
	
	public List<ExpenseDO> getExpenses() {
		return iterableToDataObjectList(expenseRepo.findAll());
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
	
	public ExpenseDO toDataObject(Expense data) {
		ExpenseDO retVal = new ExpenseDO();
		retVal.setId(data.getId());
		retVal.setAmount(data.getAmount());
		retVal.setDate(data.getDate());
		retVal.setExpenseType(data.getExpenseType());
		retVal.setNote(data.getNote());
		retVal.setExpenseTypeLabel(enumSvc.resolve(data.getExpenseType(), EnumNames.EXPENSES));
		
		return retVal;
	}
	
	private List<ExpenseDO> iterableToDataObjectList(Iterable<Expense> iterable) {
		List<ExpenseDO> retval = new ArrayList<>();
		Iterator<Expense> iter = iterable.iterator();
		while(iter.hasNext()) {
			retval.add(toDataObject(iter.next()));
		}
		return retval;
	}
}
