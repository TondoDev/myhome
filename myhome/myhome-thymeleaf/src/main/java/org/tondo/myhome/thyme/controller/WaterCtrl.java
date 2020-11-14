package org.tondo.myhome.thyme.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tondo.myhome.dto.WaterMeterDO;
import org.tondo.myhome.dto.WaterUsageDO;
import org.tondo.myhome.svc.exception.BusinessValidationexception;
import org.tondo.myhome.svc.service.WaterSvc;
import org.tondo.myhome.thyme.presentation.FormAttributes;

@Controller
@RequestMapping("/water")
public class WaterCtrl {
	
	@Autowired
	private WaterSvc waterService;
	
	
	@RequestMapping(value = "/meter/{meterId}/usage", method = RequestMethod.GET)
	public String getWaterUsage(@PathVariable Long meterId, Model model) {
		model.addAttribute("usages", waterService.getWagerUsage(meterId));
		
		WaterMeterDO waterMeter = this.waterService.getWaterMeter(meterId);
		model.addAttribute("waterMeter", waterMeter);
		
		WaterUsageDO formDefalts = new WaterUsageDO();
		formDefalts.setMeasured(LocalDate.now());
		// if we override model attribute, it will also overrides the BindingResult
		if (!model.containsAttribute("waterUsage")) {
			model.addAttribute("waterUsage", formDefalts);
		}
		return "water/water";
	}
	
	@RequestMapping(value = "/meter/{meterId}/usage", method = RequestMethod.POST)
	public String saveWaterUsage(@PathVariable Long meterId, @ModelAttribute("waterUsage") @Valid WaterUsageDO waterUsage,  BindingResult bindingResult, RedirectAttributes redirect) {
		if (!bindingResult.hasErrors()) {
			try {
				waterService.addMeasurement(meterId, waterUsage);
			} catch (BusinessValidationexception e) {
				redirect.addFlashAttribute("validationErrors", e.getFieldErrors());
			}
		} else {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.waterUsage", bindingResult);
		}
			
		redirect.addFlashAttribute("waterUsage", waterUsage);
		return "redirect:/water/meter/" + meterId + "/usage";
	}
	
	
	@RequestMapping(value = "/meter/", method = RequestMethod.GET)
	public String getWaterMeters(Model model) {
		List<WaterMeterDO> meters = this.waterService.getWaterMeters();
		
		model.addAttribute("waterMeters", meters);
		return "water/waterMeter";
	}
	
	@RequestMapping(value = "/meter/create", method = RequestMethod.GET)
	public String formMeterCreate(Model model) {
		
		WaterMeterDO meter = new WaterMeterDO();
		meter.setValidFrom(LocalDate.now());
		meter.setActive(true);
		
		populateModelCreateMeter(model, meter);
		
		return "water/editWaterMeter";
	}
	
	@RequestMapping(value = "/meter", method = RequestMethod.POST)
	public String createWaterMeter(@ModelAttribute("waterMeter") @Valid WaterMeterDO waterMeter,  BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			populateModelCreateMeter(model, waterMeter);
			return "water/editWaterMeter";
		}
		
		this.waterService.addWaterMeter(waterMeter);
		return "redirect:/water/meter/";
	}
	
	private void populateModelCreateMeter(Model model, WaterMeterDO meter) {
		
		FormAttributes formAttrib = FormAttributes.builder()
				.method("post")
				.action("/water/meter")
				.submitCaption("Create")
				.create();
		
		model.addAttribute("formAttrib", formAttrib);
		model.addAttribute("waterMeter", meter);
	}
	
	@RequestMapping(value="meter/{meterId}/update")
	public String formMeterUpdate(@PathVariable("meterId") Long waterMeterId, Model model) {
		
		WaterMeterDO meterDO = this.waterService.getWaterMeter(waterMeterId);
		if (meterDO == null) {
			throw new IllegalStateException("Invalid waterMeter");
		}
		populateModelUpdateMeter(model, meterDO);
		return "water/editWaterMeter";
	}
	@RequestMapping(value="meter/{meterId}", method=RequestMethod.PUT)
	public String updateWaterMeter(
			@PathVariable("meterId") Long waterMeterId,
			@ModelAttribute("waterMeter") @Valid WaterMeterDO waterMeter,
			BindingResult bindingResult, Model model) {
		
		waterMeter.setId(waterMeterId);
		
		if(bindingResult.hasErrors()) {
			populateModelUpdateMeter(model, waterMeter);
			return "water/editWaterMeter";
		}
		
		this.waterService.updateWaterMeter(waterMeter);
		
		return "redirect:/water/meter/";
	}
	
	private void populateModelUpdateMeter(Model model, WaterMeterDO meter) {
		
		FormAttributes formAttrib = FormAttributes.builder()
				.method("put")
				.action("/water/meter/" + meter.getId())
				.submitCaption("Update")
				.create();
		
		model.addAttribute("formAttrib", formAttrib);
		model.addAttribute("waterMeter", meter);
	}
	
	
//	@ExceptionHandler(WaterValidationException.class)
//	public RedirectView businessValidationErrorHandler(HttpServletRequest req) {
//		RedirectView rw = new RedirectView();
//		rw.setUrl("/water/");
//		System.err.println("validation error: ");
//		return rw;
//	}
	
}
