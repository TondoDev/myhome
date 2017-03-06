package org.tondo.myhome.repo;

import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.domain.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

}
