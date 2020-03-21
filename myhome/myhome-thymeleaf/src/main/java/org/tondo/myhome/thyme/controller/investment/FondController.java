package org.tondo.myhome.thyme.controller.investment;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.dto.invest.FondPaymentDO;
import org.tondo.myhome.dto.invest.FondValueDO;
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
		
		FondValueDO fondValue = this.investmentService.calculateFondValue(fondId);
		model.addAttribute("fondValue", fondValue);
		
		return "investment/fondDetail";
	}
	
	@RequestMapping("/{fondId}/createPayment")
	public String formCreateFondPayment(@PathVariable Long fondId, Model model) {
		FondPaymentDO fondPayment = new FondPaymentDO();
		fondPayment.setFondId(fondId);
		fondPayment.setDateOfPurchase(LocalDate.now());
		
		model.addAttribute("fondPayment", fondPayment);
		
		return "investment/formFondPayment";
	}
	
	
	@PostMapping("/{fondId}/fondPayment/")
	public String createFondPayment(@PathVariable Long fondId, @Valid FondPaymentDO fondPayment, Model model) {
		
		if (fondId == null) {
			model.addAttribute("fondPayment", fondPayment);
			return "investment/formFondPayment";
		}
		
		this.investmentService.createFondPayment(fondPayment);
		
		System.out.println("FondId = " + fondId);
		return "redirect:/investment/fond/" + fondId;
	}
	
	@RequestMapping("/create")
	public String createFondForm(Model model) {
		
		return "investment/newFond";
	}

}
