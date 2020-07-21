package org.tondo.myhome.svc.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceEnvelope {

	private String currency;
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	private List<Price> list;
	
	public List<Price> getList() {
		return list;
	}
	
	public void setList(List<Price> list) {
		this.list = list;
	}

	
}
