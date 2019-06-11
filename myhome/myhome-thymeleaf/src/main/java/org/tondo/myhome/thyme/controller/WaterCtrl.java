package org.tondo.myhome.thyme.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tondo.myhome.dto.WaterUsageDO;
import org.tondo.myhome.svc.service.WaterSvc;

@Controller
@RequestMapping("/water")
public class WaterCtrl {
	
	@Autowired
	private WaterSvc waterService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getWaterUsage(Model model) {
		model.addAttribute("usages", waterService.getWagerUsage());
		model.addAttribute("waterUsage", new WaterUsageDO());
		return "water";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String saveWaterUsage(@ModelAttribute("waterUsage") @Valid WaterUsageDO waterUsage,  BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			waterService.addMeasurement(waterUsage);
		}
			
		return "redirect:/water/";
		//return "water";
	}

}
