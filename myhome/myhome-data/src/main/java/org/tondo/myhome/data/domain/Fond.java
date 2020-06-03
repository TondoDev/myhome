package org.tondo.myhome.data.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Fond extends Investment {

	@Column(nullable = false)
	private String isin;
	
	// unidirectional one-many:
	// it can be created using @JoinColumn annotation, but it is much less efficient that 
	// bidirectional mapping (because of two additional updates of FK in many side
	// https://thoughts-on-java.org/hibernate-tips-unidirectional-one-to-many-association-without-junction-table/
	//
	// cascadeType.All - if Fond is deleted, it will delete all containing payments
	// originally I didn't want this direction, but I need it for cascade deletion
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentFond", fetch = FetchType.LAZY)
	private List<FondPayment> payments = new ArrayList<>();

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

//	public List<FondPayment> getPayments() {
//		return payments;
//	}
//
//	public void setPayments(List<FondPayment> payments) {
//		this.payments = payments;
//	}
}
