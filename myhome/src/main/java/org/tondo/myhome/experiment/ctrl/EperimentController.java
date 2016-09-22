package org.tondo.myhome.experiment.ctrl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tondo.myhome.experiment.domain.Food;

@RestController
public class EperimentController {


	@RequestMapping("/food/")
	public List<Food> getFoods() {
		
		List<Food> list = new ArrayList<>();
		Food halusky = new Food();
		halusky.setAproximateCost(BigDecimal.valueOf(5.75));
		halusky.setCookDate(Calendar.getInstance());
		halusky.setName("Halusky");
		list.add(halusky);
		return list;
	}
	
}
