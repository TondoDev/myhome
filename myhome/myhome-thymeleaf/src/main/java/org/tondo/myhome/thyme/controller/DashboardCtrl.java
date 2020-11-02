package org.tondo.myhome.thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DashboardCtrl {

	
	@RequestMapping(value = {"", "dashboard", "dashboard/"}, method = RequestMethod.GET)
	public String dashBoard() {
		return "dashboard";
	}
}
