package org.tondo.myhome.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ExpenseInDayDO extends ExpenseDO {

	@NotNull
	@Min(1)
	public Integer day;
	
	public Integer getDay() {
		return day;
	}
	
	public void setDay(Integer day) {
		this.day = day;
	}
}
