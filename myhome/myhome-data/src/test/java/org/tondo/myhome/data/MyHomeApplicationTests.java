package org.tondo.myhome.data;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tondo.myhome.data.domain.Expense;
import org.tondo.myhome.data.repo.ExpenseQuerySpecification;
import org.tondo.myhome.data.repo.SpecExpenseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest//(classes=MyHomeApplication.class)
@DataJpaTest
public class MyHomeApplicationTests {

	@Autowired
	private SpecExpenseRepository userRepository;
	
	@Test
	public void contextLoads() {
		List<Expense> list = userRepository.findAll(ExpenseQuerySpecification.create());
		System.out.println(list.size());
		assertTrue(list.size() > 0);
	}
}
