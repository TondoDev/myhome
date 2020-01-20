package org.tondo.myhome.svc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.data.repo.FondRepository;
import org.tondo.myhome.data.repo.InvestmentRepository;
import org.tondo.myhome.dto.invest.InvestmentDO;

@Service
public class InvestmentService {

	private InvestmentRepository investmentRepository;
	private FondRepository fondRepository;
	
	@Autowired
	public void setFondRepository(FondRepository fondRepository) {
		this.fondRepository = fondRepository;
	}
	
	
	@Autowired
	public void setInvestmentRepository(InvestmentRepository investmentRepository) {
		this.investmentRepository = investmentRepository;
	}
	
	
	public List<InvestmentDO> getInvestments() {
		
		List<InvestmentDO> retVal = new ArrayList<>();
		
		investmentRepository.findAll().forEach(e -> retVal.add(null));
		return retVal;
	}
}
