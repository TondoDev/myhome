package org.tondo.myhome;

import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tondo.myhome.domain.Expense;
import org.tondo.myhome.repo.ExpenseQuerySpecification;
import org.tondo.myhome.repo.SpecExpenseRepository;

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
		assertNull(list);
	}

}
