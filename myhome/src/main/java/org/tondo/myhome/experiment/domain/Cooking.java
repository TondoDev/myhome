package org.tondo.myhome.experiment.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Cooking {
	private Long id;
	private BigDecimal price;	
	private Date cookDate;
	
	
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
}
