package org.tondo.myhome.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.FondPayment;

/**
 * 
 * @author Anton Zsíros
 *
 */
public interface FondPaymentRepository extends CrudRepository<FondPayment, Long> {

	List<FondPayment> findByParentFond(Fond fond); 
}
