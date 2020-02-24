package org.tondo.myhome.svc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.data.domain.Fond;
import org.tondo.myhome.data.domain.FondPayment;
import org.tondo.myhome.data.domain.Investment;
import org.tondo.myhome.data.domain.SavingAccount;
import org.tondo.myhome.data.domain.ShareSummary;
import org.tondo.myhome.data.repo.FondPaymentRepository;
import org.tondo.myhome.data.repo.FondRepository;
import org.tondo.myhome.data.repo.InvestmentRepository;
import org.tondo.myhome.dto.invest.FondDO;
import org.tondo.myhome.dto.invest.FondPaymentDO;
import org.tondo.myhome.dto.invest.FondValueDO;
import org.tondo.myhome.dto.invest.InvestmentBaseDO;
import org.tondo.myhome.dto.invest.InvestmentDO;
import static org.tondo.myhome.svc.ServiceUtils.*;


@Service
public class InvestmentService {

	private InvestmentRepository investmentRepository;
	private FondRepository fondRepository;
	private FondPaymentRepository fondPaymentRepository;
	
	@Autowired
	public void setFondRepository(FondRepository fondRepository) {
		this.fondRepository = fondRepository;
	}
	
	@Autowired
	public void setInvestmentRepository(InvestmentRepository investmentRepository) {
		this.investmentRepository = investmentRepository;
	}
	
	@Autowired
	public void setFondPaymentRepository(FondPaymentRepository fondPaymentRepository) {
		this.fondPaymentRepository = fondPaymentRepository;
	}
	
	
	public List<InvestmentDO> getInvestments() {
		return iterableToDataObjectList(investmentRepository.findAll(), InvestmentService::toInvestmentDataObject);
	}
	
	public List<FondDO> getFonds() {
		return iterableToDataObjectList(fondRepository.findAllByOrderByEstablishingDateDescNameAsc(), InvestmentService::toFondDataObject);
	}
	
	public FondDO getFond(Long id) {
		Fond fond = fondRepository.findOne(id);
		return fond == null ? null : toFondDataObject(fond);
	}
	
	
	public void createFond(FondDO fond) {
		Fond toSave = toFondPersistentObject(fond);
		this.fondRepository.save(toSave);
	}
	
	public void createFondPayment(FondPaymentDO fondPayment) {
		Fond parentFond = fondRepository.findOne(fondPayment.getFondId());
		
		if (parentFond == null) {
			// throw error
		}
		
		FondPayment paymentToSave = toFondPaymentPersistentObject(fondPayment);
		paymentToSave.setParentFond(parentFond);
		fondPaymentRepository.save(paymentToSave);
	}
	
	public List<FondPaymentDO> getFondPayments(Long fondId) {
		Fond parentFond = fondRepository.findOne(fondId);
		
		if (parentFond == null) {
			// throw error
		}
		
		List<FondPayment> paymentsData = fondPaymentRepository.findByParentFondOrderByDateOfPurchaseDesc(parentFond);
		return iterableToDataObjectList(paymentsData, InvestmentService::toFondPaymentDataObject);
	}
	
	public FondValueDO calculateFondValue(Long fondId) {
		Fond parentFond = fondRepository.findOne(fondId);
		
		if (parentFond == null) {
			// throw error
		}
		
		ShareSummary summary = this.fondPaymentRepository.getSumOfPaymentsAndFees(parentFond);
		FondValueDO fondValue = toFondValueDataObject(summary);
		
		
		FondPayment lastPayment = this.fondPaymentRepository.findTopByParentFondOrderByDateOfPurchaseDesc(parentFond);
		
		if (lastPayment != null) {
			// in other words Fond has at least one payment
			calculateFondValueByUnitPrice(fondValue, lastPayment.getUnitPrice());
		}
		
		
		
		return fondValue;
		
	}
	
	private void calculateFondValueByUnitPrice(FondValueDO fondValue, Double unitPrice) {
		if (unitPrice == null) {
			return;
		}
		
		fondValue.setUnitPrice(unitPrice);
		fondValue.setTotalFondValue(unitPrice * fondValue.getOwnedUnits());
		fondValue.setActualFondProfit(fondValue.getTotalFondValue() -  fondValue.getTotalBuyPrice());
		fondValue.setProfit(fondValue.getActualFondProfit() - fondValue.getTotalFees());
	}
	
	private static FondValueDO toFondValueDataObject(ShareSummary summary) {
		FondValueDO fondValue = new FondValueDO();
		
		fondValue.setOwnedUnits(doubleNullAsZero(summary.getOwnedUnitsCount()));
		fondValue.setTotalBuyPrice(doubleNullAsZero(summary.getTotalBuyPrice()));
		fondValue.setTotalFees(doubleNullAsZero(summary.getTotalFees()));
		
		return fondValue;
	}
	
	private static FondPaymentDO toFondPaymentDataObject(FondPayment obj) {
		FondPaymentDO payment = new FondPaymentDO();
		
		payment.setFondId(obj.getParentFond().getId());
		payment.setDateOfPurchase(obj.getDateOfPurchase());
		payment.setBuyPrice(obj.getBuyPrice());
		payment.setFee(obj.getFee());
		payment.setUnitPrice(obj.getUnitPrice());
		
		return payment;
	}
	
	private static InvestmentDO toInvestmentDataObject(Investment obj) {
		InvestmentDO investmentDO = new InvestmentDO();
		
		populateInvestmentAttributes(investmentDO, obj);
		investmentDO.setType(resolveInvestmentType(obj));
		
		return investmentDO;
	}
	
	private static void populateInvestmentAttributes(InvestmentBaseDO target, Investment src) {
		target.setId(src.getId());
		target.setName(src.getName());
		target.setStartDate(src.getEstablishingDate());
		target.setEndDate(src.getEndDate());
		target.setAmountOfPay(src.getAmountOfPay());
		target.setPaymentRecurrence(src.getPaymentRecurrence());
		target.setDayOfPay(src.getDayOfPay());
		target.setFeePct(src.getFeePct());
	}
	
	
	private static FondDO toFondDataObject(Fond obj) {
		FondDO fondDo = new FondDO();
		
		populateInvestmentAttributes(fondDo, obj);
		fondDo.setIsin(obj.getIsin());
		
		return fondDo;
	}
	
	
	private static Fond toFondPersistentObject(FondDO fondDo) {
		Fond fond = new Fond();
		
		populateInvestmentPersistent(fond, fondDo);
		fond.setIsin(fondDo.getIsin());
		
		return fond;
	}
	
	private static FondPayment toFondPaymentPersistentObject(FondPaymentDO fondPaymentDo) {
		FondPayment payment = new FondPayment();
		
		payment.setDateOfPurchase(fondPaymentDo.getDateOfPurchase());
		payment.setBuyPrice(fondPaymentDo.getBuyPrice());
		payment.setFeeAmount(fondPaymentDo.getFee());
	 	
		return payment;
	}
	
	
	private static void populateInvestmentPersistent(Investment target, InvestmentBaseDO src) {
		target.setId(src.getId());
		target.setName(src.getName());
		target.setEstablishingDate(src.getStartDate());
		target.setEndDate(src.getEndDate());
		target.setDayOfPay(src.getDayOfPay());
		target.setFeePct(src.getFeePct());
		target.setPaymentRecurrence(src.getPaymentRecurrence());
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
