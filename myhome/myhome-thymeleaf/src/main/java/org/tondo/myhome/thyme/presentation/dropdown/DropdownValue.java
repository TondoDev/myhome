package org.tondo.myhome.thyme.presentation.dropdown;

public class DropdownValue<T> {
	private T key;
	private String label;
	
	public DropdownValue(T k, String l) {
		this.key = k;
		this.label = l;
	}
	
	public T getKey() {
		return key;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return this.key.toString() + " => " + this.label;
	}
}
