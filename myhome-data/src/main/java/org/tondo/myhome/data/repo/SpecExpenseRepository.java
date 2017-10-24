package org.tondo.myhome.data.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.data.domain.Expense;

public interface SpecExpenseRepository extends CrudRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

}
