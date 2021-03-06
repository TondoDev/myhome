package org.tondo.myhome.data.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.tondo.myhome.data.LocalDateDbConverter;

@Entity
public class FondPayment {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@Convert(converter = LocalDateDbConverter.class)
	private LocalDate dateOfPurchase;
	
	private Double fee;
	
	@Column(nullable = false)
	private Double buyPrice;
	
	@Column(nullable = false)
	private Double purchasedUnits;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Fond parentFond;
	
	public Fond getParentFond() {
		return parentFond;
	}
	
	public void setParentFond(Fond parentFond) {
		this.parentFond = parentFond;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDateOfPurchase() {
		return dateOfPurchase;
	}
	public void setDateOfPurchase(LocalDate dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	public Double getFee() {
		return fee;
	}
	public void setFeeAmount(Double fee) {
		this.fee = fee;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Double getPurchasedUnits() {
		return purchasedUnits;
	}
	public void setPurchasedUnits(Double purchasedUnits) {
		this.purchasedUnits = purchasedUnits;
	}
}
