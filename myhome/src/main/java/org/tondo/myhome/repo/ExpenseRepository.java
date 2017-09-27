package org.tondo.myhome.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.domain.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
	@Query("select e from #{#entityName} e where MONTH(e.date) = ?1 and YEAR(e.date) = ?2 order by e.date desc")
	Iterable<Expense> findForMonthOrderByDateDesc(int month, int year);
	
	Iterable<Expense> findAllByOrderByDateDesc();
}
