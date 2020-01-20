package org.tondo.myhome.svc;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.tondo.myhome.data.InvestmentSampleTestData;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.Investment;
import org.tondo.myhome.data.repo.InvestmentRepository;
import org.tondo.myhome.svc.service.InvestmentService;

@RunWith(MockitoJUnitRunner.class)
public class InvestmentServiceTest {

	@InjectMocks
	private InvestmentService investmetnService;
	
	@Mock
	private InvestmentRepository investmentRepository;
	
	@Test
	public void testervice() {
		
	}
	
	
	
	
	private List<Investment>createEmptyFondsList() {
		Fond a = InvestmentSampleTestData.createDefaultTestFond();
		Fond b = InvestmentSampleTestData.createDefaultTestFond();
		
		return Arrays.asList(a, b);
	}
	
}
