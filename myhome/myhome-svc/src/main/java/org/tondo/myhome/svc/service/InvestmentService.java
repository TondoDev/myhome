package org.tondo.myhome.svc.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.tondo.myhome.dto.invest.PortfolioSummaryDO;
import org.tondo.myhome.svc.data.Price;

import static org.tondo.myhome.svc.ServiceUtils.*;


@Service
public class InvestmentService {

	private InvestmentRepository investmentRepository;
	private FondRepository fondRepository;
	private FondPaymentRepository fondPaymentRepository;
	
	private FondPriceService fondPriceService;
	
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
	
	@Autowired
	public void setFondPriceService(FondPriceService service) {
		this.fondPriceService = service;
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
	
	
	public FondDO createFond(FondDO fond) {
		Fond toSave = toFondPersistentObject(fond);
		Fond saved = this.fondRepository.save(toSave);
		return toFondDataObject(saved);
	}
	
	public void updateFond(FondDO fond) {
		Fond original = this.fondRepository.findOne(fond.getId());
		if (original == null) {
			System.err.println("Fond with id: " + fond.getId() + " doesn't exist!");
		}
		
		Fond toUpdate = toFondPersistentObject(fond);
		fondRepository.save(toUpdate);
	}
	
	public void deleteFond(Long id) {
		this.fondRepository.delete(id);
	}
	
	public FondPaymentDO initFondPayment(Long fondId) {
		FondPaymentDO fondPayment = new FondPaymentDO();
		fondPayment.setFondId(fondId);
		
		
		FondDO fondDo = this.getFond(fondId);
		double calculatedFee = fondDo.getAmountOfPay() * fondDo.getFeePct();
		fondPayment.setFee(calculatedFee);
		
		double buyPrice = fondDo.getAmountOfPay() - calculatedFee;
		fondPayment.setBuyPrice(buyPrice);
		
		LocalDate dateOfPurchase =  LocalDate.now();
		fondPayment.setDateOfPurchase(dateOfPurchase);
		Price price = this.fondPriceService.getFondPrice(fondDo, dateOfPurchase);
		
		if (price != null) {
			fondPayment.setPurchasedUnits(buyPrice / price.getPrice());
		}
		
		return fondPayment;
	}
	
	public FondPayment createFondPayment(FondPaymentDO fondPayment) {
		Fond parentFond = fondRepository.findOne(fondPayment.getFondId());
		
		if (parentFond == null) {
			// throw error
		}
		
		FondPayment paymentToSave = toFondPaymentPersistentObject(fondPayment);
		paymentToSave.setParentFond(parentFond);
		return fondPaymentRepository.save(paymentToSave);
	}
	
	public FondPayment updateFondPayment(Long fondId, FondPaymentDO fondPayment) {
		FondPayment check = this.fondPaymentRepository.findByParentFondIdAndId(fondId, fondPayment.getId());
		if(check == null) {
			//err
		}
		
		FondPayment toUpdate = toFondPaymentPersistentObject(fondPayment);
		toUpdate.setParentFond(check.getParentFond());
		return this.fondPaymentRepository.save(toUpdate);
	}
	
	public void deleteFondPayment(Long fondPaymentId) {
		this.fondPaymentRepository.delete(fondPaymentId);
	}
	
	public FondPaymentDO getFondPaymentInFond(Long fondId, Long fondPaymentId) {
		FondPayment fondPayment = this.fondPaymentRepository.findByParentFondIdAndId(fondId, fondPaymentId);
		return fondPayment == null ? null : toFondPaymentDataObject(fondPayment);
	}
	
	public List<FondPaymentDO> getFondPayments(Long fondId) {
		Fond parentFond = fondRepository.findOne(fondId);
		
		if (parentFond == null) {
			// throw error
		}
		
		List<FondPayment> paymentsData = fondPaymentRepository.findByParentFondOrderByDateOfPurchaseDescIdDesc(parentFond);
		return iterableToDataObjectList(paymentsData, InvestmentService::toFondPaymentDataObject);
	}
	
	public FondValueDO calculateFondValue(Long fondId, Double forPrice, LocalDate valueDate) {
		Fond parentFond = fondRepository.findOne(fondId);
		
		if (parentFond == null) {
			// throw error
		}
		
		ShareSummary summary = this.fondPaymentRepository.getSumOfPaymentsAndFees(parentFond, valueDate);
		
		FondValueDO fondValue;
		if (summary != null) {
			fondValue = toFondValueDataObject(summary);
		} else {
			fondValue = new FondValueDO();
		}
		
		fondValue.setFondId(parentFond.getId());
		fondValue.setFondName(parentFond.getName());
		
		// this means when price was entered, the valueDate is ignored
		double unitPrice = 0.0;
		if (forPrice != null && forPrice > 0.0) {
			// if unit prices came from outside, we use it - highest priority
			unitPrice = forPrice;
			// when unit price is entered manually, value date is unimportant
		} 
		else {
			LocalDate priceDate = valueDate != null ? valueDate : LocalDate.now();
			Price currentPrice =  this.fondPriceService.getFondPrice(toFondDataObject(parentFond), priceDate);
			if (currentPrice != null) {
				unitPrice = currentPrice.getPrice();
				fondValue.setValueDate(currentPrice.getDate());
			} else {
				// used when online price is not available
				// used unit price calculated from last purchase lower than price date
				FondPayment lastPayment = this.fondPaymentRepository.findTopByParentFondAndDateOfPurchaseLessThanEqualOrderByDateOfPurchaseDescIdDesc(parentFond, priceDate);
				if (lastPayment != null && lastPayment.getPurchasedUnits() != null && !isZero(lastPayment.getPurchasedUnits())) {
					unitPrice = lastPayment.getBuyPrice()/lastPayment.getPurchasedUnits();
					fondValue.setValueDate(lastPayment.getDateOfPurchase());
				}
			}
		}
		
		if (unitPrice > 0.0) {
			calculateFondValueByUnitPrice(fondValue, unitPrice);
		}
		
		zeroUndefinedProperties(fondValue);
		return fondValue;
	}

	private void zeroUndefinedProperties(FondValueDO fondValue) {
		
		if (fondValue.getOwnedUnits() == null) {
			fondValue.setOwnedUnits(0.0);
		}
		if (fondValue.getTotalInvest() == null) {
			fondValue.setTotalInvest(0.0);
		}
		if (fondValue.getProfit() == null) {
			fondValue.setProfit(0.0);
		}
		if (fondValue.getActualFondProfit() == null) {
			fondValue.setActualFondProfit(0.0);
		}
		if (fondValue.getTotalBuyPrice() == null) {
			fondValue.setTotalBuyPrice(0.0);
		}
		if (fondValue.getTotalFees() == null) {
			fondValue.setTotalFees(0.0);
		}
		if(fondValue.getTotalFondValue() == null) {
			fondValue.setTotalFondValue(0.0);
		}
	}
	
	public PortfolioSummaryDO calculateFondsPortfolioSummary(List<FondDO> fondPortfolio, Map<Long, Double> prices, LocalDate valueDate) {
		double totalInvested = 0.0;
		double totalFees = 0.0;
		double totalBuyPrice = 0.0;
		double totalFondProfit = 0.0;
		double totalCleanProfit = 0.0;
		double totalFondValue = 0.0;
		
		Map<Long, Double> pricesMap = prices == null ? new HashMap<>() : prices;
		List<FondValueDO> fondValues = new ArrayList<>();
		for (FondDO fond : fondPortfolio) {
			Double fondPrice = pricesMap.get(fond.getId());
			// when fond price is not provided, it tries to retrieve it from external service
			FondValueDO fondValue = this.calculateFondValue(fond.getId(), fondPrice, valueDate); // TODO gui field for this value must be created
			if (fondValue.getTotalInvest() != null) totalInvested += fondValue.getTotalInvest();
			if (fondValue.getTotalBuyPrice() != null) totalBuyPrice += fondValue.getTotalBuyPrice();
			if (fondValue.getTotalFees() != null) totalFees += fondValue.getTotalFees();
			if (fondValue.getTotalFondValue() != null) totalFondValue += fondValue.getTotalFondValue();
			if (fondValue.getActualFondProfit() != null) totalFondProfit += fondValue.getActualFondProfit();
			if (fondValue.getProfit() != null) totalCleanProfit += fondValue.getProfit();
			
			fondValues.add(fondValue);
		}
		
		PortfolioSummaryDO summary = new PortfolioSummaryDO();
		summary.setFonds(fondValues);
		FondValueDO sumFooter = new FondValueDO();
		sumFooter.setTotalInvest(totalInvested);
		sumFooter.setTotalBuyPrice(totalBuyPrice);
		sumFooter.setTotalFees(totalFees);
		sumFooter.setActualFondProfit(totalFondProfit);
		sumFooter.setProfit(totalCleanProfit);
		sumFooter.setTotalFondValue(totalFondValue);
		
		summary.setTotal(sumFooter);
		
		
		return summary;
		
	}
	
	private static boolean isZero(double number) {
		final double threshold = 0.00001;

		return number >= -threshold && number <= threshold;
	}

	
	private void calculateFondValueByUnitPrice(FondValueDO fondValue, Double unitPrice) {
		if (unitPrice == null) {
			return;
		}
		
		fondValue.setUnitPrice(unitPrice);
		fondValue.setTotalFondValue(unitPrice * doubleNullAsZero(fondValue.getOwnedUnits()));
		fondValue.setTotalInvest(doubleNullAsZero(fondValue.getTotalFees()) + doubleNullAsZero(fondValue.getTotalBuyPrice()));
		fondValue.setActualFondProfit(doubleNullAsZero(fondValue.getTotalFondValue()) -  doubleNullAsZero(fondValue.getTotalBuyPrice()));
		fondValue.setProfit(doubleNullAsZero(fondValue.getTotalFondValue()) - doubleNullAsZero(fondValue.getTotalInvest()));
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
		
		payment.setId(obj.getId());
		payment.setFondId(obj.getParentFond().getId());
		payment.setDateOfPurchase(obj.getDateOfPurchase());
		payment.setBuyPrice(doubleNullAsZero(obj.getBuyPrice()));
		payment.setFee(doubleNullAsZero(obj.getFee()));
		payment.setPurchasedUnits(doubleNullAsZero(obj.getPurchasedUnits()));
		
		if (!isZero(payment.getPurchasedUnits())) {
			payment.setUnitPrice(payment.getBuyPrice()/payment.getPurchasedUnits());
		}
		
	
		
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
		
		payment.setId(fondPaymentDo.getId());
		payment.setDateOfPurchase(fondPaymentDo.getDateOfPurchase());
		payment.setBuyPrice(fondPaymentDo.getBuyPrice());
		payment.setFeeAmount(fondPaymentDo.getFee());
		payment.setPurchasedUnits(fondPaymentDo.getPurchasedUnits());
		
		return payment;
	}
	
	private static void populateInvestmentPersistent(Investment target, InvestmentBaseDO src) {
		target.setId(src.getId());
		target.setName(src.getName());
		target.setEstablishingDate(src.getStartDate());
		target.setEndDate(src.getEndDate());
		target.setDayOfPay(src.getDayOfPay());
		target.setFeePct(src.getFeePct());
		target.setAmountOfPay(src.getAmountOfPay());
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
