package org.tondo.myhome.thyme.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.dto.invest.PortfolioSummaryDO;
import org.tondo.myhome.svc.service.FondPriceProviderCSOB;
import org.tondo.myhome.svc.service.InvestmentService;

@Controller
@RequestMapping("/investment")
public class InvestmentController {

	private InvestmentService investmentService;
	
	@Autowired
	private FondPriceProviderCSOB fondPriceService;
	
	@Autowired
	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}
	
	
	@RequestMapping("/")
	public String investmentHome(Model model) {
		List<FondDO> fonds = this.investmentService.getFonds();
		
		model.addAttribute("fonds", fonds);
		return "investment/home";
	}
	
	@GetMapping("/summary")
	public String investmentSummary(Model model, @RequestParam Map<String,String> allParams) {
		List<FondDO> fonds = this.investmentService.getFonds();
		Map<Long, Double> prices = createFondPriceMap(allParams);
		PortfolioSummaryDO portfolioSummary = this.investmentService.calculateFondsPortfolioSummary(fonds, prices);
		
		model.addAttribute("portfolioSummary", portfolioSummary);
		return "investment/summary";
	}
	
	protected Map<Long, Double> createFondPriceMap(Map<String, String> queryParams) {
		Map<Long, Double> prices = new HashMap<>();
		if (queryParams != null) {
			for(Map.Entry<String, String> qp : queryParams.entrySet()) {
				Long fondId = extracFondId(qp.getKey());
				if (fondId != null) {
					try {
						Double value = Double.parseDouble(qp.getValue());
						prices.put(fondId, value);
					} catch (NumberFormatException e) {
						// Nothing to do, fund price is not added to target map
					}
				}
			}
		}
		
		return prices;
	}
	
	private Long extracFondId(String fondKey) {
		String[] parts = fondKey.split("_");
		if (parts.length == 2 && "fond".equals(parts[0])) {
			try {
				return Long.parseLong(parts[1]);
			} catch ( NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	
}
