package org.tondo.myhome.svc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
	
	
	@Test
	public void testComparator() {
		List<String> list = new ArrayList<String>(Arrays.asList("1", "5", "2", "7", "4", "6", "8", "4"));
		
		list.sort(new PicaComp());
		System.out.println(list);
	}
	
	private static class PicaComp implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return Integer.parseInt(o1) - Integer.parseInt(o2);
		}
		
	}
	
}
