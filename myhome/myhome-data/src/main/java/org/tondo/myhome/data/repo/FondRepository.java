package org.tondo.myhome.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.data.domain.Fond;

/**
 * 
 * @author TondoDev
 *
 */
public interface FondRepository extends CrudRepository<Fond, Long> {

	
	List<Fond> findAllByOrderByEstablishingDateDescNameAsc();
	
}
