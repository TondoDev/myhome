package org.tondo.myhome.svc.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.data.domain.WaterUsage;
import org.tondo.myhome.data.repo.WaterUsageRepository;
import org.tondo.myhome.dto.WaterUsageDO;

@Service
public class WaterSvc {
	
	@Autowired
	private WaterUsageRepository waterUsageRepository;

	
	public List<WaterUsageDO> getWagerUsage() {
		//return iterableToDataObjectList(waterUsageRepository.findAll(), WaterSvc::toDataObject);
		
		return calculateDifferences(waterUsageRepository.findAll());
	}
	
	public void addMeasurement(WaterUsageDO usage) {
		WaterUsage waterUsageData = new WaterUsage();
		waterUsageData.setColdUsage(usage.getColdUsage());
		waterUsageData.setWarmUsage(usage.getWarmUsage());
		waterUsageData.setMeasured(usage.getMeasured());
		
		waterUsageRepository.save(waterUsageData);
	}
	
	private List<WaterUsageDO> calculateDifferences(Iterable<WaterUsage> collection) {
		
		List<WaterUsageDO> retVal = new ArrayList<>();
		Iterator<WaterUsage> iter = collection.iterator();
		WaterUsage previous = null;
		while (iter.hasNext()) {
			WaterUsage current = iter.next();
			WaterUsageDO data = toDataObject(current);
			if (previous != null) {
				double coldDiff = doubleNotNullOperation(current.getColdUsage(), previous.getColdUsage(), (d1, d2) -> d1 - d2); 
				data.setDiffCold(coldDiff);
				double warmDiff = doubleNotNullOperation(current.getWarmUsage(), previous.getWarmUsage(), (d1, d2) -> d1 - d2);
				data.setDiffWarm(warmDiff);
				
				int numOfDays = (int)ChronoUnit.DAYS.between(previous.getMeasured(), current.getMeasured());
				data.setNumberOfDays(numOfDays);
				
				data.setColdPerDay(coldDiff/numOfDays);
				data.setWarmPerDay(warmDiff/numOfDays);
			}
			
			previous = current;
			retVal.add(data);
		}
		
		return retVal;
	}
	
	
	private double doubleNotNullOperation(Double d1, Double d2, DoubleBinaryOperator operator) {
		double operandOne = d1 == null ? 0.0 : d1;
		double operandTwo = d2 == null ? 0.0 : d2;
		
		return operator.applyAsDouble(operandOne, operandTwo);
	}
	
	
	private static WaterUsageDO toDataObject(WaterUsage obj) {
		WaterUsageDO waterUsageDo = new WaterUsageDO();
		waterUsageDo.setId(obj.getId());
		waterUsageDo.setMeasured(obj.getMeasured());
		waterUsageDo.setWarmUsage(obj.getWarmUsage());
		waterUsageDo.setColdUsage(obj.getColdUsage());
		return waterUsageDo;
	}
}
