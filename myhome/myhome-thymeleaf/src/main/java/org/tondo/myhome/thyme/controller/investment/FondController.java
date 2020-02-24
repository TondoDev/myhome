package org.tondo.myhome.thyme.controller.investment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.dto.invest.FondPaymentDO;
import org.tondo.myhome.svc.service.InvestmentService;

@Controller
@RequestMapping("/investment/fond")
public class FondController {
	
	
	private InvestmentService investmentService;
	
	
	@Autowired
	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}
	
	@RequestMapping("/{fondId}")
	public String fondDetail(@PathVariable Long fondId, Model model) {
		
		FondDO fondDo = this.investmentService.getFond(fondId);
		model.addAttribute("fond", fondDo);
		
		List<FondPaymentDO> payments = this.investmentService.getFondPayments(fondId);
		model.addAttribute("fondPayments", payments);
		
		
		return "investment/fondDetail";
	}
	
	
	@RequestMapping("/create")
	public String createFondForm(Model model) {
		
		return "investment/newFond";
	}

}
