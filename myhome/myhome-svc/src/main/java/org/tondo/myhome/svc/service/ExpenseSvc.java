package org.tondo.myhome.svc.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.data.domain.Expense;
import org.tondo.myhome.data.domain.ExpenseSummary;
import org.tondo.myhome.data.repo.ExpenseRepository;
import org.tondo.myhome.dto.ExpenseDO;
import org.tondo.myhome.dto.ExpenseSummaryDO;
import org.tondo.myhome.svc.enumsvc.EnumNames;
import org.tondo.myhome.svc.enumsvc.EnumSvc;
import org.tondo.myhome.svc.enumsvc.EnumValue;

@Service
public class ExpenseSvc {

	@Autowired
	private ExpenseRepository expenseRepo;
	
	@Autowired
	private EnumSvc enumSvc;
	
	
	public List<ExpenseDO> getExpenses(int month, int year) {
		return iterableToDataObjectList(expenseRepo.findForMonthOrderByDateDesc(month, year), this::toDataObject);
	}
	
	public List<ExpenseDO> getExpenses() {
		return iterableToDataObjectList(expenseRepo.findAllByOrderByDateDescIdDesc(), this::toDataObject);
	}
	
	public List<ExpenseSummaryDO> getTotalSummary() {
		List<ExpenseSummaryDO> categoryWithValue = iterableToDataObjectList(expenseRepo.totalSummary(), this::mapperExpenseSummary);
		return constructSummaryForAllCategories(categoryWithValue);
	}
	
	public List<ExpenseSummaryDO> getSummaryByMonth(int mont, int year) {
		List<ExpenseSummaryDO> categoriesWithValue = iterableToDataObjectList(expenseRepo.summaryByMonth(mont, year), this::mapperExpenseSummary);
		return constructSummaryForAllCategories(categoriesWithValue);
	}
	
	private List<ExpenseSummaryDO> constructSummaryForAllCategories(List<ExpenseSummaryDO> categoryWithValue) {
		List<ExpenseSummaryDO> retVal = new ArrayList<>();
		List<EnumValue> categories = this.enumSvc.getEnumValues(EnumNames.EXPENSES);
		BigDecimal total = BigDecimal.ZERO;
		for (EnumValue ev : categories) {
			boolean found = false;
			for (ExpenseSummaryDO sumObj : categoryWithValue) {
				if (ev.getValue().equals(sumObj.getExpenseType())) {
					retVal.add(sumObj);
					total = total.add(sumObj.getSum());
					found = true;
					break;
				}
			}
			
			if (!found) {
				ExpenseSummaryDO empty = new ExpenseSummaryDO();
				empty.setExpenseType(ev.getValue());
				empty.setExpenseTypeLabel(ev.getLabel());
				empty.setSum(BigDecimal.ZERO);
				retVal.add(empty);
			}
		}
		
		ExpenseSummaryDO totalSum = new ExpenseSummaryDO();
		totalSum.setExpenseType("TOTAL");
		totalSum.setExpenseTypeLabel("Total");
		totalSum.setSum(total);
		retVal.add(totalSum);
		
		return retVal;
	}
	
	public YearMonth findPreviousMonth(int month, int year) {
		Date prev = expenseRepo.findPreviousMonth(month, year);
		return prev == null ? null : YearMonth.from(toLocalDate(prev));
	}
	
	public YearMonth findNextMonth(int month, int year) {
		Date next = expenseRepo.findNextMonth(month, year);
		return next == null ? null : YearMonth.from(toLocalDate(next));
	}
	
	public void save(ExpenseDO expenseDo) {
		Expense expense = this.fromDataObject(expenseDo);
		expenseRepo.save(expense);
	}
	
	public void delete(Long id) {
		this.delete(id, true);
	}
	
	/**
	 * Delete expense by ID
	 * @param id id of expense
	 * @param admin mode, when <code>true</code> no checks are performed against deleting ID. 
	 * Otherwise it checks if entity exists, and if can be deleted according to creation date.
	 * 
	 */
	public void delete(Long id, boolean admin) {
		
		if (!admin) {
			Expense entity = expenseRepo.findOne(id);
			if (entity == null) {
				// throw error
				System.err.println("Expense with id: " + id + " not found!");
				return;
			}
			
			if (!LocalDate.now().equals(toLocalDate(entity.getCreated()))) {
				System.err.println("Can't delete expense older than from today!");
				return;
			}
		}
		
		expenseRepo.delete(id);
	}
	
	public Expense fromDataObject(ExpenseDO data) {
		Expense retVal = new Expense();
		retVal.setId(data.getId());
		retVal.setAmount(data.getAmount());
		retVal.setDate(toUtilDate(data.getDate()));
		retVal.setExpenseType(data.getExpenseType());
		retVal.setNote(data.getNote());
		retVal.setCreated(new Date());
		return retVal;
	}
	
	public ExpenseDO toDataObject(Expense data) {
		ExpenseDO retVal = new ExpenseDO();
		retVal.setId(data.getId());
		retVal.setAmount(data.getAmount());
		retVal.setDate(toLocalDate(data.getDate()));
		retVal.setExpenseType(data.getExpenseType());
		retVal.setNote(data.getNote());
		retVal.setExpenseTypeLabel(enumSvc.resolve(data.getExpenseType(), EnumNames.EXPENSES));
		
		// TODO ineffective creating of today date
		LocalDate today = LocalDate.now();
		retVal.setEditable(today.equals(toLocalDate(data.getCreated())));
		return retVal;
	}
	
	public  ExpenseSummaryDO mapperExpenseSummary(ExpenseSummary source) {
		ExpenseSummaryDO retVal = new ExpenseSummaryDO();
		retVal.setExpenseType(source.getExpenseType());
		retVal.setSum(source.getSum());
		retVal.setExpenseTypeLabel(enumSvc.resolve(source.getExpenseType(), EnumNames.EXPENSES));
		return retVal;
	}
	
	private <T, S>  List<T> iterableToDataObjectList(Iterable<S> iterable, Function<S, T> func) {
		return StreamSupport.stream(iterable.spliterator(), false)
			.map(func)
			.collect(Collectors.toList());
	}
	
	private static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	private static Date toUtilDate(LocalDate date) {
		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
