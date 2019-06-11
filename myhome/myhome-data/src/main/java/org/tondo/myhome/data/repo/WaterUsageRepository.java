package org.tondo.myhome.data.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.tondo.myhome.data.domain.WaterUsage;

public interface WaterUsageRepository extends PagingAndSortingRepository<WaterUsage, Long> {

}
