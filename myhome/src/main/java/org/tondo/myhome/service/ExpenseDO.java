package org.tondo.myhome.service;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseDO {

	private Long id;
	private Date date;
	private BigDecimal amount;
	private String expenseType;
	private String expenseTypeLabel;
	private String note;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getExpenseType() {
		return expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	
	public String getExpenseTypeLabel() {
		return expenseTypeLabel;
	}
	
	public void setExpenseTypeLabel(String expenseTypeLabel) {
		this.expenseTypeLabel = expenseTypeLabel;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
