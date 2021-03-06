package org.tondo.myhome.svc.enumsvc;

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
		expenseValues.add(new EnumValue(6, 1, "HEAL",  "Zdravie"));
		expenseValues.add(new EnumValue(7, 1, "OFFICE",  "Uradne"));
		expenseValues.add(new EnumValue(8, 1, "CLOTH",  "Oblecenie"));
		expenseValues.add(new EnumValue(9, 1, "EDU",  "Vzdelanie"));
		this.enumValues.put(EXPENSES, expenseValues);
	}
	
	public List<EnumName> getAllEnums() {
		return Collections.unmodifiableList(this.enums);
	}
	
	public List<EnumValue> getEnumValues(String enumName) {
		List<EnumValue> values = this.enumValues.get(enumName);
		return values == null ? null : Collections.unmodifiableList(values);
	}
	
	public boolean isEnumValue(String value, String enumName) {
		
		return false;
	}
	
	public String resolve(String value, String enumName) {
		List<EnumValue> enumValues = this.enumValues.get(enumName);
		if (enumValues == null) {
			return unresolvable(value);
		}
		
		for (EnumValue ev : enumValues) {
			if (value.equals(ev.getValue())) {
				return ev.getLabel();
			}
		}
		
		return unresolvable(value);
	}
	
	protected String unresolvable(String value) {
		return "!" + value + "!";
	}
}
