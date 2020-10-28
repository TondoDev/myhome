package org.tondo.myhome.thyme.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.svc.data.Price;
import org.tondo.myhome.svc.service.FondPriceService;

@RestController
public class InvestmentAPI {

	
	private FondPriceService fondPriceService;
	
	
	private ConversionService conversionService;
	
	@Autowired
	public void setFondPriceService(FondPriceService service) {
		this.fondPriceService = service;
	}
	
	@Autowired
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}
	
	@CrossOrigin
	@GetMapping("/api/fondPrice")
	public Map<String, Object> getPrice(@RequestParam("isin") String isin, @RequestParam("date") LocalDate date) {
		
		FondDO fond = new FondDO();
		fond.setIsin(isin);
		// fake initialization , just to call fond price service
		fond.setStartDate(date);
		Price price =  this.fondPriceService.getFondPrice(fond, date);
		
		Map<String, Object> rv = new HashMap<>();
		
		if (price != null) {
			rv.put("price", price.getPrice());
			rv.put("date", conversionService.convert(price.getDate(), String.class));
			rv.put("isin", isin);
		}
		
		return rv;
	}
}
