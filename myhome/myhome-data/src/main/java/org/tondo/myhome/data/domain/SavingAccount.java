package org.tondo.myhome.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SavingAccount extends Investment{

	@Column(nullable = false)
	private Double interest;
	
	public Double getInterest() {
		return interest;
	}
	
	public void setInterest(Double interest) {
		this.interest = interest;
	}
}
