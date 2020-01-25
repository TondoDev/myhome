package org.tondo.myhome.svc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.Investment;
import org.tondo.myhome.data.domain.SavingAccount;
import org.tondo.myhome.data.repo.FondRepository;
import org.tondo.myhome.data.repo.InvestmentRepository;
import org.tondo.myhome.dto.invest.InvestmentDO;
import org.tondo.myhome.svc.ServiceUtils;

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
		return ServiceUtils.iterableToDataObjectList(investmentRepository.findAll(), InvestmentService::toInvestmentDataObject);
	}
	
	
	private static InvestmentDO toInvestmentDataObject(Investment obj) {
		InvestmentDO investmentDO = new InvestmentDO();
		
		investmentDO.setId(obj.getId());
		investmentDO.setName(obj.getName());
		investmentDO.setStartDate(obj.getEstablishingDate());
		investmentDO.setEndDate(obj.getEndDate());
		investmentDO.setAmountOfPay(obj.getAmountOfPay());
		investmentDO.setPaymentRecurrence(obj.getPaymentRecurrence());
		investmentDO.setType(resolveInvestmentType(obj));
		investmentDO.setDayOfPay(obj.getDayOfPay());
		
		return investmentDO;
	}
	
	private static String resolveInvestmentType(Investment obj) {
		
		if (obj instanceof Fond) {
			return "Fond";
		} else if (obj instanceof SavingAccount) {
			return "Saving";
		} else {
			return "UNKNOWN";
		}
	}
}
