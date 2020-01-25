package org.tondo.myhome.svc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.Investment;
import org.tondo.myhome.data.domain.SavingAccount;
import org.tondo.myhome.data.repo.InvestmentRepository;
import org.tondo.myhome.dto.invest.InvestmentDO;
import org.tondo.myhome.svc.service.InvestmentService;

@RunWith(MockitoJUnitRunner.class)
public class InvestmentServiceTest {

	@InjectMocks
	private InvestmentService investmetnService;
	
	@Mock
	private InvestmentRepository investmentRepository;
	
	@Test
	public void testInvestmentListGet() {
		Mockito.when(investmentRepository.findAll()).thenReturn(createInvestmentList());
		
		List<InvestmentDO> invDoList = investmetnService.getInvestments();
		assertEquals(2, invDoList.size());
		assertEquals("Fond", invDoList.get(0).getType());
		assertEquals("Saving", invDoList.get(1).getType());
	}
	
	
	
	
	private List<Investment> createInvestmentList() {
		Fond a = new Fond();
		SavingAccount b = new SavingAccount();
		return Arrays.asList(a, b);
	}
	
}
