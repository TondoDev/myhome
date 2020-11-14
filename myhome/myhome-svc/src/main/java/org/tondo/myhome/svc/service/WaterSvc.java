package org.tondo.myhome.svc.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tondo.myhome.data.domain.WaterMeter;
import org.tondo.myhome.data.domain.WaterUsage;
import org.tondo.myhome.data.repo.WaterMeterRepository;
import org.tondo.myhome.data.repo.WaterUsageRepository;
import org.tondo.myhome.dto.WaterMeterDO;
import org.tondo.myhome.dto.WaterUsageDO;
import org.tondo.myhome.svc.exception.FieldError;
import org.tondo.myhome.svc.exception.WaterValidationException;

import static org.tondo.myhome.svc.ServiceUtils.*;

@Service
public class WaterSvc {
	
	@Autowired
	private WaterUsageRepository waterUsageRepository;
	
	@Autowired
	private WaterMeterRepository waterMeterRepository;

	
	public List<WaterUsageDO> getWagerUsage(Long meterId) {
		//return iterableToDataObjectList(waterUsageRepository.findAll(), WaterSvc::toDataObject);
		WaterMeter meter = this.waterMeterRepository.findOne(meterId);
		if (meter == null) {
			throw new IllegalStateException("Invalid water meter!");
		}
		
		return calculateDifferences(waterUsageRepository.findAllByWaterMeterOrderByMeasuredDesc(meter));
	}
	
	public void addMeasurement(Long meterId, WaterUsageDO usage) {
		
		WaterMeter meter = waterMeterRepository.findOne(meterId);
		if (meter == null) {
			throw new IllegalStateException("404 - Not found");
		} else if (!Boolean.TRUE.equals(meter.isActive())) {
			throw new IllegalStateException("meter not active");
		}
		
		WaterUsage latestWaterusage = waterUsageRepository.findTopByWaterMeterOrderByMeasuredDesc(meter);
		WaterUsage waterUsageData = new WaterUsage();
		if (latestWaterusage!= null) {
			List<FieldError> error = validateMeasurmentContinuity(usage, toDataObject(latestWaterusage));
			if (!error.isEmpty()) {
				throw new WaterValidationException(error);
			}
			
			// update of values, when date is the same as for last entry
			if (latestWaterusage.getMeasured().equals(usage.getMeasured())) {
				waterUsageData = latestWaterusage;
			}
		}
		
		
		waterUsageData.setColdUsage(usage.getColdUsage());
		waterUsageData.setWarmUsage(usage.getWarmUsage());
		waterUsageData.setMeasured(usage.getMeasured());
		waterUsageData.setWaterMeter(meter);
		
		waterUsageRepository.save(waterUsageData);
	}
	
	private List<FieldError> validateMeasurmentContinuity(WaterUsageDO current, WaterUsageDO latest) {
		List<FieldError> errors = new ArrayList<FieldError>();
		if (current.getMeasured().isBefore(latest.getMeasured())) {
			errors.add(new FieldError("measured", "water.measured.before"));
		}
		
		if (current.getColdUsage() < latest.getColdUsage()) {
			errors.add(new FieldError("coldUsage", "water.coldUsage.lower"));
		}
		
		if (current.getWarmUsage() < latest.getWarmUsage()) {
			errors.add(new FieldError("warmUsage", "water.warmUsage.lower"));
		}
		
		return errors;
	}
	
	private List<WaterUsageDO> calculateDifferences(Iterable<WaterUsage> collection) {
		
		List<WaterUsageDO> retVal = new ArrayList<>();
		Iterator<WaterUsage> iter = collection.iterator();
		WaterUsageDO previous = null;
		while (iter.hasNext()) {
			WaterUsage current = iter.next();
			WaterUsageDO data = toDataObject(current);
			if (previous != null) {
				double coldDiff = doubleNotNullOperation(previous.getColdUsage(), current.getColdUsage(), (d1, d2) -> d1 - d2); 
				previous.setDiffCold(coldDiff);
				double warmDiff = doubleNotNullOperation(previous.getWarmUsage(), current.getWarmUsage(), (d1, d2) -> d1 - d2);
				previous.setDiffWarm(warmDiff);
				
				int numOfDays = (int)ChronoUnit.DAYS.between(current.getMeasured(), previous.getMeasured());
				previous.setNumberOfDays(numOfDays);
				
				previous.setColdPerDay(coldDiff/numOfDays);
				previous.setWarmPerDay(warmDiff/numOfDays);
			}
			
			previous = data;
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
	
	private static WaterMeterDO toDataObject(WaterMeter  obj) {
		WaterMeterDO  waterMeter = new WaterMeterDO();
		waterMeter.setId(obj.getId());
		waterMeter.setMeterIdentifier(obj.getMeterIdentifier());
		waterMeter.setValidFrom(obj.getValidFrom());
		waterMeter.setValidTo(obj.getValidTo());
		waterMeter.setActive(obj.isActive());
		return waterMeter;
	}
	
	
	public WaterMeterDO getWaterMeter(Long meterId) {
		return toDataObject(this.waterMeterRepository.findOne(meterId));
	}
	
	public List<WaterMeterDO> getWaterMeters() {
		return iterableToDataObjectList(this.waterMeterRepository.findAllByOrderByValidFromDesc(), WaterSvc::toDataObject);
	}
	
	public void addWaterMeter(WaterMeterDO meterDO) {
		WaterMeter newWaterMeter = new WaterMeter();
		newWaterMeter.setMeterIdentifier(meterDO.getMeterIdentifier());
		newWaterMeter.setValidFrom(meterDO.getValidFrom());
		newWaterMeter.setValidTo(meterDO.getValidTo());
		newWaterMeter.setActive(meterDO.isActive());
		
		this.waterMeterRepository.save(newWaterMeter);
	}
	
	public WaterMeterDO updateWaterMeter(WaterMeterDO waterMeter) {
		
		WaterMeter persistent = this.waterMeterRepository.findOne(waterMeter.getId());
		if (persistent == null) {
			throw new IllegalStateException("Invalid waterMeter to update");
		}
		
		persistent.setActive(waterMeter.isActive());
		persistent.setMeterIdentifier(waterMeter.getMeterIdentifier());
		persistent.setValidFrom(waterMeter.getValidFrom());
		persistent.setValidTo(waterMeter.getValidTo());
		
		return toDataObject(this.waterMeterRepository.save(persistent));
	}
}
