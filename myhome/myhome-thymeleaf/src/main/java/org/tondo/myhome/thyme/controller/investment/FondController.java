package org.tondo.myhome.thyme.controller.investment;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.dto.invest.FondPaymentDO;
import org.tondo.myhome.dto.invest.FondValueDO;
import org.tondo.myhome.svc.service.InvestmentService;
import org.tondo.myhome.thyme.presentation.FormAttributes;

@Controller
@RequestMapping("/investment/fond")
public class FondController {
	
	
	private InvestmentService investmentService;
	
	
	@Autowired
	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}
	
	@RequestMapping("/{fondId}")
	public String fondDetail(@PathVariable Long fondId,  
			@RequestParam(name = "enteredUnitValue", required = false) Double enteredUnitValue,
			@RequestParam(name = "valueDateIn", required = false) LocalDate valueDateIn,
			Model model) {
		

		if (enteredUnitValue != null && enteredUnitValue > 0.0) {
			// price has priority over date, if both were entered process only price
			valueDateIn = null;
			//model.addAttribute("enteredUnitValue", enteredUnitValue);
		}
		
		FondDO fondDo = this.investmentService.getFond(fondId);
		model.addAttribute("fond", fondDo);
		
		List<FondPaymentDO> payments = this.investmentService.getFondPayments(fondId);
		model.addAttribute("fondPayments", payments);
		
		FondValueDO fondValue = this.investmentService.calculateFondValue(fondId, enteredUnitValue, valueDateIn);
		model.addAttribute("fondValue", fondValue);
		
		return "investment/fondDetail";
	}
	
	@RequestMapping("/{fondId}/createPayment")
	public String formCreateFondPayment(@PathVariable Long fondId, Model model) {		
		FondPaymentDO fondPayment = this.investmentService.initFondPayment(fondId);
		
		populateModelCreateFondPayment(fondPayment, model);
		
		return "investment/formFondPayment";
	}
	
	
	// ModelAttributeName = binding result is populated by default with entries named by class
	@PostMapping("/{fondId}/fondPayment/")
	public String createFondPayment(@PathVariable Long fondId,  @ModelAttribute("fondPayment") @Valid FondPaymentDO fondPayment, BindingResult bindings, Model model) {
		
		if (bindings.hasErrors()) {
			model.addAttribute("fondPayment", fondPayment);
			// this is the error code, which will be resolved in localization resources
			// bindings.reject("Some additional error");
			populateModelCreateFondPayment(fondPayment, model);
			bindings.addError(new ObjectError("fondPayment", "Some global error!"));
			return "investment/formFondPayment";
		}
		
		this.investmentService.createFondPayment(fondPayment);
		
		return "redirect:/investment/fond/" + fondId;
	}
	
	@GetMapping("/{fondId}/fondPayment/{fondPaymentId}/edit")
	public String formEditFondPayment(@PathVariable Long fondId, @PathVariable Long fondPaymentId, Model model) {
		FondPaymentDO fondPayment = this.investmentService.getFondPaymentInFond(fondId, fondPaymentId);
		if (fondPayment == null) {
			throw new IllegalStateException("Inconsistent ids for FondPayment");
		}
		
		populateModelEditFondPayment(fondId, fondPaymentId, fondPayment, model);
		return "investment/formFondPayment";
	}
	
	@PutMapping("/{fondId}/fondPayment/{fondPaymentId}")
	public String updateFondPayment(@PathVariable Long fondId, @PathVariable Long fondPaymentId,
			@ModelAttribute("fondPayment") @Valid FondPaymentDO fondPayment, BindingResult bindings, 
			Model model) {
		
		if (bindings.hasErrors()) {
			populateModelEditFondPayment(fondId, fondPaymentId, fondPayment, model);
			return "investment/formFondPayment";
		}
		this.investmentService.updateFondPayment(fondId, fondPayment);
		return "redirect:/investment/fond/" + fondId;
	}
	
	@GetMapping("/{fondId}/fondPayment/{fondPaymentId}/delete")
	public String deleteFondPaymentDialog(@PathVariable Long fondId, @PathVariable Long fondPaymentId,
			Model model) {

	
		FondPaymentDO fondPaymentDO = this.investmentService.getFondPaymentInFond(fondId, fondPaymentId);
		if (fondPaymentDO == null) {
			return "redirect:/investment/";
		}
		String url = "/investment/fond/" + fondId;
		model.addAttribute("itemName", "Payment from '" + fondPaymentDO.getDateOfPurchase() + "'");
		
		
		model.addAttribute("backUrl", url);
		model.addAttribute("actionUrl", url + "/fondPayment/" + fondPaymentId);
		// url to redirect, after the operation is executed both successfully and unsuccessfully
		model.addAttribute("forwardUrl", url);
		return "investment/deleteDialog";
	}
	
	@DeleteMapping("/{fondId}/fondPayment/{fondPaymentId}")
	public String deleteFondPayment(@PathVariable Long fondId, @PathVariable Long fondPaymentId) {
		FondPaymentDO fondPaymentDO = this.investmentService.getFondPaymentInFond(fondId, fondPaymentId);
		if (fondPaymentDO == null) {
			return "redirect:/investment/";
		}
		
		this.investmentService.deleteFondPayment(fondPaymentId);
		return "redirect:/investment/fond/" + fondId;
	}

	private void populateModelEditFondPayment(Long fondId, Long fondPaymentId, FondPaymentDO fondPayment, Model model) {
		FormAttributes formAttrib = FormAttributes.builder()
				.method("put")
				.action("/investment/fond/" + fondId + "/fondPayment/" + fondPaymentId)
				.backUrl("/investment/fond/" + fondId)
				.submitCaption("Update")
				.create();
		model.addAttribute("formAttrib", formAttrib);
		model.addAttribute("fondPayment", fondPayment);
	}
	
	private void populateModelCreateFondPayment(FondPaymentDO fondPayment, Model model) {
		FormAttributes formAttrib = FormAttributes.builder()
				.method("post")
				.action("/investment/fond/" + fondPayment.getFondId() + "/fondPayment/")
				.backUrl("/investment/fond/" + fondPayment.getFondId())
				.submitCaption("Createee")
				.create();
			
			model.addAttribute("fondPayment", fondPayment);
			model.addAttribute("formAttrib", formAttrib);
	}
	
	@RequestMapping("/create")
	public String createFondForm( Model model) {
		FondDO defaultFond =  new FondDO();
		defaultFond.setStartDate(LocalDate.now());
		defaultFond.setFeePct(0.0);
		model.addAttribute("fond", defaultFond);
		return "investment/formFondCreate";
	}
	
	
	@RequestMapping("/{fondId}/edit")
	public String updateFondForm(@PathVariable Long fondId, @RequestParam(name = "backUrl", required = false) String backUrl, Model model, HttpRequest request) {
		FondDO fond = this.investmentService.getFond(fondId);
		model.addAttribute("fond", fond);
		model.addAttribute("backUrl", backUrl);
		return "investment/formFondEdit";
	}
	
	
	@PutMapping("/{fondId}")
	public String updateFond(
			// binding result must follow the input structure
			@ModelAttribute("fond") @Valid FondDO fond, BindingResult bindings,
			@ModelAttribute("backUrl") String backUrl,
			@PathVariable("fondId") Long fondId,
			Model model) {
		
		if (bindings.hasErrors()) {
			fond.setId(fondId);	// because we don't have fondID in request (POST) parameters, only in path variable
			model.addAttribute("fond", fond);
			model.addAttribute("backUrl", backUrl);
			return "investment/formFondEdit";
		}
		
		fond.setId(fondId);
		this.investmentService.updateFond(fond);
		
		return "redirect:/investment/fond/" + fondId;
	}
	
	
	
	@PostMapping("/")
	public String createFond(@ModelAttribute("fond") @Valid FondDO fond, BindingResult bindings,  Model model) {
		if (bindings.hasErrors()) {
			model.addAttribute("fond", fond);
			return "investment/formFondCreate";
		}
		
		FondDO savedFond = this.investmentService.createFond(fond);
		return "redirect:/investment/fond/" + savedFond.getId();
	}
	
	@GetMapping("/{fondId}/delete")
	public String deleteFondDialog(@PathVariable Long fondId,  Model model, @RequestParam(name = "backUrl", required = false) String backUrl) {
		
		FondDO fond = this.investmentService.getFond(fondId);
		if (fond == null) {
			return "redirect:/investment/";
		}
		
		model.addAttribute("itemName", fond.getName());
		
		model.addAttribute("backUrl", backUrl != null ? backUrl : "/investment/");
		model.addAttribute("actionUrl", "/investment/fond/" + fondId);
		// url to redirect, after the operation is executed both successfully and unsuccessfully
		model.addAttribute("forwardUrl", "/investment/");
		return "investment/deleteDialog";
	}
	
	@DeleteMapping("/{fondId}")
	public String deleteFond(@PathVariable Long fondId,  @ModelAttribute("backUrl") String backUrl) {
		FondDO fond = this.investmentService.getFond(fondId);
		if (fond == null) {
			return "redirect:" + (backUrl != null ? backUrl : "/investment/");
		}
		this.investmentService.deleteFond(fondId);
		return "redirect:/investment/";
	}
}
