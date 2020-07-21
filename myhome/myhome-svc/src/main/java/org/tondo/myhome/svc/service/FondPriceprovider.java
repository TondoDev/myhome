package org.tondo.myhome.svc.service;

import java.util.List;

import org.tondo.myhome.svc.data.Price;

public interface FondPriceprovider {

	public List<Price> getPrices(String isin);
}
