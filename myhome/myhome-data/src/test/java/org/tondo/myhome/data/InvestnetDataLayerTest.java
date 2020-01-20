package org.tondo.myhome.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.tondo.myhome.data.InvestmentSampleTestData.*; 

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.FondPayment;
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
	private InvestmentRepository investmentRepository;
	
	
	@Test
	public void testFondSave() {
		
		assertNotNull(entityManager);
		System.out.println("-- " + entityManager);
		
		
		Fond fond = new Fond();
		
		fond.setAmountOfPay(80.0);
		fond.setDayOfPay("11");
		fond.setFee(0.03);
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
		fond.setFee(0.03);
		fond.setIsin("xxyyy");
		fond.setName("Moj Fond");
		fond.setEstablishingDate(LocalDate.now());
		System.out.println(fond);
		
		FondPayment payment = new FondPayment();
		payment.setBuyPrice(75.0);
		payment.setDateOfPurchase(LocalDate.now());
		payment.setFee(3.0);
		payment.setUnitPrice(15d);
		payment.setParentFond(fond);
		
		fond.getPayments().add(payment);
		
		entityManager.persist(fond);
		entityManager.persist(fond);
		
		
		List<Fond> results = entityManager.createQuery("from Fond", Fond.class).getResultList();
		assertEquals(1, results.size());
		
		System.out.println(results.get(0).getName());
		System.out.println(results.get(0));
		
		
		FondPayment payment2 = new FondPayment();
		payment2.setBuyPrice(66.0);
		payment2.setDateOfPurchase(LocalDate.now());
		payment2.setFee(3.0);
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
		
		fond.getPayments().add(payment);
		payment.setParentFond(fond);
		// fond repository can handle saving also for "Many side" entities
		this.fondRepository.save(fond);
		
		Fond foundFound = this.fondRepository.findOne(fond.getId());
		assertEquals(1,  foundFound.getPayments().size());
		
		FondPayment anotherPayment = createDefaultTestFondPayment();
		fond.getPayments().add(anotherPayment);
		anotherPayment.setParentFond(fond);
		
		// it will identify that we added new FondPayment into collection
		this.fondRepository.save(fond);
		
		// I wanted to try whether some update SQL will be executed, but it wasn't
		anotherPayment.setUnitPrice(1000d);
		fond.setIsin("MSG");
		this.fondRepository.save(fond);
	}
	
	
	@Test
	public void testGetInvestments() {
		Fond fond = createFondWithPayment();
		this.fondRepository.save(fond);
		
		
		this.investmentRepository.findAll().forEach(inv -> System.out.println(inv.getClass().getCanonicalName()));
	}
}
