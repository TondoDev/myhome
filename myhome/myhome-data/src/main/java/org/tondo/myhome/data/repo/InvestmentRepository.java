package org.tondo.myhome.data.repo;

import org.springframework.data.repository.Repository;
import org.tondo.myhome.data.domain.Investment;

public interface InvestmentRepository extends Repository<Investment, Long> {
	Iterable<Investment> findAll();
}
