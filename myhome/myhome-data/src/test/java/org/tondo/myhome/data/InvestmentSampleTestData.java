package org.tondo.myhome.data;

import java.time.LocalDate;

import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.FondPayment;

public class InvestmentSampleTestData {
	
	public static Fond createDefaultTestFond() {
		Fond fond = new Fond();
		fond.setAmountOfPay(80.0);
		fond.setDayOfPay("11");
		fond.setFee(0.03);
		fond.setIsin("xxyyy");
		fond.setName("Moj Fond");
		fond.setEstablishingDate(LocalDate.now());
		
		return fond;
	}
	
	
	public static FondPayment createDefaultTestFondPayment() {
		FondPayment payment = new FondPayment();
		payment.setBuyPrice(75.0);
		payment.setDateOfPurchase(LocalDate.now());
		payment.setFee(3.0);
		payment.setUnitPrice(15d);
		
		return payment;
	}
	
	public static Fond createFondWithPayment() {
		Fond fond = createDefaultTestFond();
		
		FondPayment payment = createDefaultTestFondPayment();
		fond.getPayments().add(payment);
		payment.setParentFond(fond);
		
		
		return fond;
	}


}
