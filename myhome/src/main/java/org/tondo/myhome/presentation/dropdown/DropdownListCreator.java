package org.tondo.myhome.presentation.dropdown;

import java.util.ArrayList;
import java.util.List;

import org.tondo.myhome.enumsvc.EnumValue;

public class DropdownListCreator<T> {
	
	public static interface Convertor<K> {
		K fromString(String str);
	}
	
	public static final Convertor<Integer>INTEGER_KEY = new Convertor<Integer>() {
		@Override
		public Integer fromString(String str) {
			return Integer.parseInt(str);
		}
	};
	
	public static final Convertor<String>STRING_KEY = new Convertor<String>() {
		@Override
		public String fromString(String str) {
			return str;
		}
	};
	
	private List<DropdownValue<T>> values;
	private Convertor<T> convertor;
	
	public DropdownListCreator(Convertor<T> convertor) {
		this.values = new ArrayList<>();
		this.convertor = convertor;
	}
	
	public DropdownListCreator<T> addItems(List<EnumValue> enumValues) {
		
		for (EnumValue v : enumValues) {
			this.values.add(new DropdownValue<T>(convertor.fromString(v.getValue()), v.getLabel()));
		}
		
		return this;
	}
	
	public DropdownListCreator<T> addItems(T[] items) {
		for (T i : items) {
			this.values.add(new DropdownValue<T>(i, i.toString()));
		}
		
		return this;
	}
	
	public List<DropdownValue<T>> values() {
		return this.values;
	}
	
	public static List<DropdownValue<String>> asStringKey(String... pairs) {
		List<DropdownValue<String>> retval = new ArrayList<>();
		
		if (pairs == null || pairs.length % 2 != 0) {
			throw new IllegalArgumentException("Bad dropdown value pairs!");
		}
		
		for (int i = 0; i < pairs.length; i+=2) {
			retval.add(new DropdownValue<String>(pairs[i], pairs[i+1]));
		}
		
		return retval;
	}
}
