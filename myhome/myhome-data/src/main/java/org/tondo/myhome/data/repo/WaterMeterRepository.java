package org.tondo.myhome.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.tondo.myhome.data.domain.WaterMeter;

public interface WaterMeterRepository extends CrudRepository<WaterMeter, Long> { 

	List<WaterMeter> findAllByOrderByValidFromDesc();
}
