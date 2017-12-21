package org.tondo.myhome.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ExpenseDO {

	private Long id;
	
	@NotNull
	private LocalDate date;
	
	@NotNull
	@DecimalMin("0.1")
	private BigDecimal amount;
	
	@NotNull
	private String expenseType;
	
	private String expenseTypeLabel;
	
	@Size(max=100)
	private String note;
	
	private boolean editable;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
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
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
