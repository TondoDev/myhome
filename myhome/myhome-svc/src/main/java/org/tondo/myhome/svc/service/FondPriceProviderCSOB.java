package org.tondo.myhome.svc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tondo.myhome.svc.data.Price;
import org.tondo.myhome.svc.data.PriceEnvelope;

@Service
public class FondPriceProviderCSOB implements FondPriceprovider {
	
	@Value("${csob.url}")
	private String url;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	
	@Override
	public List<Price> getPrices(String isin) {
		List<Price> retVal = new ArrayList<>();
		
		String fullUrl = this.url + "?ID="+isin;
		ResponseEntity<PriceEnvelope> response = restTemplate.getForEntity(fullUrl, PriceEnvelope.class);
		
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody().getList();
		}
		
		
		return retVal;
	}

	// // https://www.csob.sk/delegate/getMutualFundDetails?ID=CSOB00000013
}
