package org.tondo.myhome.enumsvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class EnumSvc {

	private List<EnumName> enums;
	private Map<String, List<EnumValue>> enumValues;
	
	private static final String EXPENSES = "EXPENSES";
	
	@PostConstruct
	public final void postConstruct() {
		enums = new ArrayList<>();
		enums.add(new EnumName(1, EXPENSES, "STR"));
		
		this.enumValues = new HashMap<>();
		List<EnumValue> expenseValues = new ArrayList<>();
		expenseValues.add(new EnumValue(1, 1, "FOOD", "Strava"));
		expenseValues.add(new EnumValue(2, 1, "FUN",  "Zabava"));
		expenseValues.add(new EnumValue(3, 1, "DRUG", "Drogeria"));
		expenseValues.add(new EnumValue(4, 1, "HOME", "Vybavenie"));
		expenseValues.add(new EnumValue(5, 1, "BAD",  "Jedy"));
		this.enumValues.put(EXPENSES, expenseValues);
	}
	
	public List<EnumName> getAllEnums() {
		return Collections.unmodifiableList(this.enums);
	}
	
	public List<EnumValue> getEnumValues(String enumName) {
		List<EnumValue> values = this.enumValues.get(enumName);
		return values == null ? null : Collections.unmodifiableList(values);
	}
}
