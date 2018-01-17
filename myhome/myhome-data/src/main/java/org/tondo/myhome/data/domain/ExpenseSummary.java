package org.tondo.myhome.data.domain;

import java.math.BigDecimal;

public interface ExpenseSummary {

	public String getExpenseType();
	public BigDecimal getSum();
	public Integer getMonth();
}
