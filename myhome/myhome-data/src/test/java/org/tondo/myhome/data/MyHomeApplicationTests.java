package org.tondo.myhome.data;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tondo.myhome.data.domain.Expense;
import org.tondo.myhome.data.domain.ExpenseSummary;
import org.tondo.myhome.data.repo.ExpenseQuerySpecification;
import org.tondo.myhome.data.repo.ExpenseRepository;
import org.tondo.myhome.data.repo.SpecExpenseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest//(classes=MyHomeApplication.class)
@DataJpaTest
public class MyHomeApplicationTests {

	@Autowired
	private SpecExpenseRepository userRepository;
	
	@Autowired
	private ExpenseRepository repo;
	
	@Test
	public void contextLoads() {
		List<Expense> list = userRepository.findAll(ExpenseQuerySpecification.create());
		System.out.println(list.size());
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void testYearSummary() {
		Iterable<ExpenseSummary> summary = repo.summaryByYear(2017);
		Iterator<ExpenseSummary> iter = summary.iterator();
		while(iter.hasNext()) {
			ExpenseSummary item = iter.next();
			System.out.println(item.getSum() + " " + item.getExpenseType() + " " + item.getMonth());
		}
	}
}
