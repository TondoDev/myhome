package org.tondo.myhome.svc.fondprice;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.tondo.myhome.svc.data.Price;

public class FondPriceCacheEntry {

	
	private LocalDate updated;
	private TreeMap<LocalDate, Price> entries;
	private LocalDate pastestSearchedDate;
	
	
	public FondPriceCacheEntry() {
		this.entries = new TreeMap<LocalDate, Price>();
	}
	
	
	public boolean callForUpdate(LocalDate priceDate) {
		
		if (this.updated == null) {
			return true;
		}
		
		if (priceDate == null) {
			// we want newest price, but only when previous update was called longer than 24hours ago
			return Period.between(LocalDate.now(), this.updated).getDays() > 0;
		}
		
		return this.pastestSearchedDate == null || (this.pastestSearchedDate.isAfter(priceDate) && !this.entries.isEmpty() && this.entries.firstKey().isAfter(priceDate)) ;
	}
	
	public void addEntries(List<Price> entries) {
		this.updated = LocalDate.now();
		
		entries.forEach(e -> this.entries.putIfAbsent(e.getDate(), e));
	}
	
	public Price getPrice(LocalDate priceDate) {
		if (priceDate == null) {
			return this.entries.isEmpty() ? null : this.entries.lastEntry().getValue();
		}
		
		if (this.pastestSearchedDate == null || priceDate.isBefore(this.pastestSearchedDate)) {
			this.pastestSearchedDate = priceDate;
		}
		
		return this.entries.isEmpty() ? null 
				// handling when exact date is not available, then return closest price BEFORE searched date
				: Optional.ofNullable(this.entries.floorEntry(priceDate)).map(entr -> entr.getValue()).orElse(null);
	}
	
	
	public LocalDate getUpdated() {
		return updated;
	}
}
