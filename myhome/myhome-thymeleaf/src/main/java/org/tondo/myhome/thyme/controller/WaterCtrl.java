package org.tondo.myhome.thyme.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tondo.myhome.dto.WaterUsageDO;
import org.tondo.myhome.svc.exception.BusinessValidationexception;
import org.tondo.myhome.svc.service.WaterSvc;

@Controller
@RequestMapping("/water")
public class WaterCtrl {
	
	@Autowired
	private WaterSvc waterService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getWaterUsage(Model model) {
		model.addAttribute("usages", waterService.getWagerUsage());
		
		WaterUsageDO formDefalts = new WaterUsageDO();
		formDefalts.setMeasured(LocalDate.now());
		// if we override model attribute, it will also overrides the BindingResult
		if (!model.containsAttribute("waterUsage")) {
			model.addAttribute("waterUsage", formDefalts);
		}
		return "water";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String saveWaterUsage(@ModelAttribute("waterUsage") @Valid WaterUsageDO waterUsage,  BindingResult bindingResult, RedirectAttributes redirect) {
		if (!bindingResult.hasErrors()) {
			try {
				waterService.addMeasurement(waterUsage);
			} catch (BusinessValidationexception e) {
				redirect.addFlashAttribute("validationErrors", e.getFieldErrors());
			}
		} else {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.waterUsage", bindingResult);
		}
			
		redirect.addFlashAttribute("waterUsage", waterUsage);
		return "redirect:/water/";
	}
	
	
	
//	@ExceptionHandler(WaterValidationException.class)
//	public RedirectView businessValidationErrorHandler(HttpServletRequest req) {
//		RedirectView rw = new RedirectView();
//		rw.setUrl("/water/");
//		System.err.println("validation error: ");
//		return rw;
//	}
	
}
