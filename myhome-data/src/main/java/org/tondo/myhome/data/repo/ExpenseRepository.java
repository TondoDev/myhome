package org.tondo.myhome.data.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.data.domain.Expense;
import org.tondo.myhome.data.domain.ExpenseSummary;


public interface ExpenseRepository extends CrudRepository<Expense, Long> {
	@Query("select e from #{#entityName} e where MONTH(e.date) = ?1 and YEAR(e.date) = ?2 order by e.date desc, e.id desc")
	Iterable<Expense> findForMonthOrderByDateDesc(int month, int year);
	
	Iterable<Expense> findAllByOrderByDateDescIdDesc();
	
	// Projection
	// all projected fields must have alias(even if they have same name in target object)
	@Query("select e.expenseType as expenseType, sum(e.amount) as sum from #{#entityName} e group by e.expenseType")
	Iterable<ExpenseSummary> totalSummary();
	
	@Query("select e.expenseType as expenseType, sum(e.amount) as sum " +
			"from #{#entityName} e where MONTH(e.date) = ?1 and YEAR(e.date) = ?2  " + 
			"group by e.expenseType")
	Iterable<ExpenseSummary> summaryByMonth(int month, int year);
}