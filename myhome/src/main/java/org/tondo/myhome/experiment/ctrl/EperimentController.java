package org.tondo.myhome.experiment.ctrl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tondo.myhome.experiment.domain.Food;
import org.tondo.myhome.experiment.domain.FoodRepository;

@RestController
public class EperimentController {
	
	@Autowired
	private FoodRepository foodRepository;
	
	@RequestMapping("/foodall/")
	public List<Food> something() {
		return foodRepository.findAll();
	}


	@RequestMapping("/food/")
	public List<Food> getFoods() {
		
		List<Food> list = new ArrayList<>();
		Food halusky = new Food();
		halusky.setName("Halusky");
		list.add(halusky);
		return list;
	}
	
}
