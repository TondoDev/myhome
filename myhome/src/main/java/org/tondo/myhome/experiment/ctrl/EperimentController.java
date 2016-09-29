package org.tondo.myhome.experiment.ctrl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tondo.myhome.experiment.domain.Cooking;
import org.tondo.myhome.experiment.domain.CookingRepository;
import org.tondo.myhome.experiment.domain.Eating;
import org.tondo.myhome.experiment.domain.EatingsRepository;
import org.tondo.myhome.experiment.domain.Food;
import org.tondo.myhome.experiment.domain.FoodRepository;

@RestController
public class EperimentController {
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private CookingRepository cookingRepository;
	
	@Autowired
	private EatingsRepository eatingRepository;
	
	@RequestMapping("/foodall/")
	public List<Food> something() {
		return foodRepository.findAll();
	}
	
	@RequestMapping("/cookings/")
	public List<Cooking> cookings() {
		 List<Cooking> list  = cookingRepository.findAll();
		 if (list != null && !list.isEmpty()) {
			 Cooking cook = list.get(0);
			 Collection<Eating> eatList = (Collection<Eating>) (cook.getEatings() == null ? new ArrayList<>() : new ArrayList<>(cook.getEatings()));
			 Eating e = new Eating();
			 e.setMealDate(new Date());
			 e.setMealType("vecera");
			 eatList.add(e);
			 cook.setEatings(eatList);
			 eatingRepository.save(e);
			 cookingRepository.save(cook);
		 }
		 
		 return list;
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
