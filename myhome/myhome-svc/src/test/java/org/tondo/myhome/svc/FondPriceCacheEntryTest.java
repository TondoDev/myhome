package org.tondo.myhome.svc;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.tondo.myhome.svc.data.Price;
import org.tondo.myhome.svc.fondprice.FondPriceCacheEntry;

public class FondPriceCacheEntryTest {

	
	@Test
	public void testEmpty() {
		
		FondPriceCacheEntry cache = new FondPriceCacheEntry();
		
		assertTrue("Empty cache", cache.callForUpdate(null));
		assertTrue("Empty cache", cache.callForUpdate(LocalDate.now()));
	}
	
	
	private static Price createPrice(LocalDate date) {
		Price price = new Price();
		price.setDate(date);
		return price;
	}
}
