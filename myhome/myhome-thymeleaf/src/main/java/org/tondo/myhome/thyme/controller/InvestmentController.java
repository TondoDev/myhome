package org.tondo.myhome.thyme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.svc.service.InvestmentService;

@Controller
@RequestMapping("/investment")
public class InvestmentController {

	private InvestmentService investmentService;
	
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
	
}
