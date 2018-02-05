package org.tondo.myhome.data.repo;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.tondo.myhome.data.domain.Expense;
import org.tondo.myhome.data.domain.ExpenseSummary;


public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {
	@Query("select e from #{#entityName} e where MONTH(e.date) = ?1 and YEAR(e.date) = ?2 order by e.date desc, e.id desc")
	Iterable<Expense> findForMonthOrderByDateDesc(int month, int year);
	
	Iterable<Expense> findAllByOrderByDateDescIdDesc();
	
	Page<Expense> findAll(Pageable paging);
	
	// Projection
	// all projected fields must have alias(even if they have same name in target object)
	@Query("select e.expenseType as expenseType, sum(e.amount) as sum from #{#entityName} e group by e.expenseType")
	Iterable<ExpenseSummary> totalSummary();
	
	@Query("select e.expenseType as expenseType, sum(e.amount) as sum " +
			"from #{#entityName} e where MONTH(e.date) = ?1 and YEAR(e.date) = ?2  " + 
			"group by e.expenseType")
	Iterable<ExpenseSummary> summaryByMonth(int month, int year);
	
	@Query("select MONTH(e.date) as month, e.expenseType as expenseType, sum(e.amount) as sum " +
			"from #{#entityName} e where YEAR(e.date) = ?1 " +
			"group by MONTH(e.date), e.expenseType " +
			"order by MONTH(e.date) ASC")
	Iterable<ExpenseSummary> summaryByYear(int year);
	
	@Query("select MAX(e.date) from #{#entityName} e where (YEAR(e.date) < ?2) or (YEAR(e.date) = ?2 and MONTH(e.date) < ?1 )")
	Date findPreviousMonth(int month, int year);
	
	@Query("select MIN(e.date) from #{#entityName} e where (YEAR(e.date) > ?2) or (YEAR(e.date) = ?2 and MONTH(e.date) > ?1 )")
	Date findNextMonth(int month, int year);
	
	@Query("select MAX(YEAR(e.date)) from #{#entityName} e where YEAR(e.date) < ?1")
	Integer findPreviousYear(int currentYear);
	
	@Query("select MIN(YEAR(e.date)) from #{#entityName} e where YEAR(e.date) > ?1")
	Integer findNextYear(int currentYear);
}
