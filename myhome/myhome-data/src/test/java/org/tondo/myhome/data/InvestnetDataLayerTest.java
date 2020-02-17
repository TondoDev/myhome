package org.tondo.myhome.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.tondo.myhome.data.InvestmentSampleTestData.createDefaultTestFond;
import static org.tondo.myhome.data.InvestmentSampleTestData.createDefaultTestFondPayment;
import static org.tondo.myhome.data.InvestmentSampleTestData.createFondWithPayment;
import static org.tondo.myhome.data.InvestmentSampleTestData.createPaymentForFond;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.FondPayment;
import org.tondo.myhome.data.domain.ShareSummary;
import org.tondo.myhome.data.repo.FondPaymentRepository;
import org.tondo.myhome.data.repo.FondRepository;
import org.tondo.myhome.data.repo.InvestmentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InvestnetDataLayerTest {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private FondRepository fondRepository;
	
	@Autowired
	private FondPaymentRepository fondPaymentRepository;
	
	@Autowired
	private InvestmentRepository investmentRepository;
	
	
	@Test
	public void testFondSave() {
		
		assertNotNull(entityManager);
		System.out.println("-- " + entityManager);
		
		
		Fond fond = new Fond();
		
		fond.setAmountOfPay(80.0);
		fond.setDayOfPay("11");
		fond.setFeePct(0.03);
		fond.setIsin("xxyyy");
		fond.setName("Moj Fond");
		fond.setEstablishingDate(LocalDate.now());
		System.out.println(fond);
		
		entityManager.persist(fond);
		
		
		List<Fond> results = entityManager.createQuery("from Fond", Fond.class).getResultList();
		assertEquals(1, results.size());
		
		System.out.println(results.get(0).getName());
		System.out.println(results.get(0));
	}
	
	
	@Test
	public void testFondSaveWithPayment() {
		
		assertNotNull(entityManager);
		System.out.println("-- " + entityManager);
		
		
		Fond fond = new Fond();
		
		fond.setAmountOfPay(80.0);
		fond.setDayOfPay("11");
		fond.setFeePct(0.03);
		fond.setIsin("xxyyy");
		fond.setName("Moj Fond");
		fond.setEstablishingDate(LocalDate.now());
		System.out.println(fond);
		
		FondPayment payment = new FondPayment();
		payment.setBuyPrice(75.0);
		payment.setDateOfPurchase(LocalDate.now());
		payment.setFeeAmount(3.0);
		payment.setUnitPrice(15d);
		payment.setParentFond(fond);
		
		
		entityManager.persist(fond);
		
		
		List<Fond> results = entityManager.createQuery("from Fond", Fond.class).getResultList();
		assertEquals(1, results.size());
		
		System.out.println(results.get(0).getName());
		System.out.println(results.get(0));
		
		
		FondPayment payment2 = new FondPayment();
		payment2.setBuyPrice(66.0);
		payment2.setDateOfPurchase(LocalDate.now());
		payment2.setFeeAmount(3.0);
		payment2.setUnitPrice(15d);
		
		entityManager.persist(payment2);
		
		List<FondPayment> payments = entityManager.createQuery("from FondPayment", FondPayment.class).getResultList();
		System.out.println(payments.size());
	}
	
	@Test
	public void testSaveAndGetFondByRepository() {
		System.out.println(this.fondRepository);
		Fond fond = createDefaultTestFond();
		// this will save data into Investment and Fond tables
		// we don't need separate repository for Investment for saving.
		// Investment is parent class in Entity type hierarchy
		Fond returnedFond = this.fondRepository.save(fond);
		System.out.println("Returned: " + returnedFond);
		System.out.println("Saved: " + fond);
		
		Fond retrieved = this.fondRepository.findOne(fond.getId());
		System.out.println("Retrieved: " + retrieved);
	}
	
	@Test
	public void testSaveWithPayment() {
		System.out.println("repo " + this.fondRepository);
		Fond fond = createDefaultTestFond();
		FondPayment payment = createDefaultTestFondPayment();
		
		payment.setParentFond(fond);
		// fond repository can handle saving also for "Many side" entities
		this.fondRepository.save(fond);
		this.fondPaymentRepository.save(payment);
		
		Fond foundFound = this.fondRepository.findOne(fond.getId());
		assertNotNull(foundFound);
		
		List<FondPayment> payments = fondPaymentRepository.findByParentFond(foundFound);
		assertEquals(1,  payments.size());
		
		FondPayment anotherPayment = createDefaultTestFondPayment();
		
		anotherPayment.setParentFond(fond);
		
		// it will identify that we added new FondPayment into collection
		this.fondRepository.save(fond);
		
		// I wanted to try whether some update SQL will be executed, but it wasn't
		anotherPayment.setUnitPrice(1000d);
		fond.setIsin("MSG");
		this.fondRepository.save(fond);
	}
	
	
	
	@Test
	public void testSumOfPaymentsAndFees() {
		Fond fond = createDefaultTestFond();
		fondRepository.save(fond);
		assertNotNull("Fond saved, id determined", fond.getId());
		
		ShareSummary summary = fondPaymentRepository.getSumOfPaymentsAndFees(fond);
		assertNull("For empty payment list, fees are returned as null", summary);
		
		FondPayment lastPaymentFromEmptyFond = fondPaymentRepository.findTopByParentFondOrderByDateOfPurchaseDesc(fond);
		assertNull("Instance not created for Fond without fond payments", lastPaymentFromEmptyFond);
		
		
		FondPayment payment = createDefaultTestFondPayment();
		payment.setParentFond(fond);
		payment.setBuyPrice(50.0);
		payment.setUnitPrice(10.0);
		payment.setDateOfPurchase(payment.getDateOfPurchase().plusDays(1));
		fondPaymentRepository.save(payment);
		
		summary = fondPaymentRepository.getSumOfPaymentsAndFees(fond);
		assertEquals("sum of fees for single payment", 3.0, summary.getTotalFees(), 0.001);
		assertEquals("sum of owned values", 5.0, summary.getOwnedUnitsCount(), 0.001);
		
		FondPayment anotherPayment = createDefaultTestFondPayment();
		anotherPayment.setParentFond(fond);
		anotherPayment.setBuyPrice(50.0);
		anotherPayment.setUnitPrice(0.01);
		anotherPayment.setDateOfPurchase(anotherPayment.getDateOfPurchase().plusDays(2));
		fondPaymentRepository.save(anotherPayment);
		
		summary = fondPaymentRepository.getSumOfPaymentsAndFees(fond);
		assertEquals("sum of fees for two payments", 6.0, summary.getTotalFees(), 0.001);
		assertEquals("sum of owned values", 5005.0, summary.getOwnedUnitsCount(), 0.001);
		
		
		FondPayment lastPayment = fondPaymentRepository.findTopByParentFondOrderByDateOfPurchaseDesc(fond);
		assertNotNull(lastPayment);
		assertEquals(0.01, lastPayment.getUnitPrice(), 0.001);
	}
	
	@Test
	public void testFondSummaryAggregation() {
		Fond first = createDefaultTestFond();
		FondPayment fondPaymentFirst = createPaymentForFond(first);
		fondRepository.save(first);
		fondPaymentRepository.save(fondPaymentFirst);
		
		System.out.println(fondPaymentRepository.count());
		
		List<ShareSummary> summary = fondPaymentRepository.getSumOfPaymentsAndFees();
		System.out.println(summary);

		System.out.println(fondPaymentRepository.getSumOfPaymentsAndFees(first));
	}
	
	
	@Test
	public void testGetInvestments() {
		Fond fond = createFondWithPayment();
		this.fondRepository.save(fond);
		
		
		this.investmentRepository.findAll().forEach(inv -> System.out.println(inv.getClass().getCanonicalName()));
	}
}
