package org.tondo.myhome.experiment.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Cooking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal price;	
	private Date cookDate;
	
	@OneToOne
	private Food food;
	
	
	@javax.persistence.OneToMany
	private Collection<Eating> eatings;
	
	public Long getId() {
		return this.id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getCookDate() {
		return cookDate;
	}

	public void setCookDate(Date cookDate) {
		this.cookDate = cookDate;
	}
	
	public Food getFood() {
		return food;
	}
	
	public void setFood(Food food) {
		this.food = food;
	}
	
	public Collection<Eating> getEatings() {
		return this.eatings;
	}
	
	public void setEatings(Collection<Eating> eatings) {
		this.eatings = eatings;
	}
}
