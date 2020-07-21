package org.tondo.myhome.svc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.svc.data.Price;
import org.tondo.myhome.svc.fondprice.FondPriceCacheEntry;

@Service
public class FondPriceService {
	
	private FondPriceprovider fondPriceProvider;
	
	private ConcurrentHashMap<String, FondPriceCacheEntry> cache = new ConcurrentHashMap<>();
	
	
	@Autowired
	public void setFondPriceProvider(FondPriceprovider fondPriceProvider) {
		this.fondPriceProvider = fondPriceProvider;
	}

	public Price getFondPrice(FondDO fond, LocalDate forDate) {
		
		FondPriceCacheEntry entry = this.cache.get(fond.getIsin());
		if (entry == null) {
			entry = new FondPriceCacheEntry();
			// maybe other thread already created a record for given isin
			FondPriceCacheEntry tmpEntry  = this.cache.putIfAbsent(fond.getIsin(), entry);
			if (tmpEntry != null) {
				entry = tmpEntry;
			}
		}
		
		// TODO implement locking
		if (entry.callForUpdate(forDate)) {
			List<Price> downloaded = this.fondPriceProvider.getPrices(fond.getIsin());
			List<Price> filtered = downloaded.stream().filter(p -> !p.getDate().isBefore(fond.getStartDate())).collect(Collectors.toList());
			entry.addEntries(filtered);
		}
		
		return entry.getPrice(forDate);
	}
}
