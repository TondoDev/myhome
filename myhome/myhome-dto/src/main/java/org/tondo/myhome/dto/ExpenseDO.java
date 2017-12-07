package org.tondo.myhome.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExpenseDO {

	private Long id;
	
	@NotNull
	private Date date;
	
	@NotNull
	@DecimalMin("0.1")
	private BigDecimal amount;
	
	@NotNull
	private String expenseType;
	
	private String expenseTypeLabel;
	
	@Size(max=100)
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
