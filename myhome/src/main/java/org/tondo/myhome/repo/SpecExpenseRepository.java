package org.tondo.myhome.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.domain.Expense;

public interface SpecExpenseRepository extends CrudRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

}
