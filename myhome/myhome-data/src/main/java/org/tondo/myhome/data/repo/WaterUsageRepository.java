package org.tondo.myhome.data.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.tondo.myhome.data.domain.WaterMeter;
import org.tondo.myhome.data.domain.WaterUsage;

/**
 * 
 * @author TondoDev
 *
 */
public interface WaterUsageRepository extends PagingAndSortingRepository<WaterUsage, Long> {
	
	
	Iterable<WaterUsage> findAllByOrderByMeasuredDesc();
	Iterable<WaterUsage> findAllByWaterMeterOrderByMeasuredDesc(WaterMeter meter);

	WaterUsage findTopByOrderByMeasuredDesc();
	WaterUsage findTopByWaterMeterOrderByMeasuredDesc(WaterMeter meter);
}
