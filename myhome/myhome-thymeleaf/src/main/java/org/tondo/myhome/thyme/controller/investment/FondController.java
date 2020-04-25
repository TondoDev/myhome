package org.tondo.myhome.thyme.controller.investment;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.dto.invest.FondPaymentDO;
import org.tondo.myhome.dto.invest.FondValueDO;
import org.tondo.myhome.svc.service.InvestmentService;

@Controller
@RequestMapping("/investment/fond")
public class FondController {
	// https://www.csob.sk/delegate/getMutualFundDetails?ID=CSOB00000013
	
	private InvestmentService investmentService;
	
	
	@Autowired
	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}
	
	@RequestMapping("/{fondId}")
	public String fondDetail(@PathVariable Long fondId,  @RequestParam(name = "enteredUnitValue", required = false) Double enteredUnitValue, Model model) {
		
		
		if (enteredUnitValue != null && enteredUnitValue > 0.0) {
			model.addAttribute("enteredUnitValue", enteredUnitValue);
		}
		System.out.println("Unit value: " + enteredUnitValue);
		FondDO fondDo = this.investmentService.getFond(fondId);
		model.addAttribute("fond", fondDo);
		
		List<FondPaymentDO> payments = this.investmentService.getFondPayments(fondId);
		model.addAttribute("fondPayments", payments);
		
		FondValueDO fondValue = this.investmentService.calculateFondValue(fondId, enteredUnitValue);
		model.addAttribute("fondValue", fondValue);
		
		return "investment/fondDetail";
	}
	
	@RequestMapping("/{fondId}/createPayment")
	public String formCreateFondPayment(@PathVariable Long fondId, Model model) {
		
		FondPaymentDO fondPayment = new FondPaymentDO();
		fondPayment.setFondId(fondId);
		fondPayment.setDateOfPurchase(LocalDate.now());
		
		FondDO fondDo = this.investmentService.getFond(fondId);
		double calculatedFee = fondDo.getAmountOfPay() * fondDo.getFeePct();
		fondPayment.setFee(calculatedFee);
		fondPayment.setBuyPrice(fondDo.getAmountOfPay() - calculatedFee);
		
		model.addAttribute("fondPayment", fondPayment);
		
		return "investment/formFondPayment";
	}
	
	
	// ModelAttributeName = binding result is populated by default with entries named by class
	@PostMapping("/{fondId}/fondPayment/")
	public String createFondPayment(@PathVariable Long fondId,  @ModelAttribute("fondPayment") @Valid FondPaymentDO fondPayment, BindingResult bindings, Model model) {
		
		if (bindings.hasErrors()) {
			model.addAttribute("fondPayment", fondPayment);
			// this is the error code, which will be resolved in localization resources
			// bindings.reject("Some additional error");
			bindings.addError(new ObjectError("fondPayment", "Some global error!"));
			return "investment/formFondPayment";
		}
		
		this.investmentService.createFondPayment(fondPayment);
		
		return "redirect:/investment/fond/" + fondId;
	}
	
	@RequestMapping("/create")
	public String createFondForm( Model model) {
		FondDO defaultFond =  new FondDO();
		defaultFond.setStartDate(LocalDate.now());
		defaultFond.setFeePct(0.0);
		model.addAttribute("fond", defaultFond);
		return "investment/formFond";
	}
	
	@PostMapping("/")
	public String createFond(@ModelAttribute("fond") @Valid FondDO fond, BindingResult bindings,  Model model) {
		
		
		if (bindings.hasErrors()) {
			model.addAttribute("fond", fond);
			return "investment/formFond";
		}
		
		this.investmentService.createFond(fond);
		
		return "redirect:/investment/";
	}

}
