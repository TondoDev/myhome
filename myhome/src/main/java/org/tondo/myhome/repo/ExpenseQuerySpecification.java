package org.tondo.myhome.repo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.tondo.myhome.domain.Expense;

public class ExpenseQuerySpecification {
	public static Specification<Expense> create() {
		return new Specification<Expense>() {

			@Override
			public Predicate toPredicate(Root<Expense> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("expenseType"), "FaOOD");
			}
		};
	}
}
